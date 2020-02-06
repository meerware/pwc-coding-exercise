package com.meerware.directory;

/**
 * Specialised exception thrown if an update fails.
 */
class DirectoryUpdateException extends DirectoryException {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -7928076858521820089L;

    /**
     * @param cause is the underlying cause.
     */
    DirectoryUpdateException(Throwable cause) {
        super(cause);
    }


}
