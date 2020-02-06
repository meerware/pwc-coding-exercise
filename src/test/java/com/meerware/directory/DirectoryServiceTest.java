package com.meerware.directory;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableList;

import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Tests for the {@link DirectoryService}.
 */
@RunWith(MockitoJUnitRunner.class)
public class DirectoryServiceTest {

    /**
     * Identifier fixture.
     */
    private static final UUID IDENTIFIER = UUID.randomUUID();

    /**
     * Main test object.
     */
    @InjectMocks
    private DirectoryService service;

    /**
     * {@link Mock} {@link DirectoryRepository}.
     */
    @Mock
    private DirectoryRepository repository;

    /**
     * Sets up the mocks.
     */
    @Before
    public void setUp() {
        Directory directory = new Directory(IDENTIFIER, "global", ImmutableList.of());
        when(repository.existsById(IDENTIFIER)).thenReturn(true);
        when(repository.findById(IDENTIFIER)).thenReturn(Optional.of(directory));
        when(repository.save(any(Directory.class)))
            .then(invocation -> {
                Directory input = invocation.getArgument(0);
                if (input.getId() == null) {
                    return new Directory(randomUUID(), input.getName(), input.getContacts());
                }
                return invocation.getArgument(0);
            });
    }

    /**
     * Ensures getting an existing {@link Directory}.
     */
    @Test
    public void shouldGetExistingDirectory() {
        Directory result = service.get(IDENTIFIER);
        assertNotNull(result);
        assertEquals(IDENTIFIER, result.getId());
    }

    /**
     * Ensures failure when trying to get a non existing {@link Directory}.
     */
    @Test(expected = DirectoryNotFoundException.class)
    public void shouldFailGettingNonExistingDirectory() {
        service.get(UUID.randomUUID());
    }

    /**
     * Ensures failure when the underlying repository fails.
     */
    @Test(expected = DirectoryReadException.class)
    public void shouldFailGettingDirectoryWhenRepositoryFails() {
        when(repository.findById(IDENTIFIER)).thenThrow(new RuntimeException());
        service.get(IDENTIFIER);
    }

    /**
     * Ensures creation of a {@link Directory}.
     */
    @Test
    public void shouldCreateDirectory() {
        service.create(new Directory());
        verify(repository, times(1)).save(any(Directory.class));
    }

    /**
     * Ensures failure to create a {@link Directory} when the underlying repository fails.
     */
    @Test(expected = DirectoryCreateException.class)
    public void shouldFailCreatingDirectoryWhenRepositoryFails() {
        when(repository.save(any(Directory.class))).thenThrow(new RuntimeException());
        service.create(new Directory());
    }

    /**
     * Ensures updating an existing {@link Directory}.
     */
    @Test
    public void shouldUpdateExistingDirectory() {
        service.update(new Directory(IDENTIFIER, "new", ImmutableList.of()));
        verify(repository, times(1)).save(any(Directory.class));
    }

    /**
     * Ensures failure when trying to update a {@link Directory} which does not exist.
     */
    @Test(expected = DirectoryNotFoundException.class)
    public void shouldFailUpdatingNonExistingDirectory() {
        service.update(new Directory(randomUUID(), "hello", ImmutableList.of()));
    }

    /**
     * Ensures when underlying repository fails to delete, exception is thrown.
     */
    @Test(expected = DirectoryUpdateException.class)
    public void shouldFailUpdatingDirectoryWhenRepositoryFails() {
        doThrow(new RuntimeException()).when(repository).save(any(Directory.class));
        service.update(new Directory(IDENTIFIER, "update", ImmutableList.of()));
    }

    /**
     * Ensures deleting of an existing {@link Directory}.
     */
    @Test
    public void shouldDeleteExistingDirectory() {
        service.delete(IDENTIFIER);
        verify(repository, times(1)).deleteById(IDENTIFIER);
    }

    /**
     * Ensures failure when trying to delete a {@link Directory} that does not exist.
     */
    @Test(expected = DirectoryNotFoundException.class)
    public void shouldFailDeletingNonExistingDirectory() {
        service.delete(randomUUID());
    }

    /**
     * Ensures failure to delete a {@link Directory} when the underlying repository fails.
     */
    @Test(expected = DirectoryDeleteException.class)
    public void shouldFailDeletingDirectoryWhenRepositoryFails() {
        doThrow(new RuntimeException()).when(repository).deleteById(IDENTIFIER);
        service.delete(IDENTIFIER);
    }
}
