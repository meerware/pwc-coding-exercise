package com.meerware.directory;

/**
 * Specialised exception thrown during a creation of a {@link Directory}.
 */
class DirectoryCreateException extends DirectoryException {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -2269615494867965247L;

    /**
     * @param cause is the underlying cause.
     */
    DirectoryCreateException(Throwable cause) {
        super(cause);
    }
}
