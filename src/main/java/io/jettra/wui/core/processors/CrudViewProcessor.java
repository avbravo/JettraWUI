package io.jettra.wui.core.processors;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.*;
import io.jettra.wui.core.annotations.CrudHandler;
import io.jettra.wui.core.annotations.CrudView;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.*;

@AutoService(Processor.class)
@SupportedAnnotationTypes("io.jettra.wui.core.annotations.CrudView")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class CrudViewProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(CrudView.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                generateHandler((TypeElement) element);
            }
        }
        return true;
    }

    private void generateHandler(TypeElement pageElement) {
        CrudView annotation = pageElement.getAnnotation(CrudView.class);
        
        TypeMirror modelType = getModelType(annotation);
        TypeMirror repoType = getRepoType(annotation);
        TypeMirror controllerType = getControllerType(annotation);
        
        String packageName = processingEnv.getElementUtils().getPackageOf(pageElement).getQualifiedName().toString();
        String pageClassName = pageElement.getSimpleName().toString();
        String handlerClassName = pageClassName + "CrudHandler";

        TypeName modelTypeName = TypeName.get(modelType);
        
        boolean useController = controllerType != null && !controllerType.toString().equals("void");
        TypeMirror dataAccessType = useController ? controllerType : repoType;
        TypeName dataAccessTypeName = TypeName.get(dataAccessType);

        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(handlerClassName)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(CrudHandler.class), modelTypeName));

        // findAll()
        classBuilder.addMethod(MethodSpec.methodBuilder("findAll")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(ParameterizedTypeName.get(ClassName.get(List.class), modelTypeName))
                .addStatement("return $T.findAll()", dataAccessTypeName)
                .build());

        // save(M model)
        classBuilder.addMethod(MethodSpec.methodBuilder("save")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(modelTypeName, "model")
                .addStatement("$T.save(model)", dataAccessTypeName)
                .build());

        // delete(String id)
        classBuilder.addMethod(MethodSpec.methodBuilder("delete")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "id")
                .addStatement("$T.delete(id)", dataAccessTypeName)
                .build());

        // getIdValue(M item)
        classBuilder.addMethod(MethodSpec.methodBuilder("getIdValue")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(modelTypeName, "item")
                .beginControlFlow("try")
                .addStatement("var field = item.getClass().getDeclaredField(\"code\")")
                .addStatement("field.setAccessible(true)")
                .addStatement("return field.get(item).toString()")
                .nextControlFlow("catch (Exception e)")
                .beginControlFlow("try")
                .addStatement("var field = item.getClass().getDeclaredField(\"id\")")
                .addStatement("field.setAccessible(true)")
                .addStatement("return field.get(item).toString()")
                .nextControlFlow("catch (Exception e2)")
                .addStatement("return \"0\"")
                .endControlFlow()
                .endControlFlow()
                .returns(String.class)
                .build());

        // getJsonMap(M item)
        classBuilder.addMethod(MethodSpec.methodBuilder("getJsonMap")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(modelTypeName, "item")
                .addStatement("$T<String, String> map = new $T<>()", Map.class, HashMap.class)
                .beginControlFlow("for (var field : item.getClass().getDeclaredFields())")
                .beginControlFlow("try")
                .addStatement("field.setAccessible(true)")
                .addStatement("Object val = field.get(item)")
                .addStatement("map.put(field.getName(), val != null ? val.toString() : \"\")")
                .nextControlFlow("catch (Exception e)")
                .endControlFlow()
                .endControlFlow()
                .addStatement("return map")
                .returns(ParameterizedTypeName.get(Map.class, String.class, String.class))
                .build());

        // createInstance()
        classBuilder.addMethod(MethodSpec.methodBuilder("createInstance")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(modelTypeName)
                .addStatement("return new $T()", modelTypeName)
                .build());

        // updateFields(M model, Map<String, String> data)
        MethodSpec.Builder updateBuilder = MethodSpec.methodBuilder("updateFields")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(modelTypeName, "model")
                .addParameter(ParameterizedTypeName.get(Map.class, String.class, String.class), "data");
        
        updateBuilder.beginControlFlow("for (var entry : data.entrySet())")
                .beginControlFlow("try")
                .addStatement("var field = model.getClass().getDeclaredField(entry.getKey())")
                .addStatement("field.setAccessible(true)")
                .addCode("    if (field.getType().equals(String.class)) field.set(model, entry.getValue());\n")
                .addCode("    else if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) field.set(model, Integer.parseInt(entry.getValue()));\n")
                .addCode("    else if (field.getType().equals(double.class) || field.getType().equals(Double.class)) field.set(model, Double.parseDouble(entry.getValue()));\n")
                .addCode("    else if (field.getType().equals(float.class) || field.getType().equals(Float.class)) field.set(model, Float.parseFloat(entry.getValue()));\n")
                .addCode("    else if (field.getType().equals(long.class) || field.getType().equals(Long.class)) field.set(model, Long.parseLong(entry.getValue()));\n")
                .addCode("    else if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) field.set(model, Boolean.parseBoolean(entry.getValue()));\n")
                .addCode("    else if (field.getType().equals(java.time.LocalDate.class)) field.set(model, java.time.LocalDate.parse(entry.getValue()));\n")
                .addCode("    else if (field.getType().equals(java.util.Date.class)) field.set(model, new java.text.SimpleDateFormat(\"yyyy-MM-dd\").parse(entry.getValue()));\n")
                .nextControlFlow("catch (Exception e)")
                .endControlFlow()
                .endControlFlow();
        
        classBuilder.addMethod(updateBuilder.build());

        JavaFile javaFile = JavaFile.builder(packageName, classBuilder.build()).build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TypeMirror getModelType(CrudView annotation) {
        try {
            annotation.model();
        } catch (MirroredTypeException mte) {
            return mte.getTypeMirror();
        }
        return null;
    }

    private TypeMirror getRepoType(CrudView annotation) {
        try {
            annotation.repository();
        } catch (MirroredTypeException mte) {
            return mte.getTypeMirror();
        }
        return null;
    }

    private TypeMirror getControllerType(CrudView annotation) {
        try {
            annotation.controller();
        } catch (MirroredTypeException mte) {
            return mte.getTypeMirror();
        }
        return null;
    }
}
