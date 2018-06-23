package com.tmy.audit.listener.services.impl;


import com.tmy.audit.listener.operation.Operation;
import com.tmy.audit.listener.services.AuditService;
import org.apache.commons.lang3.StringUtils;

public class AuditServiceImpl implements AuditService {

    public Operation audit(String property, Object newValue, Object oldValue, Operation operation) {
        String newValueString = String.valueOf(newValue);
        String oldValueString = String.valueOf(oldValue);
        if (!newValueString.equals(oldValueString)) {
            operation.addProperty(property, newValueString, oldValueString);
        }
        return operation;
    }

    public Operation onEmptyIgnore(String property, Object newValue, Object oldValue, Operation operation) {
        if (newValue != null && StringUtils.isNoneEmpty(String.valueOf(newValue))) {
            return audit(property, newValue, oldValue, operation);
        }
        return operation;
    }

    public Operation onEmpty(String property, Object newValue, Object oldValue, Operation operation, String replaceValue) {
        String newValueString = String.valueOf(newValue);
        String oldValueString = String.valueOf(newValue);
        if (StringUtils.isEmpty(newValueString)) {
            newValueString = replaceValue;
        }
        if (StringUtils.isEmpty(oldValueString)) {
            oldValueString = replaceValue;
        }

        return audit(property, newValueString, oldValueString, operation);

    }
}
