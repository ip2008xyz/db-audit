package com.tmy.audit.processor;

import com.tmy.audit.annotation.Audit;
import com.tmy.audit.processor.exceptions.WriterException;
import com.tmy.audit.processor.writers.BaseAuditWriter;
import com.tmy.audit.processor.writers.BaseFactoryAuditWriter;
import static javax.lang.model.element.ElementKind.CLASS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes({"com.tmy.audit.annotation.Audit"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AuditProcessor extends AbstractProcessor {

    private static final Logger logger = LoggerFactory.getLogger(AuditProcessor.class);

    private List<Element> classes = new ArrayList<>();

    private BaseFactoryAuditWriter baseFactoryAuditWriter = new BaseFactoryAuditWriter();

    private BaseAuditWriter baseAuditWriter = new BaseAuditWriter();


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {

        logger.debug("Annotations: {}", annotations);

        if (annotations.size() == 0) {
            return true;
        }

        for (TypeElement annotation : annotations) {
            logger.debug("Annotation: {}", annotation);


            Set<? extends Element> annotatedElements = roundEnvironment.getElementsAnnotatedWith(annotation);

            logger.debug("AnnotatedElements: {}", annotatedElements);

            for (Element element : annotatedElements) {
                logger.debug("Element: {}, SimpleName: {}, Kind: {}", element.toString(), element.getSimpleName(), element.getKind());

                if (CLASS.equals(element.getKind()) && annotation.toString().equals(Audit.class.getCanonicalName())) {
                    classes.add(element);
                }

            }
        }
        try {
            baseAuditWriter.write(classes, processingEnv);
            baseFactoryAuditWriter.write(classes, processingEnv);

        } catch (IOException e) {
            logger.warn("Could not generate files", e);
            throw new WriterException(e.getMessage());
        }

        return true;
    }

}

