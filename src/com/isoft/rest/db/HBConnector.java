package com.isoft.rest.db;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import com.isoft.base.logging.PropLogger;

public class HBConnector {
    private final static Logger logger = Logger.getLogger(PropLogger.class.getName());

    private static final SessionFactory sessionFactory = buildSessionFactory();
    
    private static SessionFactory buildSessionFactory() 
    {
        try {
            // Create the ServiceRegistry from hibernate.cfg.xml
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .configure("com/isoft/base/resources/db_credentials/hibernate.cfg.xml").build();
 
            Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
 
            return metadata.getSessionFactoryBuilder().build();
        } catch (Throwable ex) {
            logger.log(Level.SEVERE, "Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }
    
    public static void close() 
    {    
        getSessionFactory().close();
        logger.log(Level.INFO, "Hibernate DB session closed.");
    }
}
