package com.meerware.data;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.domain.AuditorAware;

/**
 * Tests for the {@link ActorAuditorAwareBeanPostProcessor}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ActorAuditorAwareBeanPostProcessorTest {

    /**
     * Main test object.
     */
    @InjectMocks
    private ActorAuditorAwareBeanPostProcessor processor;

    /**
     * {@link Mock} {@link AuditorAware}.
     */
    @Mock
    private AuditorAware<Actor> auditor;

    /**
     * Ensures post process of a non {@link AuditingHandler}.
     */
    @Test
    public void shouldPostProcessBeforeInitializationNonAuditingHandler() {
        processor.postProcessBeforeInitialization(new Object(), "bean");
    }

    /**
     * Ensures post process of a {@link AuditingHandler}.
     */
    @SuppressWarnings("rawtypes")
    @Test
    public void shouldPostProcessBeforeInitializationAuditingHandler() {
        AuditingHandler handler = mock(AuditingHandler.class);
        processor.postProcessBeforeInitialization(handler, "handler");
        ArgumentCaptor<AuditorAware> captor = ArgumentCaptor.forClass(AuditorAware.class);
        Mockito.verify(handler).setAuditorAware(captor.capture());
        assertEquals(auditor, captor.getValue());
    }
}
