package io.jettra.wui.core.processors;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.*;
import io.jettra.wui.core.annotations.CrudHandler;
import io.jettra.wui.core.annotations.CrudView;
import io.jettra.wui.core.annotations.ModelToRecordConversor;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import java.io.IOException;
import java.util.*;

@AutoService(Processor.class)
@SupportedAnnotationTypes("io.jettra.wui.core.annotations.CrudView")
@SupportedSourceVersion(SourceVersion.RELEASE_25)
public class CrudViewProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(CrudView.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                generateHandler((TypeElement) element);
            } else if (element.getKind() == ElementKind.INTERFACE) {
                generateHandler((TypeElement) element);
                generatePage((TypeElement) element);
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

        TypeElement modelElement = (TypeElement) processingEnv.getTypeUtils().asElement(modelType);
        ModelToRecordConversor conversorAnno = modelElement.getAnnotation(ModelToRecordConversor.class);
        boolean hasConversor = conversorAnno != null;
        TypeName conversorTypeName = null;
        if (hasConversor) {
            String modelPackageName = processingEnv.getElementUtils().getPackageOf(modelElement).getQualifiedName().toString();
            String modelClassName = modelElement.getSimpleName().toString();
            TypeMirror goalType = getGoalType(conversorAnno);
            String recordClassName;
            if (goalType != null && !goalType.toString().equals("void")) {
                recordClassName = ((TypeElement) processingEnv.getTypeUtils().asElement(goalType)).getSimpleName().toString();
            } else {
                recordClassName = modelClassName.endsWith("Model") ? modelClassName.substring(0, modelClassName.length() - 5) : modelClassName + "Record";
            }
            String conversorClassName = recordClassName + "RecordModelConverter";
            conversorTypeName = ClassName.get(modelPackageName, conversorClassName);
        }

        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(handlerClassName)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(CrudHandler.class), modelTypeName));

        if (hasConversor) {
            classBuilder.addField(FieldSpec.builder(conversorTypeName, "converter")
                    .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                    .initializer("new $T()", conversorTypeName)
                    .build());
        }

        // findAll()
        MethodSpec.Builder findAllBuilder = MethodSpec.methodBuilder("findAll")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(ParameterizedTypeName.get(ClassName.get(List.class), modelTypeName));
        if (hasConversor) {
            findAllBuilder.addStatement("return $T.findAll().stream().map(converter::toModel).collect($T.toList())",
                    dataAccessTypeName, ClassName.get("java.util.stream", "Collectors"));
        } else {
            findAllBuilder.addStatement("return $T.findAll()", dataAccessTypeName);
        }
        classBuilder.addMethod(findAllBuilder.build());

        // save(M model)
        MethodSpec.Builder saveBuilder = MethodSpec.methodBuilder("save")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(modelTypeName, "model");
        if (hasConversor) {
            saveBuilder.addStatement("$T.save(converter.toRecord(model))", dataAccessTypeName);
        } else {
            saveBuilder.addStatement("$T.save(model)", dataAccessTypeName);
        }
        classBuilder.addMethod(saveBuilder.build());

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
                .addStatement("Object val = getFieldValue(item, \"code\")")
                .addStatement("if (val != null) return val.toString()")
                .addStatement("val = getFieldValue(item, \"id\")")
                .addStatement("if (val != null) return val.toString()")
                .addStatement("return \"0\"")
                .returns(String.class)
                .build());

        // getJsonMap(M item)
        MethodSpec.Builder getJsonMapBuilder = MethodSpec.methodBuilder("getJsonMap")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(modelTypeName, "item")
                .addStatement("$T<String, String> map = new $T<>()", Map.class, HashMap.class);
        
        for (VariableElement field : ElementFilter.fieldsIn(modelElement.getEnclosedElements())) {
            String name = field.getSimpleName().toString();
            getJsonMapBuilder.addStatement("Object val_$L = getFieldValue(item, $S)", name, name);
            getJsonMapBuilder.addStatement("map.put($S, val_$L != null ? val_$L.toString() : \"\")", name, name, name);
        }
        getJsonMapBuilder.addStatement("return map")
                .returns(ParameterizedTypeName.get(Map.class, String.class, String.class));
        classBuilder.addMethod(getJsonMapBuilder.build());

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
        
        updateBuilder.beginControlFlow("for (var entry : data.entrySet())");
        updateBuilder.beginControlFlow("try");
        updateBuilder.beginControlFlow("switch (entry.getKey())");
        
        for (VariableElement field : ElementFilter.fieldsIn(modelElement.getEnclosedElements())) {
            String name = field.getSimpleName().toString();
            TypeMirror type = field.asType();
            updateBuilder.addCode("case $S:\n", name);
            if (type.toString().equals("java.lang.String")) {
                updateBuilder.addStatement("  setFieldValue(model, $S, entry.getValue())", name);
            } else if (type.toString().equals("int") || type.toString().equals("java.lang.Integer")) {
                updateBuilder.addStatement("  setFieldValue(model, $S, Integer.parseInt(entry.getValue()))", name);
            } else if (type.toString().equals("double") || type.toString().equals("java.lang.Double")) {
                updateBuilder.addStatement("  setFieldValue(model, $S, Double.parseDouble(entry.getValue()))", name);
            } else if (type.toString().equals("boolean") || type.toString().equals("java.lang.Boolean")) {
                updateBuilder.addStatement("  setFieldValue(model, $S, Boolean.parseBoolean(entry.getValue()))", name);
            } else if (type.toString().equals("java.time.LocalDate")) {
                updateBuilder.addStatement("  setFieldValue(model, $S, java.time.LocalDate.parse(entry.getValue()))", name);
            } else if (type.toString().equals("java.time.Instant")) {
                updateBuilder.addStatement("  setFieldValue(model, $S, java.time.Instant.parse(entry.getValue()))", name);
            } else if (type.toString().equals("java.util.Date")) {
                updateBuilder.addStatement("  setFieldValue(model, $S, new java.text.SimpleDateFormat(\"yyyy-MM-dd\").parse(entry.getValue()))", name);
            } else {
                updateBuilder.addStatement("  // Unsupported type for updateFields: " + type.toString());
            }
            updateBuilder.addStatement("  break");
        }
        updateBuilder.endControlFlow(); // switch
        updateBuilder.nextControlFlow("catch (Exception e)");
        updateBuilder.endControlFlow(); // try
        updateBuilder.endControlFlow(); // for
        
        classBuilder.addMethod(updateBuilder.build());

        // Generate Metadata and Reflection-free access
        generateReflectionFreeMethods(classBuilder, modelTypeName, modelElement);

        JavaFile javaFile = JavaFile.builder(packageName, classBuilder.build()).build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateReflectionFreeMethods(TypeSpec.Builder classBuilder, TypeName modelTypeName, TypeElement modelElement) {
        ClassName metadataClass = ClassName.get("io.jettra.wui.core.annotations", "FieldMetadata");

        // getFieldsMetadata
        MethodSpec.Builder metaBuilder = MethodSpec.methodBuilder("getFieldsMetadata")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(ParameterizedTypeName.get(ClassName.get(List.class), metadataClass))
                .addStatement("$T<$T> list = new $T<>()", List.class, metadataClass, ArrayList.class);

        // getFieldValue
        MethodSpec.Builder getValBuilder = MethodSpec.methodBuilder("getFieldValue")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(modelTypeName, "item")
                .addParameter(String.class, "fieldName")
                .returns(Object.class)
                .beginControlFlow("switch (fieldName)");

        // setFieldValue
        MethodSpec.Builder setValBuilder = MethodSpec.methodBuilder("setFieldValue")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(modelTypeName, "item")
                .addParameter(String.class, "fieldName")
                .addParameter(Object.class, "value")
                .beginControlFlow("switch (fieldName)");

        Set<TypeMirror> nestedTypes = new HashSet<>();

        for (VariableElement field : ElementFilter.fieldsIn(modelElement.getEnclosedElements())) {
            String name = field.getSimpleName().toString();
            String capName = name.substring(0, 1).toUpperCase() + name.substring(1);
            TypeMirror type = field.asType();
            
            // extract raw type name for Class object
            TypeName fieldTypeName = TypeName.get(type);
            TypeName rawType = fieldTypeName;
            if (fieldTypeName instanceof ParameterizedTypeName) {
                rawType = ((ParameterizedTypeName) fieldTypeName).rawType;
            }

            metaBuilder.addStatement("$T fm_$L = new $T($S, $T.class)", metadataClass, name, metadataClass, name, rawType);

            // Copy annotations to metadata
            addAnnotationCheck(metaBuilder, field, "io.jettra.wui.core.annotations.Hidden", name, "isHidden = true");
            addAnnotationCheck(metaBuilder, field, "io.jettra.wui.core.annotations.NoEditable", name, "isNoEditable = true");
            addAnnotationCheck(metaBuilder, field, "io.jettra.rules.annotations.Compute", name, "hasCompute = true");
            
            // Complex annotations...
            // PropertiesLabel
            AnnotationMirror propLabel = getAnnotationMirror(field, "io.jettra.wui.core.annotations.PropertiesLabel");
            if (propLabel != null) {
                String val = getAnnotationValue(propLabel, "value", "");
                String lbl = getAnnotationValue(propLabel, "label", "");
                metaBuilder.addStatement("fm_$L.propertiesLabelValue = $S", name, val);
                metaBuilder.addStatement("fm_$L.propertiesLabelLabel = $S", name, lbl);
            }

            // ViewSelectOne
            AnnotationMirror vso = getAnnotationMirror(field, "io.jettra.wui.core.annotations.ViewSelectOne");
            if (vso != null) {
                metaBuilder.addStatement("fm_$L.hasViewSelectOne = true", name);
                metaBuilder.addStatement("fm_$L.vsoSource = $S", name, getAnnotationValue(vso, "source", ""));
                metaBuilder.addStatement("fm_$L.vsoMethod = $S", name, getAnnotationValue(vso, "method", ""));
                metaBuilder.addStatement("fm_$L.vsoLabel = $S", name, getAnnotationValue(vso, "label", ""));
                metaBuilder.addStatement("fm_$L.vsoFilter = $S", name, getAnnotationValue(vso, "filter", ""));
                metaBuilder.addStatement("fm_$L.vsoFieldOnlyMasterTable = $S", name, getAnnotationValue(vso, "fieldOnlyMasterTable", ""));
            }

            // ViewSelectMany
            AnnotationMirror vsm = getAnnotationMirror(field, "io.jettra.wui.core.annotations.ViewSelectMany");
            if (vsm != null) {
                metaBuilder.addStatement("fm_$L.hasViewSelectMany = true", name);
                metaBuilder.addStatement("fm_$L.vsmSource = $S", name, getAnnotationValue(vsm, "source", ""));
                metaBuilder.addStatement("fm_$L.vsmMethod = $S", name, getAnnotationValue(vsm, "method", ""));
                metaBuilder.addStatement("fm_$L.vsmLabel = $S", name, getAnnotationValue(vsm, "label", ""));
                metaBuilder.addStatement("fm_$L.vsmFilter = $S", name, getAnnotationValue(vsm, "filter", ""));
                metaBuilder.addStatement("fm_$L.vsmFieldOnlyMasterTable = $S", name, getAnnotationValue(vsm, "fieldOnlyMasterTable", ""));
            }

            // ViewDataTable
            AnnotationMirror vdt = getAnnotationMirror(field, "io.jettra.wui.core.annotations.ViewDataTable");
            if (vdt != null) {
                metaBuilder.addStatement("fm_$L.hasViewDataTable = true", name);
                metaBuilder.addStatement("fm_$L.vdtRow = $S", name, getAnnotationValue(vdt, "row", ""));
                metaBuilder.addStatement("fm_$L.vdtSource = $S", name, getAnnotationValue(vdt, "source", ""));
                metaBuilder.addStatement("fm_$L.vdtMethod = $S", name, getAnnotationValue(vdt, "method", ""));
                metaBuilder.addStatement("fm_$L.vdtFilter = $S", name, getAnnotationValue(vdt, "filter", ""));
                metaBuilder.addStatement("fm_$L.vdtEditableRow = $S", name, getAnnotationValue(vdt, "editablerow", ""));
                metaBuilder.addStatement("fm_$L.vdtShowRowInMasterTable = $L", name, getAnnotationValue(vdt, "showRowInMasterTable", "true"));
                metaBuilder.addStatement("fm_$L.vdtEditableRowMaster = $L", name, getAnnotationValue(vdt, "editableRowMaster", "true"));

                // Save nested type for getNestedFieldValue
                if (type instanceof DeclaredType) {
                    List<? extends TypeMirror> typeArgs = ((DeclaredType) type).getTypeArguments();
                    if (!typeArgs.isEmpty()) {
                        nestedTypes.add(typeArgs.get(0));
                    }
                }
            }

            // TableColumnField
            AnnotationMirror tcf = getAnnotationMirror(field, "io.jettra.wui.core.annotations.TableColumnField");
            if (tcf != null) {
                metaBuilder.addStatement("fm_$L.hasTableColumnField = true", name);
                metaBuilder.addStatement("fm_$L.tcfField = $S", name, getAnnotationValue(tcf, "field", ""));
            }

            // Compute
            AnnotationMirror cmp = getAnnotationMirror(field, "io.jettra.rules.annotations.Compute");
            if (cmp != null) {
                metaBuilder.addStatement("fm_$L.hasCompute = true", name);
                metaBuilder.addStatement("fm_$L.computeEditable = $L", name, getAnnotationValue(cmp, "editable", "false"));
            }

            metaBuilder.addStatement("list.add(fm_$L)", name);

            String getter = type.getKind() == TypeKind.BOOLEAN ? "is" + capName : "get" + capName;
            String setter = "set" + capName;

            getValBuilder.addStatement("case $S: return item.$L()", name, getter);
            
            // setFieldValue requires casting
            if (type.getKind().isPrimitive()) {
                if (type.getKind() == TypeKind.INT) {
                    setValBuilder.addStatement("case $S: item.$L((Integer) value); break", name, setter);
                } else if (type.getKind() == TypeKind.BOOLEAN) {
                    setValBuilder.addStatement("case $S: item.$L((Boolean) value); break", name, setter);
                } else if (type.getKind() == TypeKind.DOUBLE) {
                    setValBuilder.addStatement("case $S: item.$L((Double) value); break", name, setter);
                }
            } else {
                setValBuilder.addStatement("case $S: item.$L(($T) value); break", name, setter, rawType);
            }
        }
        metaBuilder.addStatement("return list");
        getValBuilder.addStatement("default: return null");
        getValBuilder.endControlFlow();
        
        setValBuilder.endControlFlow();

        classBuilder.addMethod(metaBuilder.build());
        classBuilder.addMethod(getValBuilder.build());
        classBuilder.addMethod(setValBuilder.build());

        // Nested methods
        generateNestedMethods(classBuilder, nestedTypes, metadataClass);
    }

    private void generateNestedMethods(TypeSpec.Builder classBuilder, Set<TypeMirror> nestedTypes, ClassName metadataClass) {
        MethodSpec.Builder getNestedValBuilder = MethodSpec.methodBuilder("getNestedFieldValue")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "item")
                .addParameter(String.class, "fieldName")
                .returns(Object.class);

        MethodSpec.Builder setNestedValBuilder = MethodSpec.methodBuilder("setNestedFieldValue")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "item")
                .addParameter(String.class, "fieldName")
                .addParameter(Object.class, "value");

        MethodSpec.Builder getNestedMetaBuilder = MethodSpec.methodBuilder("getNestedFieldMetadata")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "item")
                .addParameter(String.class, "fieldName")
                .returns(metadataClass);

        for (TypeMirror nestedType : nestedTypes) {
            TypeElement nestedElement = (TypeElement) processingEnv.getTypeUtils().asElement(nestedType);
            if (nestedElement != null) {
                TypeName rawType = TypeName.get(nestedType);
                if (rawType instanceof ParameterizedTypeName) rawType = ((ParameterizedTypeName) rawType).rawType;

                getNestedValBuilder.beginControlFlow("if (item instanceof $T)", rawType);
                getNestedValBuilder.addStatement("$T nestedItem = ($T) item", rawType, rawType);
                getNestedValBuilder.beginControlFlow("switch (fieldName)");

                setNestedValBuilder.beginControlFlow("if (item instanceof $T)", rawType);
                setNestedValBuilder.addStatement("$T nestedItem = ($T) item", rawType, rawType);
                setNestedValBuilder.beginControlFlow("switch (fieldName)");

                getNestedMetaBuilder.beginControlFlow("if (item instanceof $T)", rawType);
                getNestedMetaBuilder.beginControlFlow("switch (fieldName)");

                for (VariableElement field : ElementFilter.fieldsIn(nestedElement.getEnclosedElements())) {
                    String name = field.getSimpleName().toString();
                    String capName = name.substring(0, 1).toUpperCase() + name.substring(1);
                    TypeMirror type = field.asType();
                    TypeName fieldRawType = TypeName.get(type);
                    if (fieldRawType instanceof ParameterizedTypeName) fieldRawType = ((ParameterizedTypeName) fieldRawType).rawType;

                    String getter = type.getKind() == TypeKind.BOOLEAN ? "is" + capName : "get" + capName;
                    String setter = "set" + capName;

                    getNestedValBuilder.addStatement("case $S: return nestedItem.$L()", name, getter);

                    if (type.getKind().isPrimitive()) {
                        if (type.getKind() == TypeKind.INT) {
                            setNestedValBuilder.addStatement("case $S: nestedItem.$L((Integer) value); break", name, setter);
                        } else if (type.getKind() == TypeKind.BOOLEAN) {
                            setNestedValBuilder.addStatement("case $S: nestedItem.$L((Boolean) value); break", name, setter);
                        } else if (type.getKind() == TypeKind.DOUBLE) {
                            setNestedValBuilder.addStatement("case $S: nestedItem.$L((Double) value); break", name, setter);
                        }
                    } else {
                        setNestedValBuilder.addStatement("case $S: nestedItem.$L(($T) value); break", name, setter, fieldRawType);
                    }

                    // Metadata for nested field
                    getNestedMetaBuilder.beginControlFlow("case $S:", name);
                    getNestedMetaBuilder.addStatement("$T fm_$L = new $T($S, $T.class)", metadataClass, name, metadataClass, name, fieldRawType);
                    addAnnotationCheck(getNestedMetaBuilder, field, "io.jettra.wui.core.annotations.ViewSelectOne", name, "hasViewSelectOne = true");
                    AnnotationMirror vso = getAnnotationMirror(field, "io.jettra.wui.core.annotations.ViewSelectOne");
                    if (vso != null) {
                        getNestedMetaBuilder.addStatement("fm_$L.vsoSource = $S", name, getAnnotationValue(vso, "source", ""));
                        getNestedMetaBuilder.addStatement("fm_$L.vsoMethod = $S", name, getAnnotationValue(vso, "method", ""));
                        getNestedMetaBuilder.addStatement("fm_$L.vsoLabel = $S", name, getAnnotationValue(vso, "label", ""));
                        getNestedMetaBuilder.addStatement("fm_$L.vsoFilter = $S", name, getAnnotationValue(vso, "filter", ""));
                    }
                    getNestedMetaBuilder.addStatement("return fm_$L", name);
                    getNestedMetaBuilder.endControlFlow();
                }
                
                getNestedValBuilder.addStatement("default: return null");
                getNestedValBuilder.endControlFlow(); // switch
                getNestedValBuilder.endControlFlow(); // if

                setNestedValBuilder.endControlFlow(); // switch
                setNestedValBuilder.endControlFlow(); // if

                getNestedMetaBuilder.endControlFlow(); // switch
                getNestedMetaBuilder.endControlFlow(); // if
            }
        }
        getNestedValBuilder.addStatement("return null");
        getNestedMetaBuilder.addStatement("return null");

        classBuilder.addMethod(getNestedValBuilder.build());
        classBuilder.addMethod(setNestedValBuilder.build());
        classBuilder.addMethod(getNestedMetaBuilder.build());
    }

    private void addAnnotationCheck(MethodSpec.Builder builder, VariableElement field, String annotationClass, String name, String assignment) {
        if (getAnnotationMirror(field, annotationClass) != null) {
            builder.addStatement("fm_$L.$L", name, assignment);
        }
    }

    private AnnotationMirror getAnnotationMirror(VariableElement field, String className) {
        for (AnnotationMirror mirror : field.getAnnotationMirrors()) {
            if (mirror.getAnnotationType().toString().equals(className)) {
                return mirror;
            }
        }
        return null;
    }

    private String getAnnotationValue(AnnotationMirror mirror, String key, String defaultValue) {
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : mirror.getElementValues().entrySet()) {
            if (entry.getKey().getSimpleName().toString().equals(key)) {
                String val = entry.getValue().getValue().toString();
                if (val.startsWith("\"") && val.endsWith("\"")) {
                    val = val.substring(1, val.length() - 1);
                }
                return val;
            }
        }
        return defaultValue;
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

    private TypeMirror getGoalType(ModelToRecordConversor annotation) {
        try {
            annotation.goal();
        } catch (MirroredTypeException mte) {
            return mte.getTypeMirror();
        }
        return null;
    }

    private TypeMirror getExtendsType(CrudView annotation) {
        try {
            annotation.extendsClass();
        } catch (MirroredTypeException mte) {
            return mte.getTypeMirror();
        }
        return null;
    }

    private void generatePage(TypeElement interfaceElement) {
        CrudView annotation = interfaceElement.getAnnotation(CrudView.class);
        String packageName = processingEnv.getElementUtils().getPackageOf(interfaceElement).getQualifiedName().toString();
        
        String interfaceName = interfaceElement.getSimpleName().toString();
        String generatedClassName = interfaceName;
        if (generatedClassName.endsWith("Def")) {
            generatedClassName = generatedClassName.substring(0, generatedClassName.length() - 3);
        } else if (generatedClassName.endsWith("Config")) {
            generatedClassName = generatedClassName.substring(0, generatedClassName.length() - 6);
        } else {
            generatedClassName = generatedClassName + "Impl";
        }
        
        TypeMirror extendsType = getExtendsType(annotation);
        TypeName extendsTypeName = TypeName.get(extendsType);
        
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(generatedClassName)
                .addModifiers(Modifier.PUBLIC)
                .superclass(extendsTypeName)
                .addSuperinterface(TypeName.get(interfaceElement.asType()));

        // Copy annotations from interface to class (like @JettraPageSincronized)
        for (AnnotationMirror mirror : interfaceElement.getAnnotationMirrors()) {
            if (!mirror.getAnnotationType().toString().equals("io.jettra.wui.core.annotations.CrudView")) {
                classBuilder.addAnnotation(AnnotationSpec.get(mirror));
            }
        }
        
        // @CrudView still applied to the class to satisfy any remaining runtime checks like JettraMVC routing if any, but autoRender=false
        AnnotationSpec.Builder crudViewBuilder = AnnotationSpec.builder(CrudView.class)
                .addMember("model", "$T.class", TypeName.get(getModelType(annotation)))
                .addMember("autoRender", "false")
                .addMember("editable", "$L", annotation.editable())
                .addMember("report", "$L", annotation.report())
                .addMember("reportOrientation", "$S", annotation.reportOrientation())
                .addMember("reportTitle", "$S", annotation.reportTitle())
                .addMember("reportHeaderColor", "$S", annotation.reportHeaderColor());

        TypeMirror repoType = getRepoType(annotation);
        if (repoType != null && !repoType.toString().equals("void")) {
            crudViewBuilder.addMember("repository", "$T.class", TypeName.get(repoType));
        }
        TypeMirror controllerType = getControllerType(annotation);
        if (controllerType != null && !controllerType.toString().equals("void")) {
            crudViewBuilder.addMember("controller", "$T.class", TypeName.get(controllerType));
        }
        classBuilder.addAnnotation(crudViewBuilder.build());

        // Field for properties
        classBuilder.addField(FieldSpec.builder(Properties.class, "msg", Modifier.PRIVATE)
                .addAnnotation(AnnotationSpec.builder(ClassName.get("io.jettra.core.inject.annotation", "InjectProperties"))
                        .addMember("name", "$S", "messages")
                        .build())
                .build());

        // Constructor
        MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addStatement("super($S)", annotation.title());
        classBuilder.addMethod(constructor.build());

        // initCenter
        String handlerClassName = interfaceName + "CrudHandler";
        ClassName handlerType = ClassName.get(packageName, handlerClassName);

        MethodSpec.Builder initCenter = MethodSpec.methodBuilder("initCenter")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .addParameter(ClassName.get("io.jettra.wui.complex", "Center"), "center")
                .addParameter(String.class, "username");

        initCenter.addStatement("$T handler = new $T()", handlerType, handlerType);
        
        boolean useController = controllerType != null && !controllerType.toString().equals("void");
        TypeName dataAccessTypeName = TypeName.get(useController ? controllerType : repoType);

        initCenter.addStatement("io.jettra.wui.complex.CrudView crudComponent = new io.jettra.wui.complex.CrudView($T.class, $T.class, msg, handler)", 
                TypeName.get(getModelType(annotation)), dataAccessTypeName);
        
        initCenter.addStatement("crudComponent.setEditable($L)", annotation.editable());
        initCenter.addStatement("crudComponent.setReportEnabled($L)", annotation.report());
        initCenter.addStatement("crudComponent.setReportShowViewer($L)", annotation.reportShowViewer());
        initCenter.addStatement("crudComponent.setReportAllowPrint($L)", annotation.reportAllowPrint());
        initCenter.addStatement("crudComponent.setReportAllowPdf($L)", annotation.reportAllowPdf());
        initCenter.addStatement("crudComponent.setReportAllowExcel($L)", annotation.reportAllowExcel());
        initCenter.addStatement("crudComponent.setReportAllowCsv($L)", annotation.reportAllowCsv());
        initCenter.addStatement("crudComponent.setReportAllowWord($L)", annotation.reportAllowWord());
        initCenter.addStatement("crudComponent.setReportOrientation($S)", annotation.reportOrientation());
        initCenter.addStatement("crudComponent.setReportCustomTitle($S)", annotation.reportTitle());
        initCenter.addStatement("crudComponent.setReportHeaderColor($S)", annotation.reportHeaderColor());
        initCenter.addStatement("crudComponent.setParentPage(this)");
        
        if (annotation.autoRender()) {
            initCenter.addStatement("crudComponent.build()");
            initCenter.addStatement("center.add(crudComponent)");
        }

        // Call afterInitCenter only if defined
        boolean hasAfterInit = false;
        for (javax.lang.model.element.Element enclosed : interfaceElement.getEnclosedElements()) {
            if (enclosed.getKind() == javax.lang.model.element.ElementKind.METHOD && enclosed.getSimpleName().toString().equals("afterInitCenter")) {
                hasAfterInit = true;
                break;
            }
        }
        if (hasAfterInit) {
            initCenter.addStatement("this.afterInitCenter(center, username)");
        }

        classBuilder.addMethod(initCenter.build());


        JavaFile javaFile = JavaFile.builder(packageName, classBuilder.build()).build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
