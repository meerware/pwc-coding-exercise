package com.meerware.data;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Auto {@link Configuration} used to register data related beans.
 */
@Configuration
@EnableJpaAuditing
class DataAutoConfiguration {

    /**
     * @param provider is the {@link ObjectProvider}.
     * @return the newly created {@link ActorAuditorAwareBeanPostProcessor}.
     */
    @Bean
    ActorAuditorAwareBeanPostProcessor actorAuditorAwareBeanPostProcessor(ObjectProvider<AuditorAware<Actor>> provider) {
        return new ActorAuditorAwareBeanPostProcessor(new CompositeActorAuditorAware(provider));
    }
}
