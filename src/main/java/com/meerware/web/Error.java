package com.meerware.web;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_UNDERSCORE;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.io.Serializable;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.primitives.Ints;

/**
 * Internal transfer object representation of an {@code JSON} error.
 */
@JsonInclude(value = NON_EMPTY)
final class Error implements Serializable {

    /**
     * {@link Source} component of an error, either a pointer to a field
     * or a parameter value.
     */
    @JsonInclude(value = NON_EMPTY)
    static final class Source implements Serializable {

        /**
         * Serial version UID.
         */
        private static final long serialVersionUID = -4727765312027783286L;

        private final String pointer;

        private final String parameter;

        private Source(String pointer, String parameter) {
            this.pointer = pointer;
            this.parameter = parameter;
        }

        static Source parameter(String parameter) {
            return new Source(null, parameter);
        }

        static Source pointer(String pointer) {
            return new Source(pointer, null);
        }

        @JsonProperty(access = READ_ONLY)
        String getPointer() {
            return pointer;
        }

        @JsonProperty(access = READ_ONLY)
        String getParameter() {
            return parameter;
        }
    }


    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 2075934331646817224L;

    /**
     * {@link HttpStatus} of the {@link Error}. Should default
     * to {@link HttpStatus#INTERNAL_SERVER_ERROR} if nothing is explicitly
     * set.
     */
    @NotNull
    private final HttpStatus status;

    /**
     * Error code, can be {@code null} and will default using the status
     * as the basis.
     */
    @Nullable
    private String code;

    /**
     * Error title, can be {@code null} and will default using the
     * status reason.
     */
    @Nullable
    private String title;

    /**
     * Detail, can be {@code null}.
     */
    @Nullable
    private String detail;

    @Nullable
    private Source source;


    private Error(HttpStatus status) {
        this.status = ofNullable(status).orElse(INTERNAL_SERVER_ERROR);
    }

    static Error of(int status) {
        return of(HttpStatus.resolve(status));
    }

    static Error of(String status) {
        return of(Ints.tryParse(status));
    }

    static Error of(HttpStatus status) {
        return new Error(status);
    }

    @JsonFormat(shape = STRING)
    @JsonProperty(access = READ_ONLY)
    int getStatus() {
        return status.value();
    }

    @JsonProperty(access = READ_ONLY)
    String getCode() {
        return ofNullable(code)
                .orElse("error." + UPPER_UNDERSCORE.to(LOWER_CAMEL, status.name()));
    }

    Error code(String code) {
        this.code = code;
        return this;
    }

    @JsonProperty(access = READ_ONLY)
    String getTitle() {
        return ofNullable(title).orElse(status.getReasonPhrase());
    }

    Error title(String title) {
        this.title = title;
        return this;
    }

    @Nullable
    @JsonProperty(access = READ_ONLY)
    String getDetail() {
        return detail;
    }

    Error detail(String detail) {
        this.detail = detail;
        return this;
    }

    @Nullable
    @JsonProperty(access = READ_ONLY)
    Source getSource() {
        return source;
    }

    Error source(Source source) {
        this.source = source;
        return this;
    }

    /**
     * Convenience method to set the source pointer.
     *
     * @param pointer is the source pointer.
     * @return the {@link Error}.
     */
    Error pointer(String pointer) {
        return source(Source.pointer(pointer));
    }
}
