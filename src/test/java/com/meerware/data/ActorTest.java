package com.meerware.data;

import static java.util.UUID.fromString;
import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

/**
 * Tests for the {@link Actor}.
 */
public class ActorTest {

    /**
     * Identifier fixture.
     */
    private static final UUID IDENTIFIER = fromString("9902d94f-543b-4d4c-8155-d0b77567937c");

    /**
     * Ensures a good debug {@link Actor#toString()}.
     */
    @Test
    public void shouldBeDebuggable() {
        Actor actor = new Actor(IDENTIFIER);
        assertEquals("id=\"9902d94f-543b-4d4c-8155-d0b77567937c\", type=\"actor\"", actor.toString());
    }

}
