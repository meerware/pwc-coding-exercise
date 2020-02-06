package com.meerware.web;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Tests for the {@link Error} transfer object.
 */
public class ErrorTest {

    /**
     * {@link ObjectMapper} for {@code JSON} serialization tests.
     */
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * Ensures {@code JSON} serialization with just a status.
     */
    @Test
    public void shouldSerializeWithStatus() throws Exception {
        assertEquals("{\"status\":\"400\",\"code\":\"error.badRequest\",\"title\":\"Bad Request\"}",
                mapper.writeValueAsString(Error.of(400)));
    }
}
