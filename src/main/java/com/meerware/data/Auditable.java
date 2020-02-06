package com.meerware.data;

import java.util.Date;

/**
 * Interface which exposes the auditing columns.
 */
public interface Auditable {

    /**
     * @return the creator. This is the current {@link Actor}, such as a user.
     */
    Actor getCreator();

    /**
     * @return the {@link Date} created.
     */
    Date getCreated();

    /**
     * @return the modifier. This is the current {@link Actor}, such as a user.
     */
    Actor getModifier();

    /**
     * @return the {@link Date} modified.
     */
    Date getModified();
}
