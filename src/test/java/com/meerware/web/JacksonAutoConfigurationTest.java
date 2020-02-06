package com.meerware.web;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;

/**
 * Tests for the {@link JacksonAutoConfiguration}.
 */
@RunWith(MockitoJUnitRunner.class)
public class JacksonAutoConfigurationTest {

    /**
     * Test object.
     */
    @InjectMocks
    private JacksonAutoConfiguration configuration;

    /**
     * Ensures configuring of the {@link JacksonProperties}.
     */
    @Test
    public void shouldProvideJacksonProperties() {
        JacksonProperties properties = configuration.jacksonProperties();
        assertEquals("yyyy-MM-dd'T'HH:mm:ssZ", properties.getDateFormat());
    }

}
