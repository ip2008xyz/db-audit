package com.tmy.audit.models;

import com.tmy.audit.annotation.Audit;
import com.tmy.audit.annotation.Ignore;
import com.tmy.audit.annotation.OnEmpty;
import com.tmy.audit.annotation.OnEmptyIgnore;

import java.util.HashMap;
import java.util.Map;

public class Properties {


    public static Map<String, Class<?>> annotations = new HashMap<>();


    static {
        annotations.put(Audit.class.getCanonicalName(), Audit.class);
        annotations.put(Ignore.class.getCanonicalName(), Ignore.class);
        annotations.put(OnEmpty.class.getCanonicalName(), OnEmpty.class);
        annotations.put(OnEmptyIgnore.class.getCanonicalName(), OnEmptyIgnore.class);

    }

}
