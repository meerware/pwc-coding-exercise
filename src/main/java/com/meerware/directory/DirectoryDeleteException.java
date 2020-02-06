package com.meerware.directory;

/**
 * Specialised exception thrown during a deletion of a {@link Directory}.
 */
class DirectoryDeleteException extends DirectoryException {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -1793651895746750242L;

    /**
     * @param cause is the underlying cause.
     */
    DirectoryDeleteException(Throwable cause) {
        super(cause);
    }


}
