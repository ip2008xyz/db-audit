package com.tmy.audit.processor.services;

import com.tmy.audit.annotation.Audit;
import com.tmy.audit.annotation.Ignore;
import com.tmy.audit.annotation.OnEmpty;
import com.tmy.audit.annotation.OnEmptyIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Element;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


public class FieldsService {

    private static final Logger logger = LoggerFactory.getLogger(FieldsService.class);

    public void getFields(Element element) {
        Map<String, Field> fieldMap = new HashMap<>();
        logger.debug("Element: {}", element.getSimpleName());

        for (Field field : element.getClass().getDeclaredFields()) {
            logger.debug("Field: {}", field.getName());
            logger.debug("Audit: {}", field.isAnnotationPresent(Audit.class));
            logger.debug("Ignore: {}", field.isAnnotationPresent(Ignore.class));
            logger.debug("OnEmptyIgnore: {}", field.isAnnotationPresent(OnEmptyIgnore.class));
            logger.debug("OnEmpty: {}", field.isAnnotationPresent(OnEmpty.class));
            logger.debug("Id: {}", field.isAnnotationPresent(Id.class));
        }
    }
}
