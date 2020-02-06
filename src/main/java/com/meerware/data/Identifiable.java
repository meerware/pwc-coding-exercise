package com.meerware.data;

import java.io.Serializable;

/**
 * Interface which defines the implementation as having an id.
 *
 * @param <I> is the identifier type.
 */
public interface Identifiable<I extends Serializable> {

    /**
     * @return the identifier.
     */
    I getId();
}
