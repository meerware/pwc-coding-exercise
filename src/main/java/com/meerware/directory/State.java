package com.meerware.directory;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Nullable;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;


/**
 * Wrapper around a {@link State} or province. This handles short version of some values and behaves a bit like
 * an enumeration.
 */
class State implements Comparable<State>, Serializable {


    /**
     * Australian Capital Territory {@link State}.
     */
    public static final State AUSTRALIAN_CAPITAL_TERRITORY = new State(
            "ACT", "Australian Capital Territory");

    /**
     * New South Wales {@link State}.
     */
    public static final State NEW_SOUTH_WALES = new State(
            "NSW", "New South Wales");

    /**
     * Northern Territory {@link State}.
     */
    public static final State NORTHERN_TERRITORY = new State(
            "NT", "Northern Territory");

    /**
     * Queensland {@link State}.
     */
    public static final State QUEENSLAND = new State(
            "QLD", "Queensland");

    /**
     * South Australia {@link State}.
     */
    public static final State SOUTH_AUSTRALIA = new State(
            "SA", "South Australia");

    /**
     * Tasmamia {@link State}.
     */
    public static final State TASMANIA = new State(
            "TAS", "Tasmania");

    /**
     * Victoria {@link State}.
     */
    public static final State VICTORIA = new State(
            "VIC", "Victoria");

    /**
     * Western Australia {@link State}.
     */
    public static final State WESTERN_AUSTRALIA = new State(
            "WA", "Western Australia");

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -4411744890145130219L;

    /**
     * Maximum length of the name.
     */
    private static final int MAXIMUM_NAME_LENGTH = 128;

    /**
     * Known {@link State} objects.
     */
    private static Set<State> states = ImmutableSet.<State>builder()
            .add(AUSTRALIAN_CAPITAL_TERRITORY)
            .add(NEW_SOUTH_WALES)
            .add(NORTHERN_TERRITORY)
            .add(QUEENSLAND)
            .add(SOUTH_AUSTRALIA)
            .add(TASMANIA)
            .add(VICTORIA)
            .add(WESTERN_AUSTRALIA)
            .build();

    /**
     * Short code version of the {@link State}. Maybe {@code null}.
     */
    @Nullable
    private final String code;

    /**
     * Long name of the {@link State}.
     */
    private final String name;

    /**
     * @param code is the optional short code.
     * @param name is the long name.
     */
    State(@Nullable String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * @return the name of the {@link State}. This will not return {@code null}.
     */
    @Size(max = MAXIMUM_NAME_LENGTH, message = "State is too long")
    public String getName() {
        return name;
    }

    /**
     * @return the optional short code of the {@link State}.
     */
    @Nullable
    public String getCode() {
        return code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getName());
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
        final State state = (State) object;
        return Objects.equals(getName(), state.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(State state) {
        return this.getName().compareTo(state.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @JsonValue
    public String toString() {
        return getName();
    }

    /**
     * @param value is the {@link String} form of the value.
     * @return a {@link State} or {@code null} if the input value was {@code blank}.
     */
    @JsonCreator
    @Nullable
    static State parse(@Nullable String value) {
        if (isBlank(value)) {
            return null;
        }
        final String search = value.trim();
        return states.stream()
                .filter(candidate -> candidate.getName().equalsIgnoreCase(search)
                        || candidate.getCode().equalsIgnoreCase(search))
                .findFirst().orElseGet(() -> new State(null, search));
    }


}
