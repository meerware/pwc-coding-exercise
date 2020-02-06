package com.meerware.directory;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.google.common.collect.Sets.newTreeSet;
import static org.flywaydb.core.internal.util.StringUtils.wrap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.meerware.data.AbstractEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * A {@link Directory} of {@link Contact} entities.
 */
@Entity
@Table(name = "directory")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_EMPTY)
class Directory extends AbstractEntity {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -4042833720838924902L;

    /**
     * Maximum length of the name.
     */
    private static final int MAXIMUM_NAME_LENGTH = 64;

    /**
     * Name of the {@link Directory}.
     */
    @Nullable
    @Column(name = "`name`", length = MAXIMUM_NAME_LENGTH, nullable = true)
    @Size(max = MAXIMUM_NAME_LENGTH)
    private String name;

    /**
     * {@link Set} holding the {@link Contact}.
     */
    @ElementCollection
    @JoinTable(
                name = "directory_contact",
                joinColumns = @JoinColumn(name = "directory_id")
            )
    @OrderBy("name ASC")
    private Set<Contact> contacts = newTreeSet();

    /**
     * Default constructor.
     */
    Directory() {
        super();
    }

    /**
     * @param contacts is the {@link Iterable} of {@link Contact} entities. This
     *        cannot be {@code null}.
     */
    Directory(Iterable<Contact> contacts) {
        this(null, contacts);
    }

    /**
     * {@link JsonCreator} constructor.
     *
     * @param name is the optional name.
     * @param contacts is the {@link Iterable} of {@link Contact} entities. This
     *        cannot be {@code null}.
     */
    @JsonCreator
    Directory(
            @JsonProperty("name") @Nullable String name,
            @JsonProperty("contacts") Iterable<Contact> contacts) {
        this(null, name, contacts);
    }

    /**
     * @param id is the explicitly set identifier.
     * @param name is the optional name.
     * @param contacts is the {@link Iterable} of {@link Contact} entities. This
     *        cannot be {@code null}.
     */
    Directory(UUID id, @Nullable String name, Iterable<Contact> contacts) {
        super(id);
        Iterables.addAll(this.contacts, contacts);
        this.name = name;
    }


    /**
     * Calculates the set union of contacts with the given input
     * {@link Directory}.
     *
     * @param directory is the other {@link Directory} to calculate the union with.
     * @return a new {@link Directory} with the union of the two {@link Directory}
     *         {@link Contact} entities.
     */
    Directory union(Directory directory) {
        return new Directory(Sets.union(contacts, directory.contacts));
    }

    /**
     * Calculates the set intersection of contacts with the given input
     * {@link Directory}.
     *
     * @param directory is the other {@link Directory} to calculate the intersection with.
     * @return a new {@link Directory} with only the intersected {@link Contact} entities.
     */
    Directory intersection(Directory directory) {
        return new Directory(Sets.intersection(contacts, directory.contacts));
    }

    /**
     * @return the optional name. This can return {@code null}.
     */
    @Nullable
    @JsonGetter
    String getName() {
        return name;
    }

    /**
     * @return an immutable {@link List} of {@link Contact} entities attached.
     */
    @JsonGetter
    List<Contact> getContacts() {
        return ImmutableList.copyOf(contacts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        builder.append(", name=");
        builder.append(wrap(getName(), '"'));

        if (!contacts.isEmpty()) {
            builder.append(", contacts=[{");
            builder.append(Joiner.on("}, {").join(contacts));
            builder.append("}]");
        }

        return builder.toString();
    }
}
