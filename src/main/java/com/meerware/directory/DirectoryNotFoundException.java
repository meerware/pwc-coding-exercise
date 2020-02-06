package com.meerware.directory;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.UUID;

import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when looking up a {@link Directory} which does not exist.
 * This maps to a {@code 404} response.
 */
@ResponseStatus(NOT_FOUND)
class DirectoryNotFoundException extends DirectoryException {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 2596907236528985307L;

    /**
     * @param id is the identifier of the directory not found.
     */
    DirectoryNotFoundException(UUID id) {
        super(String.format("Directory not found: %s", id));
    }

}
