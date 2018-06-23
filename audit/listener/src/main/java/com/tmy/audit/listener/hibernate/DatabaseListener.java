package com.tmy.audit.listener.hibernate;


import com.tmy.audit.listener.services.BaseFactoryAudit;
import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;

public class DatabaseListener {

    @Resource
    //TODO find way to obtain this without using annotations
    private EntityManagerFactory factory;

    private InsertListener insertListener;

    private UpdateListener updateListener;

    public DatabaseListener(BaseFactoryAudit auditFactory) {
        insertListener = new InsertListener(auditFactory);
        updateListener = new UpdateListener(auditFactory);
    }

    @PostConstruct
    public void loadListeners() {

        SessionFactory sessionFactory = factory.unwrap(SessionFactory.class);

        EventListenerRegistry registry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry().getService(EventListenerRegistry.class);

        registry.getEventListenerGroup(EventType.POST_COMMIT_INSERT)
                .appendListener(insertListener);

        registry.getEventListenerGroup(EventType.POST_COMMIT_UPDATE)
                .appendListener(updateListener);
    }
}
