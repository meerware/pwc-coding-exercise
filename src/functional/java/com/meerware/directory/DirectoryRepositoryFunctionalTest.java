package com.meerware.directory;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.ImmutableList;
import com.meerware.Application;

import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Functional tests for the {@link DirectoryRepository}. This is more
 * to test the {@code JPA} configuration and annotations.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
    classes =  Application.class
)
public class DirectoryRepositoryFunctionalTest {

    /**
     * Identifier fixture.
     */
    private static final UUID IDENTIFIER = randomUUID();

    /**
     * Main test object.
     */
    @Autowired
    private DirectoryRepository repository;

    /**
     * Sets up the test data.
     */
    @Before
    public void setUp() {
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
        Directory directory = new Directory(IDENTIFIER, "global", ImmutableList.of(contact));

        repository.save(directory);
    }

    /**
     * Ensures finding by an identifier.
     */
    @Test
    public void shouldFindById() {
        Optional<Directory> directory = repository.findById(IDENTIFIER);
        assertTrue(directory.isPresent());
    }
}
