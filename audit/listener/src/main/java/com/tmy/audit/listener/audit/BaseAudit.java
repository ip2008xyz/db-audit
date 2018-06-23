package com.tmy.audit.listener.audit;


import com.tmy.audit.listener.events.PostEvent;
import com.tmy.audit.listener.operation.Operation;
import com.tmy.audit.listener.services.AuditService;
import com.tmy.audit.listener.services.impl.AuditServiceImpl;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public abstract class BaseAudit {

    protected AuditService auditService;

    protected PostEvent event;
    protected Operation operations;

    public abstract Operation audit();

    public BaseAudit(PostEvent event) {
        this.event = event;
        this.operations = new Operation();
        this.auditService = new AuditServiceImpl();
    }

    protected Operation createOperation(Long id, String entity) {
        Operation operation = new Operation();

        operation.setId(id);
        operation.setDate(Instant.now());
        operation.setOperation(event.getOperation());
        operation.setEntity(entity);
        return operation;
    }

    protected Object getNewValue(int position) {
        return event.getState()[position];
    }

    protected Object getOldValue(int position) {
        if (event.isInsert()) {
            return null;
        }
        return event.getOldState()[position];
    }

}
