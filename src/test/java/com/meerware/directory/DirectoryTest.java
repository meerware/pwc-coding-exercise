package com.meerware.directory;

import static org.apache.commons.lang3.SerializationUtils.deserialize;
import static org.apache.commons.lang3.SerializationUtils.serialize;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link Directory}.
 */
public class DirectoryTest {

    /**
     * {@code JSON} representation.
     */
    private static final String JSON = ""
            + "{\"name\":\"global\","
            + "\"contacts\":["
            +   "{\"name\":\"Frank Zappa\",\"address\":{},\"display\":\"Frank Zappa\"},"
            +   "{\"name\":\"Jack Bruce\",\"address\":{},\"display\":\"Jack Bruce\"},"
            +   "{\"name\":\"Les Claypool\",\"address\":{},\"display\":\"Les Claypool\"}]}";

    /**
     * {@link ObjectMapper} used for {@code JSON} tests.
     */
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * Main test class.
     */
    private Directory directory;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() {
        directory = new Directory("global", ImmutableList.of(
                    new Contact("Jack Bruce"),
                    new Contact("Les Claypool"),
                    new Contact("Frank Zappa")
                ));
    }

    /**
     * Ensures {@link java.io.Serializable}.
     */
    @Test
    public void shouldBeSerializable() {
        Directory result = deserialize(serialize(directory));
        assertEquals(directory.getName(), result.getName());
        assertEquals(directory.getContacts(), result.getContacts());
    }

    /**
     * Ensures {@code JSON} serialization.
     */
    @Test
    public void shouldSerialize() throws Exception {
        assertEquals(JSON, mapper.writeValueAsString(directory));
    }

    /**
     * Ensures {@code JSON} deserialization.
     */
    @Test
    public void shouldDeserialize() throws Exception {
        Directory result = mapper.readValue(JSON, Directory.class);
        assertEquals(directory.getName(), result.getName());
        assertEquals(directory.getContacts(), result.getContacts());
    }

    /**
     * Ensures calculation of the intersection with another {@link Directory}.
     * This should leave only the common {@link Contact}.
     */
    @Test
    public void shouldCalculateIntersectionWithAnotherDirectory() {
        Directory other = new Directory("other", ImmutableList.of(
                new Contact("Les Claypool"),
                new Contact("Jason Newstead")
            ));

        Directory intersection = directory.intersection(other);
        assertEquals(1, intersection.getContacts().size());
        assertEquals("Les Claypool", intersection.getContacts().get(0).getName());
    }

    /**
     * Ensures calculation of the set union with another {@link Directory}.
     * This is a combination of both sets of {@link Contact} entities.
     */
    @Test
    public void shouldCalculateUnionWithAnotherDirectory() {
        Directory other = new Directory("other", ImmutableList.of(
                new Contact("Les Claypool"),
                new Contact("Jason Newstead")
            ));

        Directory union = directory.union(other);
        assertEquals(4, union.getContacts().size());
        assertEquals("Frank Zappa", union.getContacts().get(0).getName());
        assertEquals("Jack Bruce", union.getContacts().get(1).getName());
        assertEquals("Jason Newstead", union.getContacts().get(2).getName());
        assertEquals("Les Claypool", union.getContacts().get(3).getName());
    }
}
