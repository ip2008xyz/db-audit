package com.tmy.audit.listener.hibernate;

import com.tmy.audit.listener.audit.BaseAudit;
import com.tmy.audit.listener.events.PostEvent;
import com.tmy.audit.listener.operation.Operation;
import com.tmy.audit.listener.services.BaseFactoryAudit;
import org.hibernate.event.spi.PostCommitUpdateEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.persister.entity.EntityPersister;


public class UpdateListener implements PostCommitUpdateEventListener {

    private BaseFactoryAudit auditFactory;

    public UpdateListener(BaseFactoryAudit auditFactory) {
        this.auditFactory = auditFactory;
    }

    @Override
    public void onPostUpdate(PostUpdateEvent postUpdateEvent) {
        PostEvent event = new PostEvent(postUpdateEvent);

        BaseAudit auditEntity = auditFactory.factory(event);
        Operation operations = auditEntity.audit();
        System.out.println(operations);
    }


    @Override
    public boolean requiresPostCommitHanding(EntityPersister persister) {
        return true;
    }

    @Override
    public void onPostUpdateCommitFailed(PostUpdateEvent event) {
        //LOGGER.warn("Post insert commit event failed: {}", event.getEntity());
    }

}

