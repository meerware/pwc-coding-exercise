package com.meerware.directory;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.google.common.collect.ImmutableList.copyOf;
import static javax.persistence.AccessType.FIELD;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.overlay;
import static org.apache.commons.lang3.StringUtils.repeat;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * {@link Address}. Outside the package, this is immutable.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Embeddable
@JsonInclude(NON_EMPTY)
@javax.persistence.Access(FIELD)
class Address implements Comparable<Address>, Serializable {

    /**
     * Empty {@link Address}.
     */
    static final Address EMPTY = new Address();

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -3945664514535833973L;

    /**
     * Maximum lines length for the column.
     */
    private static final int MAXIMUM_LINES_LENGTH = 512;

    /**
     * Validation maximum number of lines.
     */
    private static final int MAXIMUM_LINES = 5;

    /**
     * Maximum length of the locality.
     */
    private static final int MAXIMUM_LOCALITY_LENGTH = 128;

    /**
     * Maximum length of the postcode.
     */
    private static final int MAXIMUM_POSTCODE_LENGTH = 16;

    /**
     * Maximum length of the state.
     */
    private static final int MAXIMUM_STATE_LENGTH = 128;

    /**
     * Maximum length of the country.
     */
    private static final int MAXIMUM_COUNTRY_LENGTH = 2;

    /**
     * Masking character.
     */
    private static final String MASK = "*";

    /**
     * Maximum length of unmasked characters in the {@link #toString()} method which are exposed.
     */
    private static final int UNMASKED_LENGTH = 5;

    /**
     * Lines of the address. This is held in a single string which is delimited.
     * Explicitly defines the column to avoid getting mixed up with validation annotations.
     */
    @Column(length = MAXIMUM_LINES_LENGTH,
            name = "`lines`",
            columnDefinition = "varchar(" + MAXIMUM_LINES_LENGTH + ")")
    private String lines;

    /**
     * Suburb or town name.
     */
    @Column(length = MAXIMUM_LOCALITY_LENGTH)
    @Size(max = MAXIMUM_LOCALITY_LENGTH)
    private String locality;

    /**
     * Postcode component.
     */
    @Column(length = MAXIMUM_POSTCODE_LENGTH)
    @Size(max = MAXIMUM_POSTCODE_LENGTH)
    private String postcode;

    /**
     * {@link State} component.
     */
    @Column(length = MAXIMUM_STATE_LENGTH, name = "state")
    @Valid
    private State state;

    /**
     * {@link Country} component.
     */
    @Column(length = MAXIMUM_COUNTRY_LENGTH)
    private Country country;

    /**
     * Default, package private constructor.
     */
    Address() {
        super();
    }

    /**
     * @return an immutable {@link List} of the {@link Address} lines.
     */
    @Size(max = MAXIMUM_LINES)
    public List<String> getLines() {
        if (isBlank(lines)) {
            return ImmutableList.of();
        }
        return copyOf(Splitter.on(',').trimResults().split(lines));
    }

    /**
     * @param lines is the {@link Iterable} of lines.
     */
    void setLines(Iterable<String> lines) {
        if (lines == null) {
            this.lines = null;
            return;
        }

        this.lines = Joiner.on(", ").skipNulls().join(lines);
    }

    /**
     * @return the locality which is the suburb or town name.
     */
    public String getLocality() {
        return locality;
    }

    /**
     * @param locality is the locality name to set.
     */
    void setLocality(String locality) {
        this.locality = locality;
    }

    /**
     * @return the postcode.
     */

    public String getPostcode() {
        return postcode;
    }

    /**
     * @param postcode is the postcode to set.
     */
    void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * @return the {@link State}.
     */
    @Nullable
    public State getState() {
        return state;
    }

    /**
     * @param state is the {@link State} to set.
     */
    void setState(State state) {
        this.state = state;
    }

    /**
     * @return the {@link Country}. This can return {@code null}.
     */
    public Country getCountry() {
        return country;
    }

    /**
     * @param country is the {@link Country} to set.
     */
    void setCountry(@Nullable Country country) {
        this.country = country;
    }


    /**
     * @return the address as a single line for display purposes.
     */
    @JsonProperty(access = Access.READ_ONLY, value = "display")
    String toDisplay() {
        final List<Object> components = Lists.newArrayList(lines);
        components.add(getLocality());
        if (StringUtils.isNotBlank(getPostcode()) && getState() != null) {
            components.add(getState() + " " + getPostcode());
        } else {
            components.add(getState());
            components.add(getPostcode());
        }
        components.add(getCountry());
        return Joiner.on(", ").skipNulls().join(components);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(lines, getLocality(), getState(), getPostcode(), getCountry());
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
        final Address address = (Address) object;
        return Objects.equals(lines, address.lines)
                && Objects.equals(locality, address.locality)
                && Objects.equals(state, address.state)
                && Objects.equals(postcode, address.postcode)
                && Objects.equals(country, address.country);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Address address) {
        return this.toDisplay().compareTo(address.toDisplay());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String display = this.toDisplay();
        if (display.length() <= UNMASKED_LENGTH + UNMASKED_LENGTH) {
            return repeat(MASK, display.length());
        } else {
            return overlay(
                    display,
                    repeat(MASK, display.length() - UNMASKED_LENGTH - UNMASKED_LENGTH),
                    UNMASKED_LENGTH, display.length() - UNMASKED_LENGTH);
        }
    }
}
