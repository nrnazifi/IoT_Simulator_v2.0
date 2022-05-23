package edu.campuswien.smartcity.time;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerationException;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class IdGenerator implements IdentifierGenerator, Configurable {

    public static final String PARAM_DBNR = "dbnr";
    private Class<?> returnClass = String.class;
    private int dbnr;//TODO should read from properties

    public IdGenerator() {
        returnClass = Long.class;
    }

    @Override
    public synchronized Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
        if (returnClass == (Long.class)) {
            return TimeIdGenerator.getObjectId(dbnr);
        } else {
            throw new IdentifierGenerationException("This id generator generates long types only!");
        }
    }

    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) throws MappingException {
        Integer res = (Integer) properties.get(PARAM_DBNR);
        dbnr = (res == null) ? 0 : res;
        returnClass = type.getReturnedClass();
    }

}
