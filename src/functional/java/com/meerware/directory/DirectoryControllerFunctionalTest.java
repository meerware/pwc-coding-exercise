package com.meerware.directory;

import static com.google.common.collect.Lists.newArrayList;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static java.util.UUID.randomUUID;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.google.common.collect.ImmutableList;
import com.meerware.Application;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
    classes =  Application.class,
    webEnvironment = DEFINED_PORT,
    properties = {
         "server.port=" + DirectoryControllerFunctionalTest.PORT
    }
)
public class DirectoryControllerFunctionalTest {

    /**
     * Port number the test server runs on.
     */
    static final int PORT = 43928;

    /**
     * {@link DirectoryRepository} used to populate and test
     * data.
     */
    @Autowired
    private DirectoryRepository repository;

    /**
     * Stored {@link Directory}.
     */
    private Directory directory;

    /**
     * Sets up the port for {@link io.restassured.RestAssured}.
     */
    @BeforeClass
    public static void setUpClass() {
        port = PORT;
    }

    /**
     * Sets up test data.
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
        directory = repository.save(new Directory("global", ImmutableList.of(contact)));
    }

    /**
     * Ensures successful getting an existing {@link Directory}.
     */
    @Test
    public void shouldGetExistingDirectory() {
        given()
            .get("/directories/" + directory.getId())
       .then()
            .statusCode(SC_OK)
            .contentType(APPLICATION_JSON_VALUE)
            .body("data.id", equalTo(directory.getId().toString()))
            .body("data.name", equalTo("global"));
    }

    /**
     * Ensures failure to get a non existing {@link Directory}.
     */
    @Test
    public void shouldFailGettingNonExistingDirectory() {
        given()
            .get("/directories/" + randomUUID())
       .then()
            .statusCode(SC_NOT_FOUND)
            .body("errors[0].status", equalTo("404"));
    }
}
