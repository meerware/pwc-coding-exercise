package com.meerware.directory;

import static com.meerware.directory.State.parse;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
class StateConverter implements AttributeConverter<State, String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String convertToDatabaseColumn(State state) {
        if (state == null) {
            return null;
        }
        if (state.getCode() == null) {
            return state.getName();
        } else {
            return state.getCode();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public State convertToEntityAttribute(String value) {
        return parse(value);
    }

}
