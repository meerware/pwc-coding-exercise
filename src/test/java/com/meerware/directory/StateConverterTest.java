package com.meerware.directory;

import static com.meerware.directory.State.VICTORIA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Tests for the {@link StateConverter}.
 */
@RunWith(MockitoJUnitRunner.class)
public class StateConverterTest {

    /**
     * Main test object.
     */
    @InjectMocks
    private StateConverter converter;

    /**
     * Ensures conversion to database column.
     */
    @Test
    public void shouldConvertToDatabaseColumn() {
        assertNull(converter.convertToDatabaseColumn(null));
        assertEquals("VIC", converter.convertToDatabaseColumn(VICTORIA));
        assertEquals("WI", converter.convertToDatabaseColumn(State.parse("WI")));
    }

    /**
     * Ensures conversion from database to entity attribute.
     */
    @Test
    public void shouldConvertToEntityAttribute() {
        assertEquals(State.VICTORIA,
                converter.convertToEntityAttribute("VIC"));
    }

}
