package com.meerware.data;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.domain.AuditorAware;

/**
 * {@link BeanPostProcessor} which applies the {@link AuditorAware} implementation.
 */
class ActorAuditorAwareBeanPostProcessor implements BeanPostProcessor {

    /**
     * {@link AuditorAware}.
     */
    private final AuditorAware<Actor> auditor;

    /**
     * @param auditor is the {@link AuditorAware}.
     */
    ActorAuditorAwareBeanPostProcessor(AuditorAware<Actor> auditor) {
        this.auditor = auditor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof AuditingHandler) {
            ((AuditingHandler) bean).setAuditorAware(auditor);
        }
        return bean;
    }
}
