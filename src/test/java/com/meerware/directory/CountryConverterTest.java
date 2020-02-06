package com.meerware.directory;

import static com.meerware.directory.Country.AUSTRALIA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Tests for the {@link CountryConverter}.
 */
@RunWith(MockitoJUnitRunner.class)
public class CountryConverterTest {

    /**
     * Main test object.
     */
    @InjectMocks
    private CountryConverter converter;

    /**
     * Ensures conversion to database column.
     */
    @Test
    public void shouldConvertToDatabaseColumn() {
        assertNull(converter.convertToDatabaseColumn(null));
        assertEquals("AU", converter.convertToDatabaseColumn(AUSTRALIA));
    }

    /**
     * Ensures conversion from database to entity attribute.
     */
    @Test
    public void shouldConvertToEntityAttribute() {
        assertEquals(AUSTRALIA,
                converter.convertToEntityAttribute("AU"));
    }

}
