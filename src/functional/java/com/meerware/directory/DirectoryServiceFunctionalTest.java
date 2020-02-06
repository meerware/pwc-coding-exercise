package com.meerware.directory;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import com.google.common.collect.ImmutableList;
import com.meerware.Application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Functional tests for the {@link DirectoryService}.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
    classes =  Application.class
)
public class DirectoryServiceFunctionalTest {

    /**
     * Main test object.
     */
    @Autowired
    private DirectoryService service;

    /**
     * {@link DirectoryRepository} used populate and test
     * stored {@link Directory} entities.
     */
    @Autowired
    private DirectoryRepository repository;

    /**
     * Stored {@link Directory}.
     */
    private Directory directory;

    /**
     * Sets up the test data.
     */
    @Before
    public void setUp()  {
        // Address
        Address address = new Address();
        address.setCountry(Country.AUSTRALIA);
        address.setState(State.VICTORIA);
        address.setLocality("Melbourne");
        address.setPostcode("3000");
        address.setLines(newArrayList("100 Collins Street"));

        // Contact
        Contact contact = new Contact(
                "H P Lovecraft", "hp@lovecraft.com", "1800-757-888", address);

        // Directory
        directory = repository.save(new Directory("global", ImmutableList.of(contact)));
    }

    /**
     * Ensures getting an existing {@link Directory}.
     */
    @Test
    public void shouldGetExistingDirectory() {
        assertNotNull(service.get(directory.getId()));
    }

    /**
     * Ensures failure to get a non existing {@link Directory}.
     */
    @Test(expected = DirectoryNotFoundException.class)
    public void shouldFailGettingNonExistingDirectory() {
        service.get(randomUUID());
    }

    /**
     * Ensures creating a {@link Directory}.
     */
    @Test
    public void shouldCreateDirectory() {
        Directory directory = service.create(new Directory("name", ImmutableList.of()));
        assertNotNull(directory.getId());
    }

    /**
     * Ensures updating an existing {@link Directory}.
     */
    @Test
    public void shouldUpdateExistingDirectory() {
        service.update(
                new Directory(this.directory.getId(), "update", ImmutableList.of()));
    }

    /**
     * Ensures failure to update a non existing {@link Directory}.
     */
    @Test(expected = DirectoryNotFoundException.class)
    public void shouldFailUpdatingNonExistingDirectory() {
        service.update(
                new Directory(randomUUID(), "update", ImmutableList.of()));
    }

    /**
     * Ensures deleting an existing {@link Directory}.
     */
    @Test
    public void shouldDeleteExistingDirectory() {
        service.delete(directory.getId());
        assertFalse(repository.existsById(directory.getId()));
    }

    /**
     * Ensures failure to delete a non existing {@link Directory}.
     */
    @Test(expected = DirectoryNotFoundException.class)
    public void shouldFailDeletingNonExistingDirectory() {
        service.delete(randomUUID());
    }
}
