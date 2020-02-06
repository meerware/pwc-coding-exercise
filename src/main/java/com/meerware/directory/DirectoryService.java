package com.meerware.directory;

import static com.google.common.base.Throwables.throwIfInstanceOf;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * {@link Service} for accessing {@link Directory} entities.
 * This offers the {@code CRUD} operations.
 */
@Service
@Validated
class DirectoryService {

    /**
     * Log instance.
     */
    private static final Logger LOG = getLogger(DirectoryService.class);

    /**
     * Underlying {@link DirectoryRepository} used for storage.
     */
    private final DirectoryRepository repository;

    /**
     * @param repository is the {@link DirectoryRepository}.
     */
    @Autowired
    DirectoryService(DirectoryRepository repository) {
        this.repository = repository;
    }

    /**
     * Attempts to get a {@link Directory} by identifier.
     *
     * @param id is the identifier of the {@link Directory}. This cannot be {@code null}.
     * @return the found {@link Directory}.
     * @throws DirectoryException if there is a problem retrieving the {@link Directory}
     *         or if the {@link Directory} cannot be found.
     */
    Directory get(@NotNull UUID id) throws DirectoryException {
        try {
            LOG.info("Attempting to read directory: id=\"{}\"", id);
            final Directory directory = repository.findById(id)
                    .orElseThrow(() -> {
                        LOG.info("Directory not found: id=\"{}\"", id);
                        return new DirectoryNotFoundException(id);
                    });

            LOG.info("Successfully read directory: {}", directory);
            return directory;
        } catch (Exception exception) {
            throwIfInstanceOf(exception, DirectoryException.class);
            LOG.error("Failed to read directory", exception);
            throw new DirectoryReadException(exception);
        }
    }

    /**
     * Attempts to create a {@link Directory} in the underlying repository layer.
     *
     * @param directory is the {@link Directory} input.
     * @return the created {@link Directory}.
     * @throws DirectoryException if there is a problem writing the {@link Directory}.
     */
    @Transactional
    Directory create(@Valid Directory directory) throws DirectoryException {
        try {
            LOG.info("Attempting to create directory: {}", directory);
            final Directory result = repository.save(directory);
            LOG.info("Successfully created directory: {}", result);
            return result;
        } catch (Exception exception) {
            LOG.error("Failed to create directory", exception);
            throw new DirectoryCreateException(exception);
        }
    }

    /**
     * Attempts to update an existing {@link Directory}.
     *
     * @param directory is the {@link Directory} to update.
     * @return the updated {@link Directory}.
     * @throws DirectoryException if there is a problem writing the {@link Directory} or
     *         if no {@link Directory} exists to be updated.
     */
    @Transactional
    Directory update(@Valid Directory directory) throws DirectoryException {
        try {
            LOG.info("Attempting to update directory: {}", directory);
            if (!repository.existsById(directory.getId())) {
                LOG.info("Directory not found: id=\"{}\"", directory.getId());
                throw new DirectoryNotFoundException(directory.getId());
            }
            final Directory result = repository.save(directory);
            LOG.info("Successfully updated directory: {}", directory);
            return result;
        } catch (Exception exception) {
            throwIfInstanceOf(exception, DirectoryException.class);
            LOG.error("Failed to update directory", exception);
            throw new DirectoryUpdateException(exception);
        }
    }

    /**
     * Attempts to delete a {@link Directory} by identifier.
     *
     * @param id is the identifier of the {@link Directory}. This cannot be {@code null}.
     * @throws DirectoryException if there is a problem deleting the {@link Directory}
     *         or if the {@link Directory} cannot be found.
     */
    @Transactional
    void delete(@NotNull UUID id) throws DirectoryException {
        try {
            LOG.info("Attempting to delete directory: id=\"{}\"", id);
            if (!repository.existsById(id)) {
                LOG.info("Directory not found: id=\"{}\"", id);
                throw new DirectoryNotFoundException(id);
            }
            repository.deleteById(id);
            LOG.info("Successfully deleted directory: id=\"{}\"", id);
        } catch (Exception exception) {
            throwIfInstanceOf(exception, DirectoryException.class);
            LOG.error("Failed to delete directory", exception);
            throw new DirectoryDeleteException(exception);
        }
    }
}
