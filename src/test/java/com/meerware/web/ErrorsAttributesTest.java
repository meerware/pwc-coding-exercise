package com.meerware.web;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.WebRequest;

import com.google.common.collect.ImmutableList;

/**
 * Tests for {@link ErrorsAttributes}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ErrorsAttributesTest {

    /**
     * Main test object.
     */
    @InjectMocks
    private ErrorsAttributes attributes;

    /**
     * {@link Mock} {@link MessageSourceAccessor}.
     */
    @Mock
    private MessageSourceAccessor accessor;

    /**
     * {@link Mock} {@link HttpServletRequest}.
     */
    @Mock
    private HttpServletRequest request;

    /**
     * {@link Mock} {@link HttpServletResponse}.
     */
    @Mock
    private HttpServletResponse response;

    @Test
    public void shouldResolveExceptionAndAddRequestAttribute() {
        Exception exception = new RuntimeException();
        attributes.resolveException(request, response, new Object(), exception);
        verify(request).setAttribute("com.meerware.web.Exception", exception);
    }

    /**
     * Ensures getting the error attributes from a generic exception.
     */
    @Test
    public void shouldGetErrorAttributesFromGenericException() {
        WebRequest request = mock(WebRequest.class);
        when(request.getAttribute("javax.servlet.error.exception",
                SCOPE_REQUEST)).thenReturn(new RuntimeException("Boom"));
        Errors errors = attributes.getErrorAttributes(request, false);
        assertFalse(errors.isEmpty());
        verify(request).setAttribute("javax.servlet.error.status_code", 500, SCOPE_REQUEST);
    }

    /**
     * Ensures getting error attributes from a passed in status code.
     */
    @Test
    public void shouldGetErrorAttributesFromStatusCode() {
        WebRequest request = mock(WebRequest.class);
        when(request.getAttribute("javax.servlet.error.status_code",
                SCOPE_REQUEST)).thenReturn(503);
        Errors errors = attributes.getErrorAttributes(request, false);
        assertFalse(errors.isEmpty());
        verify(request).setAttribute("javax.servlet.error.status_code", 503, SCOPE_REQUEST);
    }

    /**
     * Ensures getting error attributes from a {@link BindException) (validation error).
     */
    @Test
    public void shouldGetErrorAttributesFromBindException() {
        BindException result = mock(BindException.class);
        ObjectError objectError = mock(ObjectError.class);
        FieldError fieldError = mock(FieldError.class);
        when(result.getAllErrors()).thenReturn(ImmutableList.of(objectError, fieldError));

        WebRequest request = mock(WebRequest.class);
        when(request.getAttribute("javax.servlet.error.exception", SCOPE_REQUEST)).thenReturn(result);

        Errors errors = attributes.getErrorAttributes(request, false);
        assertFalse(errors.isEmpty());
        verify(request).setAttribute("javax.servlet.error.status_code", 400, SCOPE_REQUEST);
    }
}
