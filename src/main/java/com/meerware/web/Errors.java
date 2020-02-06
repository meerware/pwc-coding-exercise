package com.meerware.web;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.NATURAL;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * {@code JSON} transfer object representing multiple {@link Error}
 * objects. This, in reality, is simply a {@link Map}. This is immutable.
 */
@JsonFormat(shape = NATURAL)
final class Errors extends ForwardingMap<String, Object> implements Iterable<Error> {

    /**
     * Map key to store the errors in.
     */
    private static final String ERRORS = "errors";

    private final List<Error> errors;

    private Errors(Error... errors) {
        this(ImmutableList.copyOf(errors));
    }

    private Errors(Iterable<Error> errors) {
        this.errors = ImmutableList.copyOf(errors);
    }

    static Errors of(Error... errors) {
        return new Errors(errors);
    }

    static Errors of(Iterable<Error> errors) {
        return new Errors(errors);
    }

    @Override
    protected Map<String, Object> delegate() {
        return ImmutableMap.of(ERRORS, errors);
    }

    @Override
    public Iterator<Error> iterator() {
        return errors.iterator();
    }

}
