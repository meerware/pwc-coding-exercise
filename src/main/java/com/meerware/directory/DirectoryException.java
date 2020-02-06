package com.meerware.directory;

/**
 * Abstract, base exception thrown by the directory components.
 */
abstract class DirectoryException extends RuntimeException {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message is the exception message.
     */
    DirectoryException(String message) {
        super(message);
    }

    /**
     * @param cause is the underlying cause.
     */
    DirectoryException(Throwable cause) {
        super(cause);
    }

}
