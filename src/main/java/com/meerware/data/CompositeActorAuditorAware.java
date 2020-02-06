package com.meerware.data;

import static java.util.Optional.empty;

import java.util.Optional;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.domain.AuditorAware;

/**
 * {@link AuditorAware} which looks for the first present {@link Actor} and applies
 * that as the {@link Actor} making the change.
 */
class CompositeActorAuditorAware implements AuditorAware<Actor> {

    /**
     * Empty {@link AuditorAware} used as the last option.
     */
    private static final AuditorAware<Actor> EMPTY = () -> empty();

    private final ObjectProvider<AuditorAware<Actor>> provider;

    CompositeActorAuditorAware(ObjectProvider<AuditorAware<Actor>> provider) {
        this.provider = provider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Actor> getCurrentAuditor() {
        return provider.orderedStream()
                .filter(candidate -> candidate.getCurrentAuditor().isPresent())
                .findFirst()
                .orElse(EMPTY)
                .getCurrentAuditor();
    }

}
