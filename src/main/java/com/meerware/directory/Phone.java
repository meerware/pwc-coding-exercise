package com.meerware.directory;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.meerware.directory.Phone.List;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Validation annotation for a phone number.
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Repeatable(List.class)
@Constraint(validatedBy = { PhoneValidator.class })
@interface Phone {

    /**
     * @return the error message template.
     */
    String message() default "Invalid phone number";

    /**
     * @return the groups the constraint belongs to.
     */
    Class<?>[] groups() default {};

    /**
     * @return the payload associated to the constraint.
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@link Phone} annotations on the same element.
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        Phone[] value();
    }
}
