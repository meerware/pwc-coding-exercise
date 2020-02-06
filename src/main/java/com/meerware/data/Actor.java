package com.meerware.data;

import static java.util.Optional.ofNullable;
import static javax.persistence.InheritanceType.JOINED;
import java.util.UUID;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Table;

/**
 * Base class representing an {@link Actor} in the system.
 */
@Entity
@Inheritance(strategy = JOINED)
@DiscriminatorColumn(name = "type", length = 16)
@DiscriminatorValue(Actor.TYPE)
@Table(name = "actor")
public class Actor extends AbstractEntity {

    /**
     * Type given to the base {@link Actor}.
     */
    static final String TYPE = "actor";

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1372152653104841165L;

    /**
     * Type of the {@link Actor}.
     */
    @Column(insertable = false, updatable = false)
    private String type = TYPE;

    /**
     * Default protected constructor.
     */
    protected Actor() {
        this(null, null);
    }

    /**
     * @param id is the optional identifier.
     */
    protected Actor(@Nullable UUID id) {
        this(id, null);
    }

    /**
     * @param type is the optional type, this will default to {@link #TYPE} if {@code null}.
     */
    protected Actor(@Nullable String type) {
        this(null, type);
    }

    /**
     * @param id is the optional identifier.
     * @param type is the optional type, this will default to {@link #TYPE} if {@code null}.
     */
    protected Actor(@Nullable UUID id, @Nullable String type) {
        super(id);
        this.type = ofNullable(type).orElse(TYPE);
    }

    /**
     * @return the type of {@link Actor}.
     */
    public String getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", type=\"");
        builder.append(getType());
        builder.append('"');
        return builder.toString();
    }
}
