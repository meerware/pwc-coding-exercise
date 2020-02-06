package com.meerware.data;

import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.util.AlternativeJdkIdGenerator;
import org.springframework.util.IdGenerator;


public class UniversalUniqueIdentifierGenerator implements IdentifierGenerator {

    private final IdGenerator generator;

    public UniversalUniqueIdentifierGenerator() {
        this(new AlternativeJdkIdGenerator());
    }

    UniversalUniqueIdentifierGenerator(IdGenerator generator) {
        this.generator = generator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Serializable generate(
            SharedSessionContractImplementor session,
            Object entity) throws HibernateException {
        ClassMetadata metadata = session.getEntityPersister(null, entity).getClassMetadata();
        Serializable identifier = metadata.getIdentifier(entity, session);
        if (identifier == null) {
            // Generate an identifier
            identifier = this.generator.generateId();
        }
        return identifier;
    }

}
