package com.meerware.directory;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.google.common.collect.ImmutableList;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Tests for the {@link DirectoryController}.
 */
@RunWith(MockitoJUnitRunner.class)
public class DirectoryControllerTest {

    /**
     * Identifier fixture used for test input.
     */
    private static final UUID IDENTIFIER = randomUUID();

    /**
     * Main test object.
     */
    @InjectMocks
    private DirectoryController controller;

    /**
     * {@link Mock} {@link DirectoryService}.
     */
    @Mock
    private DirectoryService service;


    /**
     * Ensures getting a {@link Directory}.
     */
    @Test
    public void shouldGetDirectory() {
        controller.get(IDENTIFIER);
        verify(service, times(1)).get(IDENTIFIER);
    }

    /**
     * Ensures creation of a {@link Directory}.
     */
    @Test
    public void shouldCreateDirectory() {
        controller.create(new Directory());
        verify(service, times(1)).create(any(Directory.class));
    }

    /**
     * Ensures update of a {@link Directory}.
     */
    @Test
    public void shouldUpdateDirectory() {
        ArgumentCaptor<Directory> captor = forClass(Directory.class);
        Directory directory = new Directory(IDENTIFIER, "update", ImmutableList.of());
        controller.update(IDENTIFIER, directory);
        verify(service, times(1)).update(captor.capture());
        assertEquals(IDENTIFIER, captor.getValue().getId());
    }

    /**
     * Ensures deletion of a {@link Directory}.
     */
    @Test
    public void shouldDeleteDirectory() {
        controller.delete(IDENTIFIER);
        verify(service, times(1)).delete(IDENTIFIER);
    }
}
