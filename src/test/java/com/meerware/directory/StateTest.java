package com.meerware.directory;

import static com.google.common.collect.Lists.newArrayList;
import static com.meerware.directory.State.NORTHERN_TERRITORY;
import static com.meerware.directory.State.QUEENSLAND;
import static com.meerware.directory.State.WESTERN_AUSTRALIA;
import static org.apache.commons.lang3.SerializationUtils.deserialize;
import static org.apache.commons.lang3.SerializationUtils.serialize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;


/**
 * Tests for the {@link State}.
 */
public class StateTest {

    /**
     * {@link ObjectMapper} used for {@code JSON} tests.
     */
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * Ensures parsing of a {@code null} value.
     */
    @Test
    public void shouldParseNull() {
        assertNull(State.parse(null));
    }

    /**
     * Ensures parsing of a {@code empty} value.
     */
    @Test
    public void shouldParseEmpty() {
        assertNull(State.parse(""));
    }

    /**
     * Ensures parsing of a {@code blank} value.
     */
    @Test
    public void shouldParseBlank() {
        assertNull(State.parse(" "));
    }

    /**
     * Ensures parsing.
     */
    @Test
    public void shouldParse() {
        assertEquals(State.AUSTRALIAN_CAPITAL_TERRITORY, State.parse("ACT"));
        assertEquals(State.AUSTRALIAN_CAPITAL_TERRITORY, State.parse("act"));
        assertEquals(State.AUSTRALIAN_CAPITAL_TERRITORY, State.parse("Australian Capital Territory"));
        assertEquals("Wisconsin", State.parse("Wisconsin").getName());
    }

    /**
     * Ensures {@link java.io.Serializable}.
     */
    @Test
    public void shouldBeSerializable() {
        assertEquals(QUEENSLAND, deserialize(serialize(QUEENSLAND)));
    }

    /**
     * Ensures {@link java.lang.Comparable}.
     */
    @Test
    public void shouldBeComparable() {
        List<State> states = newArrayList(QUEENSLAND, NORTHERN_TERRITORY, WESTERN_AUSTRALIA);
        Collections.sort(states);
        assertEquals(NORTHERN_TERRITORY, states.get(0));
        assertEquals(QUEENSLAND, states.get(1));
        assertEquals(WESTERN_AUSTRALIA, states.get(2));
    }

    /**
     * Ensures {@code JSON} serialization.
     */
    @Test
    public void shouldSerialize() throws Exception {
        assertEquals("\"New South Wales\"", mapper.writeValueAsString(State.NEW_SOUTH_WALES));
    }

    /**
     * Ensures {@code JSON} deserialization.
     */
    @Test
    public void shouldDeserialize() throws Exception {
        assertEquals(State.NEW_SOUTH_WALES, mapper.readValue("\"NSW\"", State.class));
        assertEquals(State.NEW_SOUTH_WALES, mapper.readValue("\"New South Wales\"", State.class));
    }

    /**
     * Ensures not equal {@code null}.
     */
    @Test
    public void shouldNotEqualNull() {
        assertFalse(State.TASMANIA.equals(null));
    }

    /**
     * Ensures not equal to a different class.
     */
    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void shouldNotEqualDifferentClass() {
        assertFalse(State.TASMANIA.equals("TAS"));
    }

    /**
     * Ensures equal the same memory reference.
     */
    @Test
    public void shouldEqualSameReference() {
        State left = State.parse("Wisconsin");
        State right = left;
        assertTrue(left.equals(right));
        assertTrue(right.equals(left));
        assertEquals(left.hashCode(), right.hashCode());
    }

    /**
     * Ensures equal the same value.
     */
    @Test
    public void shouldEqualSameValue() {
        State left = State.parse("Wisconsin");
        State right = State.parse("Wisconsin");
        assertTrue(left.equals(right));
        assertTrue(right.equals(left));
        assertEquals(left.hashCode(), right.hashCode());
    }

    /**
     * Ensures not equal with a different underlying value.
     */
    @Test
    public void shouldNotEqualDifferentValue() {
        State left = State.parse("Wisconsin");
        State right = State.parse("New York");
        assertFalse(left.equals(right));
        assertFalse(right.equals(left));
    }

}
