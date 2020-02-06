package com.meerware.web;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.prependIfMissing;
import static org.apache.commons.lang3.StringUtils.replace;
import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.resolve;

import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Streams;

/**
 * Implementation of the {@link ErrorAttributes} which converts
 * an exception into the standard error. This has the structure of:
 * <code>
 * {
 *   "errors": [{
 *     "status": STATUS,
 *     "code": CODE,
 *     "title": TITLE,
 *     "detail": DETAIL,
 *     "source": {
 *       "pointer": POINTER,
 *       "parameter": PARAMETER
 *     }
 *   }]
 * }
 * </code>
 */
class ErrorsAttributes implements ErrorAttributes, HandlerExceptionResolver {

    /**
     * Request attribute name of where the exceptions are stored.
     */
    private static final String EXCEPTION_ATTRIBUTE = "com.meerware.web.Exception";


    /**
     * {@link MessageSourceAccessor} used to get any error messages
     * in the validation or message properties of the application.
     * Defaults are used if not available.
     */
    private final MessageSourceAccessor source;

    ErrorsAttributes(MessageSourceAccessor source) {
        this.source = source;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelAndView resolveException(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception exception) {
        request.setAttribute(EXCEPTION_ATTRIBUTE, exception);
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Errors getErrorAttributes(
            WebRequest request,
            boolean includeStackTrace) {
        Throwable cause = getError(request);

        final int value = ofNullable((Integer) request.getAttribute(
                "javax.servlet.error.status_code", SCOPE_REQUEST))
                .orElse(INTERNAL_SERVER_ERROR.value());

        final Errors errors;
        if (cause != null) {
            errors = this.getErrorAttributes(
                    cause, resolve(value));
        } else {
            // No exception, so we have to try and see if there was
            // an error code in the request
            Error error = Error.of(value);
            errors = Errors.of(error);
        }

        // Errors is now filled out, try and work out an appropriate
        // response code based on all the errors
        // Reduce down, 500s are given priority, multiple different
        // 500 type errors gives just an internal server error
        // Multiple 400 type errors reduce to a bad request error
        HttpStatus status = Streams.stream(errors)
                .map(error -> resolve(error.getStatus()))
                .filter(Objects::nonNull)
                .reduce((left, right) -> {
                    if (Objects.equals(left, right)) {
                        return left;
                    }
                    if (left.is5xxServerError() && right.is5xxServerError()) {
                        return INTERNAL_SERVER_ERROR;
                    } else if (left.is5xxServerError()) {
                        return left;
                    } else if (right.is5xxServerError()) {
                        return right;
                    } else {
                        return BAD_REQUEST;
                    }
                }).orElse(INTERNAL_SERVER_ERROR);

        // Update the request attribute with the code
        request.setAttribute("javax.servlet.error.status_code",
                status.value(), SCOPE_REQUEST);

        return errors;
    }

    Errors getErrorAttributes(
            Throwable cause,
            @Nullable HttpStatus status) {
        // Was it a binding result or something that holds a binding result
        if (cause instanceof BindingResult) {
            return getErrorAttributes((BindingResult) cause);
        } else if (cause instanceof MethodArgumentNotValidException) {
            return getErrorAttributes(((MethodArgumentNotValidException) cause).getBindingResult());
        }

        // Try and get it from a response status on the exception
        final ResponseStatus annotation = findMergedAnnotation(
                cause.getClass(), ResponseStatus.class);

        final Error error;
        if (annotation != null && annotation.code() != null) {
            // Override from the exception annotation
            error = Error.of(annotation.code()).detail(annotation.reason());
        } else  {
            // Use what was passed in, null will be handled
            error = Error.of(status);
        }

        return Errors.of(error);
    }

    /**
     * Gets the error attributes for a {@link BindingResult}. This will
     * create an error object per object error in the result.
     *
     * @param result is the {@link BindingResult}. This will not be {@code null}.
     * @return an {@link Errors} object.
     */
    Errors getErrorAttributes(
            BindingResult result) {
        // Make an error per field error with a pointer to help the
        // end user to narrow down the field which is invalid

        List<Error> errors = Lists.newArrayList();
        for (final ObjectError error : result.getAllErrors()) {
            Error item = Error.of(BAD_REQUEST);

            if (error instanceof FieldError) {
                item.code("error.invalidField")
                    .title(source.getMessage("error.invalidField",
                        "Invalid Field"));
            } else {
                item.code("error.invalidObject")
                     .title(source.getMessage("error.invalidObject",
                        "Invalid Object"));
            }

            final String detail = source.getMessage(error);
            if (isNotBlank(detail)) {
                item.detail(detail);
            }

            // Finally, add pointers to the field to help the user
            // narrow down the problem

            if (error instanceof FieldError) {
                final FieldError field = (FieldError) error;
                final String pointer = prependIfMissing("/" + replace(field.getField(), ".", "/"), "/data");
                item.pointer(pointer);
            }

            errors.add(item);
        }

        return Errors.of(errors);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Throwable getError(WebRequest request) {
        Throwable exception = (Throwable) request.getAttribute(EXCEPTION_ATTRIBUTE, SCOPE_REQUEST);
        if (exception == null) {
            exception = (Throwable) request.getAttribute("javax.servlet.error.exception", SCOPE_REQUEST);
        }
        return exception;
    }

}
