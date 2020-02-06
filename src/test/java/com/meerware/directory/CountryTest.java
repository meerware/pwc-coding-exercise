package com.meerware.directory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Tests for the {@link Country}.
 */
public class CountryTest {

    /**
     * {@link ObjectMapper} for {@code JSON} tests.
     */
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * Ensures parsing of {@code null}.
     */
    @Test
    public void shouldParseNull() {
        assertNull(Country.parse(null));
    }

    /**
     * Ensures parsing of {@code empty} values.
     */
    @Test
    public void shouldParseEmpty() {
        assertNull(Country.parse(""));
    }

    /**
     * Ensures parsing of {@code blank} values.
     */
    @Test
    public void shouldParseBlank() {
        assertNull(Country.parse(" "));
    }

    /**
     * Ensures parsing of actual values.
     */
    @Test
    public void shouldParse() {
        assertEquals(Country.ECUADOR, Country.parse("República del Ecuador"));
        assertEquals(Country.ECUADOR, Country.parse("EC"));
        assertEquals(Country.ECUADOR, Country.parse("Republic of Ecuador"));
        assertEquals(Country.ECUADOR, Country.parse("ECU"));
        assertEquals(Country.ECUADOR, Country.parse("Ecuador"));
    }

    /**
     * Ensures {@code JSON} serialization.
     */
    @Test
    public void shouldSerialize() throws Exception {
        assertEquals("\"São Tomé and Príncipe\"",
                mapper.writeValueAsString(Country.SAO_TOME_AND_PRINCIPE));
    }

    /**
     * Ensures {@code JSON} deserialization.
     */
    @Test
    public void shouldDeserialize() throws Exception {
        assertEquals(Country.SERBIA, mapper.readValue("\"Република Србија\"", Country.class));
    }
}
