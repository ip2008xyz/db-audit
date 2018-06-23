package com.tmy.audit.listener.operation;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class Operation {
    private Long id;
    private String entity;
    private String userName = "";
    private String operation;
    private Instant date;
    private List<Properties> properties = new ArrayList<>();

    @Data
    @AllArgsConstructor
    private class Properties {
        private String property;
        private String newValue;
        private String oldValue;
    }

    public void addProperty(String propertyName, String newValue, String oldValue) {
        Properties property = new Properties(propertyName, newValue, oldValue);
        properties.add(property);
    }
}
