package com.tmy.audit.listener.services;

import com.tmy.audit.listener.audit.BaseAudit;
import com.tmy.audit.listener.events.PostEvent;

public interface BaseFactoryAudit {

    BaseAudit factory(PostEvent event);

}
