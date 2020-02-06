package com.meerware.directory;

/**
 * Specialised exception thrown if during reads fail.
 */
class DirectoryReadException extends DirectoryException {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1052166331168084545L;

    /**
     * @param cause is the underlying cause.
     */
    DirectoryReadException(Throwable cause) {
        super(cause);
    }

}
