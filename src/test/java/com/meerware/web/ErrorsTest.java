package com.meerware.web;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;


public class ErrorsTest {


    /**
     * {@link ObjectMapper} for {@code JSON} serialization tests.
     */
    private static ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
    }

    /**
     * Ensures {@code JSON} serialization.
     */
    @Test
    public void shouldSerialize() throws Exception {
        assertEquals("{\"errors\":[{\"status\":\"401\",\"code\":\"error.unauthorized\",\"title\":\"Unauthorized\"}]}",
                mapper.writeValueAsString(Errors.of(Error.of(401))));
    }

}
