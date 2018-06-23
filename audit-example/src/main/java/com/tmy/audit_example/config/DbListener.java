package com.tmy.audit_example.config;

import com.tmy.audit.listener.hibernate.DatabaseListener;
import com.tmy.audit.listener.services.BaseFactoryAudit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbListener {

    @Autowired
    private BaseFactoryAudit baseFactoryAudit;

    @Bean
    public DatabaseListener databaseListener() {
        return new DatabaseListener(baseFactoryAudit);
    }
}
