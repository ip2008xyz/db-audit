package com.tmy.audit.processor;

import com.tmy.audit.processor.writers.BaseAuditWriter;
import com.tmy.audit.processor.writers.BaseFactoryAuditWriter;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static javax.lang.model.element.ElementKind.CLASS;

@SupportedAnnotationTypes({"com.tmy.audit.annotation.Audit", "com.tmy.audit.annotation.Ignore", "javax.annotation.PostConstruct"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AuditProcessor extends AbstractProcessor {

    private List<Element> classes = new ArrayList<>();

    private BaseFactoryAuditWriter baseFactoryAuditWriter = new BaseFactoryAuditWriter();

    private BaseAuditWriter baseAuditWriter = new BaseAuditWriter();


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {

        System.out.println("annotations: " + annotations);

        if (annotations.size() == 0) {
            return true;
        }

        for (TypeElement annotation : annotations) {
            System.out.println("annotation: " + annotation);
            Set<? extends Element> annotatedElements = roundEnvironment.getElementsAnnotatedWith(annotation);
            System.out.println("annotatedElements: " + annotatedElements);
            for (Element element : annotatedElements) {
                System.out.println("element: " + element.toString());
                System.out.println("getSimpleName: " + element.getSimpleName());
                System.out.println("getKind: " + element.getKind());

                if (CLASS.equals(element.getKind()) && annotation.toString().equals("com.tmy.audit.annotation.Audit")) {
                    classes.add(element);
                }

            }
        }
        try {
            baseAuditWriter.write(classes, processingEnv);
            baseFactoryAuditWriter.write(classes, processingEnv);

        } catch (IOException e) {
            System.out.println("Could not generate files: " + e);
        }

        return true;
    }

    private void writeBuilderFile(
            String className, Map<String, String> setterMap)
            throws IOException {

        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }

        String simpleClassName = className.substring(lastDot + 1);
        String builderClassName = className + "Builder";
        String builderSimpleClassName = builderClassName
                .substring(lastDot + 1);

        JavaFileObject builderFile = processingEnv.getFiler()
                .createSourceFile(builderClassName);

        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

            if (packageName != null) {
                out.print("package ");
                out.print(packageName);
                out.println(";");
                out.println();
            }

            out.print("public class ");
            out.print(builderSimpleClassName);
            out.println(" {");
            out.println();

            out.print("    private ");
            out.print(simpleClassName);
            out.print(" object = new ");
            out.print(simpleClassName);
            out.println("();");
            out.println();

            out.print("    public ");
            out.print(simpleClassName);
            out.println(" build() {");
            out.println("        return object;");
            out.println("    }");
            out.println();

            setterMap.entrySet().forEach(setter -> {
                String methodName = setter.getKey();
                String argumentType = setter.getValue();

                out.print("    public ");
                out.print(builderSimpleClassName);
                out.print(" ");
                out.print(methodName);

                out.print("(");

                out.print(argumentType);
                out.println(" value) {");
                out.print("        object.");
                out.print(methodName);
                out.println("(value);");
                out.println("        return this;");
                out.println("    }");
                out.println();
            });

            out.println("}");
        }
    }
}

