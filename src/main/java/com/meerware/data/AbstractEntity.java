package com.meerware.data;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static com.google.common.collect.Lists.newArrayList;
import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Joiner;


/**
 * Abstract {@link Auditable} entity which adds auditing columns to an entity.
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable, Auditable, Identifiable<UUID> {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -6144668139504814112L;

    /**
     * Identifier column length.
     */
    private static final int IDENTIFIER_COLUMN_LENGTH = 36;

    /**
     * Output date format for {@link #toString()}.
     */
    private static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /**
     * Identifier.
     */
    @Id
    @org.springframework.data.annotation.Id
    @Type(type = "uuid-char")
    @GenericGenerator(name = "uuid-generator", strategy = "com.meerware.data.UniversalUniqueIdentifierGenerator")
    @GeneratedValue(generator = "uuid-generator")
    @JsonProperty(access = READ_ONLY)
    @Column(length = IDENTIFIER_COLUMN_LENGTH)
    private UUID id;

    /**
     * Creator.
     */
    @CreatedBy
    @JsonIgnore
    @OneToOne(optional = true, cascade = { REFRESH }, fetch = EAGER)
    @JoinColumn(insertable = true, updatable = false)
    private Actor creator;

    /**
     * Created timestamp.
     */
    @CreatedDate
    @Temporal(TIMESTAMP)
    @JsonProperty(access = READ_ONLY)
    @Column(insertable = true, updatable = false)
    private Date created;

    /**
     * Modifier.
     */
    @LastModifiedBy
    @JsonIgnore
    @OneToOne(optional = true, cascade = { REFRESH }, fetch = EAGER)
    private Actor modifier;

    /**
     * Modified timestamp.
     */
    @LastModifiedDate
    @Temporal(TIMESTAMP)
    @JsonProperty(access = READ_ONLY)
    private Date modified;

    /**
     * Default protected constructor.
     */
    protected AbstractEntity() {
        this(null);
    }

    /**
     * Explicit identifier constructor.
     *
     * @param id is the identifier.
     */
    protected AbstractEntity(UUID id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Actor getCreator() {
        return creator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getCreated() {
        if (created == null) {
            return null;
        }
        return new Date(created.getTime());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Actor getModifier() {
        return modifier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getModified() {
        if (modified == null) {
            return null;
        }
        return new Date(modified.getTime());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final AbstractEntity entity = (AbstractEntity) object;
        return Objects.equals(getId(), entity.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final List<String> components = newArrayList();
        final DateFormat format = new SimpleDateFormat(DATETIME_FORMAT);
        if (getId() == null) {
            components.add("id=null");
        } else {
            components.add("id=\"" + getId() + '"');
        }
        if (getCreator() != null) {
            components.add("creator=\"" + getCreator() + '"');
        }
        if (getCreated() != null) {
            components.add("created=\"" + format.format(getCreated()) + '"');
        }
        if (getModifier() != null) {
            components.add("modifier=\"" + getModifier() + '"');
        }
        if (getModified() != null) {
            components.add("modified=\"" + format.format(getModified()) + '"');
        }
        return Joiner.on(", ").join(components);
    }
}
