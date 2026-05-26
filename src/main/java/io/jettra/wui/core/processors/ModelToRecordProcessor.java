package io.jettra.wui.core.processors;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.*;
import io.jettra.wui.core.annotations.ModelToRecordConversor;
import io.jettra.wui.core.annotations.PropertiesInRecord;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.*;

@AutoService(Processor.class)
@SupportedAnnotationTypes("io.jettra.wui.core.annotations.ModelToRecordConversor")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class ModelToRecordProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(ModelToRecordConversor.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                generateConversor((TypeElement) element);
            }
        }
        return true;
    }

    private void generateConversor(TypeElement modelElement) {
        ModelToRecordConversor annotation = modelElement.getAnnotation(ModelToRecordConversor.class);
        
        String packageName = processingEnv.getElementUtils().getPackageOf(modelElement).getQualifiedName().toString();
        String modelClassName = modelElement.getSimpleName().toString();
        
        TypeMirror goalType = getGoalType(annotation);
        TypeName recordTypeName;
        String recordClassName;
        
        if (goalType != null && !goalType.toString().equals("void")) {
            recordTypeName = TypeName.get(goalType);
            recordClassName = ((TypeElement) processingEnv.getTypeUtils().asElement(goalType)).getSimpleName().toString();
        } else {
            recordClassName = modelClassName.endsWith("Model") ? modelClassName.substring(0, modelClassName.length() - 5) : modelClassName + "Record";
            recordTypeName = ClassName.get(packageName, recordClassName);
        }

        String conversorClassName = recordClassName + "RecordModelConverter";

        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(conversorClassName)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(ClassName.get("jettra.scoped", "ApplicationScoped"));

        // toModel
        MethodSpec.Builder toModelBuilder = MethodSpec.methodBuilder("toModel")
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.get(modelElement))
                .addParameter(recordTypeName, "record")
                .beginControlFlow("if (record == null)")
                .addStatement("return null")
                .endControlFlow()
                .addStatement("$T model = new $T()", ClassName.get(modelElement), ClassName.get(modelElement));

        // toRecord
        MethodSpec.Builder toRecordBuilder = MethodSpec.methodBuilder("toRecord")
                .addModifiers(Modifier.PUBLIC)
                .returns(recordTypeName)
                .addParameter(ClassName.get(modelElement), "model")
                .beginControlFlow("if (model == null)")
                .addStatement("return null")
                .endControlFlow();

        List<String> recordParams = new ArrayList<>();

        for (Element enclosed : modelElement.getEnclosedElements()) {
            if (enclosed.getKind() == ElementKind.FIELD) {
                VariableElement fieldElement = (VariableElement) enclosed;
                PropertiesInRecord propAnnotation = fieldElement.getAnnotation(PropertiesInRecord.class);
                
                if (propAnnotation != null) {
                    String modelFieldName = fieldElement.getSimpleName().toString();
                    String recordFieldName = propAnnotation.field().isEmpty() ? modelFieldName : propAnnotation.field();
                    
                    // toModel
                    String setterName = "set" + Character.toUpperCase(modelFieldName.charAt(0)) + modelFieldName.substring(1);
                    toModelBuilder.addStatement("model.$L(record.$L())", setterName, recordFieldName);
                    
                    // toRecord
                    String getterName = "get" + Character.toUpperCase(modelFieldName.charAt(0)) + modelFieldName.substring(1);
                    if (fieldElement.asType().toString().equals("boolean")) {
                        getterName = "is" + Character.toUpperCase(modelFieldName.charAt(0)) + modelFieldName.substring(1);
                    }
                    recordParams.add("model." + getterName + "()");
                }
            }
        }

        toModelBuilder.addStatement("return model");
        
        toRecordBuilder.addStatement("return new $T(\n  $L\n)", recordTypeName, String.join(",\n  ", recordParams));

        classBuilder.addMethod(toModelBuilder.build());
        classBuilder.addMethod(toRecordBuilder.build());

        JavaFile javaFile = JavaFile.builder(packageName, classBuilder.build()).build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TypeMirror getGoalType(ModelToRecordConversor annotation) {
        try {
            annotation.goal();
        } catch (MirroredTypeException mte) {
            return mte.getTypeMirror();
        }
        return null;
    }
}
