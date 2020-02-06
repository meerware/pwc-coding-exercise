package com.meerware.directory;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;
import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;



/**
 * Tests for the {@link Address}.
 */
public class AddressTest {

    /**
     * {@code JSON} representation.
     */
    private static final String JSON = "{"
            + "\"lines\":[\"100 Collins Street\"],"
            + "\"locality\":\"Melbourne\","
            + "\"postcode\":\"3000\","
            + "\"state\":\"Victoria\","
            + "\"country\":\"Australia\","
            + "\"display\":\"100 Collins Street, Melbourne, Victoria 3000, Australia\"}";

    /**
     * {@link ObjectMapper} used for {@code JSON} tests.
     */
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * {@link Validator} used for validation tests.
     */
    private static Validator validator = buildDefaultValidatorFactory().getValidator();

    /**
     * Main test object.
     */
    private Address address;

    /**
     * Sets up the main test object.
     */
    @Before
    public void setUp() {
        address = new Address();
        address.setCountry(Country.AUSTRALIA);
        address.setState(State.VICTORIA);
        address.setLocality("Melbourne");
        address.setPostcode("3000");
        address.setLines(newArrayList("100 Collins Street"));
    }

    /**
     * Ensures {@code JSON} serialization.
     */
    @Test
    public void shouldSerialize() throws Exception {
        assertEquals(JSON, mapper.writeValueAsString(address));
    }

    /**
     * Ensures {@code JSON} deserialization.
     */
    @Test
    public void shouldDeserialize() throws Exception {
        assertEquals(address, mapper.readValue(JSON, Address.class));
    }

    /**
     * Ensures valid with a complete, valid {@link Address}.
     */
    @Test
    public void shouldBeValidWithValidValues() {
        Set<ConstraintViolation<Address>> violations = validator.validate(address);
        assertTrue(
                "Violations: "
                        + Joiner.on(", ").join(
                                violations.stream().map(
                                violation -> violation.getMessage())
                                .collect(toList())),
                        violations.isEmpty());
    }


    /**
     * Ensures not valid when the state name is way too long.
     */
    @Test
    public void shouldNotBeValidWithTooLongState() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 129; i++) {
            builder.append('A');
        }
        address.setState(State.parse(builder.toString()));
        assertFalse(validator.validate(address).isEmpty());
    }

    /**
     * Ensures not valid with a locality name which is too long.
     */
    @Test
    public void shouldNotBeValidWithTooLongLocality() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 129; i++) {
            builder.append('A');
        }
        address.setLocality(builder.toString());
        assertFalse(validator.validate(address).isEmpty());
    }

    /**
     * Ensures not valid with a postcode which is too long.
     */
    @Test
    public void shouldNotBeValidWithTooLongPostcode() {
        address.setPostcode("12341234123412341");
        assertFalse(validator.validate(address).isEmpty());
    }

    /**
     * Ensures not valid when too many (more than 5) lines are given.
     */
    @Test
    public void shouldNotBeValidTooManyLines() {
        address.setLines(Lists.newArrayList("1", "2", "3", "4", "5", "6"));
        assertFalse(validator.validate(address).isEmpty());
    }

    /**
     * Ensures masking of the {@link Address#toString()} method for security.
     */
    @Test
    public void shouldMaskForDebugging() {
        assertEquals("100 C*********************************************ralia",
                address.toString());
    }
}
