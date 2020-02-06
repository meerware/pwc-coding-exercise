package com.meerware.directory;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.google.common.collect.ComparisonChain.start;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.overlay;
import static org.apache.commons.lang3.StringUtils.repeat;
import static org.apache.commons.lang3.StringUtils.wrap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <p>{@link Embeddable} {@link Contact}. This is a simplified representation
 * of an address book directory contact. Normally the name would be broken
 * into first and last name and would be one to many for emails, addresses and phone numbers.
 * This also would not be an {@link Embeddable} but rather a standalone entity.</p>
 *
 * <p>Equals and comparsion methods are defined on all attributes, not just name.</p>
 *
 * <p>Instances are immutable.</p>
 */
@JsonInclude(value = NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Embeddable
class Contact implements Comparable<Contact>, Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 3988759242874418728L;

    /**
     * Maximum name length.
     */
    private static final int MAXIMUM_NAME_LENGTH = 128;

    /**
     * Maximum email length.
     */
    private static final int MAXIMUM_EMAIL_LENGTH = 384;

    /**
     * Maximum phone length.
     */
    private static final int MAXIMUM_PHONE_LENGTH = 32;

    /**
     * Masking character for attributes for logging.
     */
    private static final String MASK = "*";

    /**
     * Most number of characters which are left unmasked at the start and end of attributes for logging.
     */
    private static final int UNMASKED_LENGTH = 2;

    /**
     * Name attribute. This is required and not {@code null}.
     */
    @NotBlank
    @Column(length = MAXIMUM_NAME_LENGTH, name = "`name`")
    @Size(max = MAXIMUM_NAME_LENGTH)
    private final String name;

    /**
     * Email address, can be {@code null}.
     */
    @Email
    @Nullable
    @Column(length = MAXIMUM_EMAIL_LENGTH)
    @Size(max = MAXIMUM_EMAIL_LENGTH)
    private final String email;

    /**
     * Phone number. This can be {@code null}.
     */
    @Phone
    @Nullable
    @Column(length = MAXIMUM_PHONE_LENGTH)
    @Size(max = MAXIMUM_PHONE_LENGTH)
    private final String phone;

    /**
     * Physical {@link Address}. This can be {@link Address#EMPTY}.
     */
    @Embedded
    @Valid
    private final Address address;

    /**
     * Default package private constructor.
     */
    Contact() {
        this(null, null, null, null);
    }

    /**
     * @param name is the name.
     */
    Contact(String name) {
        this(name, null, null, null);
    }

    /**
     * @param name is the name.
     * @param email is the email address.
     * @param phone is the phone number.
     * @param address is the {@link Address}.
     */
    @JsonCreator
    Contact(@JsonProperty("name") String name,
            @JsonProperty("email") String email,
            @JsonProperty("phone") String phone,
            @JsonProperty("address") Address address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = ofNullable(address).orElse(Address.EMPTY);
    }

    /**
     * @return the name of the contact. This should not return {@code null}.
     */
    @Nullable
    public String getName() {
        return name;
    }

    /**
     * @return the email address. This might return {@code null}.
     */
    @Nullable
    public String getEmail() {
        return email;
    }

    /**
     * @return the phone number. This might return {@code null}
     *         if no number has been set.
     */
    @Nullable
    public String getPhone() {
        return phone;
    }

    /**
     * @return the {@link Address} if set, {@code null} otherwise.
     */
    @Nullable
    public Address getAddress() {
        return address;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Contact contact) {
        return start()
                .compare(defaultString(getName()), defaultString(contact.getName()))
                .compare(defaultString(getEmail()), defaultString(contact.getEmail()))
                .compare(defaultString(getPhone()), defaultString(contact.getPhone()))
                .compare(getAddress(), contact.getAddress())
                .result();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        final Contact contact = (Contact) object;
        return Objects.equals(getName(), contact.getName())
                && Objects.equals(getEmail(), contact.getEmail())
                && Objects.equals(getPhone(), contact.getPhone())
                && Objects.equals(getAddress(), contact.getAddress());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getName(), getEmail(), getPhone(), getAddress());
    }

    /**
     * @return the display component.
     */
    @JsonProperty(access = Access.READ_ONLY,  value = "display")
    String toDisplay() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        Function<String, String> mask = value -> {
            final String masked;
            if (value.length() <= UNMASKED_LENGTH + UNMASKED_LENGTH) {
                // Full masking
                masked = repeat(MASK, value.length());
            } else {
                // Partial masking
                masked = overlay(
                        value,
                        repeat(MASK, value.length() - UNMASKED_LENGTH - UNMASKED_LENGTH),
                        UNMASKED_LENGTH,
                        value.length() - UNMASKED_LENGTH);
            }
            return masked;
        };

        final StringBuilder builder = new StringBuilder();
        builder.append("name=");
        builder.append(wrap(mask.apply(getName()), '"'));

        if (isNotBlank(getEmail())) {
            builder.append(", email=");
            builder.append(wrap(mask.apply(getEmail()), '"'));
        }

        if (isNotBlank(getPhone())) {
            builder.append(", phone=");
            builder.append(wrap(mask.apply(getPhone()), '"'));
        }

        if (!getAddress().equals(Address.EMPTY)) {
            builder.append(", address=");
            builder.append(wrap(getAddress().toString(), '"'));
        }
        return builder.toString();
    }
}
