package com.meerware.directory;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for handling {@code CRUD} input of {@link Directory} objects.
 */
@RestController
@RequestMapping(
        path = "/directories")
class DirectoryController {

    /**
     * {@link DirectoryService} used to create, get, update and delete {@link Directory} entities.
     */
    private DirectoryService service;

    /**
     * @param service is the {@link DirectoryService}.
     */
    @Autowired
    DirectoryController(DirectoryService service) {
        this.service = service;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    Directory create(@RequestBody Directory directory) throws DirectoryException {
        return service.create(directory);
    }

    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    Directory get(@PathVariable("id") UUID id) throws DirectoryException {
        return service.get(id);
    }

    @PutMapping(
            path = "/{id}",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    Directory update(@PathVariable("id") UUID id, @RequestBody Directory directory) throws DirectoryException {
        // Enforce the path variable identifier
        return service.update(new Directory(id, directory.getName(), directory.getContacts()));
    }

    @DeleteMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    void delete(UUID id) throws DirectoryException {
        service.delete(id);
    }
}
