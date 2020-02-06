package com.meerware.data;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.EntityPersister;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Tests for the {@link UniversalUniqueIdentifierGenerator}.
 */
@RunWith(MockitoJUnitRunner.class)
public class UniversalUniqueIdentifierGeneratorTest {

    /**
     * Main test object.
     */
    private UniversalUniqueIdentifierGenerator generator;

    /**
     * {@link Mock} {@link SharedSessionContractImplementor}.
     */
    @Mock
    private SharedSessionContractImplementor session;

    /**
     * {@link Mock} {@link ClassMetadata}.
     */
    @Mock
    private ClassMetadata metadata;

    /**
     * Sets up the main test object and mocks.
     */
    @Before
    public void setUp() {
        generator = new UniversalUniqueIdentifierGenerator();
        EntityPersister persister = mock(EntityPersister.class);
        when(persister.getClassMetadata()).thenReturn(metadata);


        when(session.getEntityPersister(Mockito.<String>any(), any(Class.class))).thenReturn(persister);
    }

    /**
     * Ensures generation of an identifier when not already set.
     */
    @Test
    public void shouldGenerateIdentifier() {
        Serializable identifier = generator.generate(session, Object.class);
        assertNotNull(identifier);
        assertTrue(identifier instanceof UUID);
    }

    /**
     * Ensures honouring of an explicitly set identifier.
     */
    @Test
    public void shouldHonourExistingIdentifier() {
        UUID id = randomUUID();
        when(metadata.getIdentifier(Object.class, session)).thenReturn(id);
        Serializable identifier = generator.generate(session, Object.class);
        assertNotNull(identifier);
        assertEquals(id, identifier);
    }
}
