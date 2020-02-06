package com.meerware.directory;

import static com.meerware.directory.Country.AUSTRALIA;
import static com.meerware.directory.State.VICTORIA;
import static java.util.stream.Collectors.toList;
import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.apache.commons.lang3.SerializationUtils.deserialize;
import static org.apache.commons.lang3.SerializationUtils.serialize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the {@link Contact}.
 */
public class ContactTest {

    /**
     * Expected {@link JSON} representation.
     */
    private static final String JSON
        = "{\"name\":\"H P Lovecraft\","
        + "\"email\":\"hp@lovecraft.com\","
        + "\"phone\":\"1800-757-888\","
        + "\"address\":{"
        +   "\"lines\":[\"Level 13\",\"509 Bourke Street\"],"
        +   "\"locality\":\"Melbourne\","
        +   "\"postcode\":\"3000\","
        +   "\"state\":\"Victoria\","
        +   "\"country\":\"Australia\","
        +   "\"display\":\"Level 13, 509 Bourke Street, Melbourne, Victoria 3000, Australia\"},"
        + "\"display\":\"H P Lovecraft\"}";

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
    private Contact contact;

    /**
     * Sets up the main test object.
     */
    @Before
    public void setUp() {
        Address address = new Address();
        address.setLines(ImmutableList.of("Level 13", "509 Bourke Street"));
        address.setLocality("Melbourne");
        address.setPostcode("3000");
        address.setState(VICTORIA);
        address.setCountry(AUSTRALIA);

        contact = new Contact(
                "H P Lovecraft", "hp@lovecraft.com", "1800-757-888", address);
    }

    /**
     * Ensures {@link java.lang.Comparable} (sortable).
     */
    @Test
    public void shouldBeComparable() {
        List<Contact> contacts = Lists.newArrayList(contact,
                new Contact("Phil"), new Contact("Andrew"), new Contact("Andrew Howlett"));
        Collections.sort(contacts);
        assertEquals("Andrew", contacts.get(0).getName());
        assertEquals("Andrew Howlett", contacts.get(1).getName());
        assertEquals("H P Lovecraft", contacts.get(2).getName());
        assertEquals("Phil", contacts.get(3).getName());
    }

    /**
     * Ensures {@link java.io.Serializable}.
     */
    @Test
    public void shouldBeSerializable() {
        assertEquals(contact, deserialize(serialize(contact)));
    }

    /**
     * Ensures {@code JSON} serialization.
     */
    @Test
    public void shouldSerialize() throws Exception {
        assertEquals(JSON, mapper.writeValueAsString(contact));
    }

    /**
     * Ensures {@code JSON} deserialization.
     */
    @Test
    public void shouldDeserialize() throws Exception {
        assertEquals(contact, mapper.readValue(JSON, Contact.class));
    }

    /**
     * Ensures validation is successful when the {@link Contact} is valid.
     */
    @Test
    public void shouldBeValidWithValidValues() {
        Set<ConstraintViolation<Contact>> violations = validator.validate(contact);
        assertTrue(
                "Violations: "
                        + Joiner.on(", ").join(
                                violations.stream().map(
                                violation -> violation.getMessage())
                                .collect(toList())),
                        violations.isEmpty());
    }

    /**
     * Ensures validation fails when the email address given is not valid.
     */
    @Test
    public void shouldNotBeValidWithInvalidEmail() {
        contact = new Contact(
                "H P Lovecraft", "RUBBISH", "1800-757-888", null);
        assertFalse(validator.validate(contact).isEmpty());
    }

    /**
     * Ensures validation fails when the phone given is not valid.
     */
    @Test
    public void shouldNotBeValidWithInvalidPhone() {
        contact = new Contact(
                "H P Lovecraft", "cthulhu@lovecraft.com", "~~~", null);
        assertFalse(validator.validate(contact).isEmpty());
    }

    /**
     * Ensures validation fails when the {@link Address} given is not valid.
     */
    @Test
    public void shouldNotBeValidWhenInvalidAddress() {
        Address address = contact.getAddress();
        address.setPostcode("12341234123412341");
        assertFalse(validator.validate(contact).isEmpty());
    }

    /**
     * Ensures not equal to {@code null}.
     */
    @Test
    public void shouldNotEqualNull() {
        assertFalse(contact.equals(null));
    }

    /**
     * Ensures not equal to a different class.
     */
    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void shouldNotEqualDifferentClass() {
        assertFalse(contact.equals("RUBBISH"));
    }

    /**
     * Ensures equal to the same memory reference.
     */
    @Test
    public void shouldEqualSameReference() {
        Contact left = contact;
        Contact right = contact;
        assertTrue(left.equals(right));
        assertTrue(right.equals(left));
        assertEquals(left.hashCode(), right.hashCode());
    }

    /**
     * Ensures equal to the same underlying value.
     */
    @Test
    public void sholdEqualSameValue() {
        Contact left = contact;
        Contact right = new Contact(
                "H P Lovecraft", "hp@lovecraft.com", "1800-757-888", contact.getAddress());
        assertTrue(left.equals(right));
        assertTrue(right.equals(left));
        assertEquals(left.hashCode(), right.hashCode());
    }

    /**
     * Ensures not equal when having a different name.
     */
    @Test
    public void shouldNotEqualWithDifferentName() {
        Contact left = contact;
        Contact right = new Contact(
                "Phil Lovecraft", "hp@lovecraft.com", "1800-757-888", contact.getAddress());
        assertFalse(left.equals(right));
        assertFalse(right.equals(left));
    }

    /**
     * Ensures not equal with a different email address.
     */
    @Test
    public void shouldNotEqualWithDifferentEmail() {
        Contact left = contact;
        Contact right = new Contact(
                "H P Lovecraft", "other@lovecraft.com", "1800-757-888", contact.getAddress());
        assertFalse(left.equals(right));
        assertFalse(right.equals(left));
    }

    /**
     * Ensures not equal with a different phone number.
     */
    @Test
    public void shouldNotEqualWithDifferentPhone() {
        Contact left = contact;
        Contact right = new Contact(
                "H P Lovecraft", "hp@lovecraft.com", "0401231234", contact.getAddress());
        assertFalse(left.equals(right));
        assertFalse(right.equals(left));
    }

    /**
     * Ensures not equal with a different {@link Address}.
     */
    @Test
    public void shouldNotEqualWithDifferentAddress() {
        Contact left = contact;
        Contact right = new Contact(
                "H P Lovecraft", "hp@lovecraft.com", "1800-757-888", null);
        assertFalse(left.equals(right));
        assertFalse(right.equals(left));
    }

    /**
     * Ensures masking of the {@link Contact#toString()} method for security.
     */
    @Test
    public void shouldMaskForDebugging() {
        assertEquals("name=\"H *********ft\","
                + " email=\"hp************om\","
                + " phone=\"18********88\","
                + " address=\"Level******************************************************ralia\"",
                contact.toString());
        assertEquals("name=\"****\"", new Contact("Name").toString());
    }
}
