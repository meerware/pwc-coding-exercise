package com.meerware.directory;

import static com.meerware.directory.Country.parse;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * {@code JPA} {@link AttributeConverter} for converting back and forth
 * from a {@link Country}.
 */
@Converter(autoApply = true)
class CountryConverter implements AttributeConverter<Country, String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String convertToDatabaseColumn(Country country) {
        if (country == null) {
            return null;
        }
        return country.getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Country convertToEntityAttribute(String value) {
        return parse(value);
    }

}
