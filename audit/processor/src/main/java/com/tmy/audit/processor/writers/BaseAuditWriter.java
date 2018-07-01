package com.tmy.audit.processor.writers;

import com.tmy.audit.annotation.Audit;
import com.tmy.audit.listener.audit.BaseAudit;
import com.tmy.audit.listener.events.PostEvent;
import com.tmy.audit.listener.operation.Operation;
import com.tmy.audit.processor.services.FieldsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class BaseAuditWriter {

    private static final Logger logger = LoggerFactory.getLogger(BaseAuditWriter.class);

    FieldsService fieldsService = new FieldsService();

    public void write(List<Element> elements, ProcessingEnvironment processingEnv) throws IOException {

        if (elements.size() == 0) {
            logger.warn("Could not found the {} annotations on any class", Audit.class);
            return;
        }

        logger.info("Elements: {}", elements);

        for (Element element : elements) {
            logger.info("Element: {}, Enclosing: {}, Kind: {}", element.getSimpleName(), element.getEnclosingElement(), element.getKind());
            writeForClass(element, processingEnv);
        }
    }

    private void writeForClass(Element element, ProcessingEnvironment processingEnv) throws IOException {
        String simpleClassName = element.getSimpleName().toString() + "Audit";
        String packageName = element.getEnclosingElement().toString() + ".audit";

        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(packageName + "." + simpleClassName);

        fieldsService.getFields(element);

        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {


            out.printf("package %s;", packageName).println();
            out.println();
            out.printf("import %s;", PostEvent.class.getCanonicalName()).println();
            out.printf("import %s;", BaseAudit.class.getCanonicalName()).println();
            out.printf("import %s;", Operation.class.getCanonicalName()).println();
            out.printf("import %s;", element.toString()).println();
            out.println();
            out.printf("public class %s extends %s {", simpleClassName, BaseAudit.class.getSimpleName()).println();
            out.println();
            out.printf("    public %s(%s event) {", simpleClassName, PostEvent.class.getSimpleName()).println();
            out.printf("        super(event);").println();
            out.printf("    }").println();
            out.println();
            out.printf("    public %s audit() {", Operation.class.getSimpleName()).println();
            out.printf("        %s jpaEntity = (%s) event.getEntity();", element.getSimpleName().toString(), element.getSimpleName().toString()).println();
            out.printf("        operations = createOperation( jpaEntity.getId(), %s.class.getSimpleName());", element.getSimpleName().toString()).println();

            out.println();
            out.printf("        String firstNameProperty = \"firstName\";").println();
            out.printf("        Object firstNameNewValue = getNewValue(0);").println();
            out.printf("        Object firstNameOldValue = getOldValue(1);").println();
            out.printf("        operations = auditService.audit(firstNameProperty, firstNameNewValue, firstNameOldValue, operations);").println();
            out.println();
            out.printf("        String lastNameProperty = \"lastName\";").println();
            out.printf("        Object lastNameNewValue = getNewValue(1);").println();
            out.printf("        Object lastNameOldValue = getOldValue(1);").println();
            out.printf("        operations = auditService.onEmptyIgnore(lastNameProperty, lastNameNewValue, lastNameOldValue, operations);").println();
            out.println();
            out.printf("        // ignore office").println();
            out.println();
            out.printf("        String userNameProperty = \"userName\";").println();
            out.printf("        Object userNameNewValue = getNewValue(3);").println();
            out.printf("        Object userNameOldValue = getOldValue(3);").println();
            out.printf("        operations = auditService.onEmpty(userNameProperty, userNameNewValue, userNameOldValue, operations, \"None\");").println();
            out.println();
            out.printf("        return getOperations();").println();
            out.printf("    }");
            out.println();
            out.printf("}");


        }
    }
}
