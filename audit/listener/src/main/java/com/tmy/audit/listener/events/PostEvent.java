package com.tmy.audit.listener.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.persister.entity.EntityPersister;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PostEvent {

    private static final String UPDATE = "UPDATE";
    private static final String INSERT = "INSERT";
    private static final String DELETE = "DELETE";

    private Object entity;
    private EntityPersister persister;
    private Object[] state;
    private Serializable id;

    private Object[] oldState;
    private final int[] dirtyProperties;

    private final String operation;

    public PostEvent(PostUpdateEvent postUpdateEvent) {
        this.entity = postUpdateEvent.getEntity();
        this.id = postUpdateEvent.getId();
        this.state = postUpdateEvent.getState();
        this.oldState = postUpdateEvent.getOldState();
        this.dirtyProperties = postUpdateEvent.getDirtyProperties();
        this.persister = postUpdateEvent.getPersister();
        this.operation = UPDATE;
    }

    public PostEvent(PostInsertEvent postUpdateEvent) {

        this.entity = postUpdateEvent.getEntity();
        this.id = postUpdateEvent.getId();
        this.state = postUpdateEvent.getState();
        this.persister = postUpdateEvent.getPersister();
        this.dirtyProperties = new int[0];
        this.oldState = new Object[0];
        this.operation = INSERT;
    }

    public boolean isUpdate() {
        return UPDATE.equals(operation);
    }

    public boolean isInsert() {
        return INSERT.equals(operation);
    }

    public boolean isDelete() {
        return DELETE.equals(operation);
    }
}
