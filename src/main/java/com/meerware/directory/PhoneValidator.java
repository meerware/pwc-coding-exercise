package com.meerware.directory;

import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

/**
 * {@link ConstraintValidator} for a phone number. This needs to be public due
 * to validation implementation.
 */
public class PhoneValidator
       implements ConstraintValidator<Phone, String> {

    /**
     * Default country code.
     */
    private static final String DEFAULT_COUNTRY_CODE = "AU";

    private static final Set<String> EXAMPLE_AUSTRALIAN_NUMBERS
        = ImmutableSet.of(
                "1800 00 1234",
                "1300 65 9399",
                "+61 3 8685 1462",
                "03 8685 1462",
                "0491 570 156",
                "+61 491 570 156");

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        final PhoneNumberUtil utility = PhoneNumberUtil.getInstance();
        try {
            final PhoneNumber number = utility.parse(value, DEFAULT_COUNTRY_CODE);

            if (!utility.isValidNumber(number)) {
                final String examples = Joiner.on(", ").join(EXAMPLE_AUSTRALIAN_NUMBERS);

                // Give a useful message
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        "Invalid phone number, some examples of valid numbers are: " + examples)
                       .addConstraintViolation();
                return false;
            }
            // Valid
            return true;
        } catch (NumberParseException exception) {
            return false;
        }
    }

}
