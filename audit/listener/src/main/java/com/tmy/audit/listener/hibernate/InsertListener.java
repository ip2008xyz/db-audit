package com.tmy.audit.listener.hibernate;

import com.tmy.audit.listener.audit.BaseAudit;
import com.tmy.audit.listener.events.PostEvent;
import com.tmy.audit.listener.operation.Operation;
import com.tmy.audit.listener.services.BaseFactoryAudit;
import org.hibernate.event.spi.PostCommitInsertEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.persister.entity.EntityPersister;


public class InsertListener implements PostCommitInsertEventListener {

    private BaseFactoryAudit auditFactory;

    public InsertListener(BaseFactoryAudit auditFactory) {
        this.auditFactory = auditFactory;
    }

    @Override
    public void onPostInsert(PostInsertEvent postInsertEvent) {
        PostEvent event = new PostEvent(postInsertEvent);

        BaseAudit auditEntity = auditFactory.factory(event);
        Operation operations = auditEntity.audit();
        System.out.println(operations);

    }


    @Override
    public boolean requiresPostCommitHanding(EntityPersister persister) {
        return true;
    }

    @Override
    public void onPostInsertCommitFailed(PostInsertEvent event) {
        //LOGGER.warn("Post insert commit event failed: {}", event.getEntity());
    }

}
