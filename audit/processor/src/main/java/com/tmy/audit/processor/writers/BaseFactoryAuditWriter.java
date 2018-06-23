package com.tmy.audit.processor.writers;


import com.tmy.audit.listener.audit.BaseAudit;
import com.tmy.audit.listener.events.PostEvent;
import com.tmy.audit.listener.services.BaseFactoryAudit;
import org.springframework.stereotype.Component;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class BaseFactoryAuditWriter {

    private static final String simpleClassName = BaseFactoryAudit.class.getSimpleName() + "Impl";

    public void write(List<Element> elements, ProcessingEnvironment processingEnv) throws IOException {

        if (elements.size() == 0) {
            System.out.println("Could not found the @com.tmy.audit.annotation.Audit annotations on any class");
            return;
        }

        System.out.println("writeBaseFactoryAudit: " + elements);
        for (Element element : elements) {
            System.out.println(element.getSimpleName());
            System.out.println(element);
        }

        String packageName = getThePackage(elements);

        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(packageName + "." + simpleClassName);

        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

            out.printf("package %s;", packageName).println();

            out.printf("import %s;", PostEvent.class.getCanonicalName()).println();
            out.printf("import %s;", BaseAudit.class.getCanonicalName()).println();
            out.printf("import %s;", BaseFactoryAudit.class.getCanonicalName()).println();

            for (Element element : elements) {
                out.printf("import %s;", element).println();
                out.printf("import %s.audit.%s%s;", element.getEnclosingElement(), element.getSimpleName(), "Audit").println();
            }

            out.printf("import %s;", Component.class.getCanonicalName()).println();

            out.println();
            out.printf("@%s", Component.class.getSimpleName()).println();
            out.printf("public class %s implements %s {", simpleClassName, BaseFactoryAudit.class.getSimpleName()).println();
            out.println();

            out.printf("    public %s factory(%s event) {", BaseAudit.class.getSimpleName(), PostEvent.class.getSimpleName()).println();
            out.printf("        %s auditEntity = null;", BaseAudit.class.getSimpleName()).println();

            for (Element element : elements) {//TODO add a switch or do better
                out.printf("        if (event.getEntity() instanceof %s) {", element.getSimpleName()).println();
                out.printf("            auditEntity = new PeopleAudit(event);").println();
                out.printf("        }").println();
            }

            out.printf("        if (auditEntity != null) {").println();
            out.printf("            auditEntity.audit();").println();
            out.printf("        }").println();
            out.printf("        return auditEntity;").println();
            out.printf("    }");
            out.printf("}");

        }
    }

    private String getThePackage(List<Element> elements) {
        for (Element element : elements) {
            return element.getEnclosingElement().toString() + ".factory";
        }
        return "factory";
    }

}
