package com.tmy.audit.listener.services;


import com.tmy.audit.listener.operation.Operation;

public interface AuditService {

    Operation audit(String property, Object newValue, Object oldValue, Operation operation);

    Operation onEmptyIgnore(String property, Object newValue, Object oldValue, Operation operation);
    
    Operation onEmpty(String property, Object newValue, Object oldValue, Operation operation, String replaceValue);
}
