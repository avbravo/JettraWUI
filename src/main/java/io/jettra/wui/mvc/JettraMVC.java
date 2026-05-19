package io.jettra.wui.mvc;

import io.jettra.wui.core.Page;
import io.jettra.wui.core.UIComponent;
import io.jettra.wui.core.annotations.Inject;
import io.jettra.wui.core.annotations.InjectViewModel;
import io.jettra.wui.core.annotations.JettraViewModel;
import io.jettra.wui.core.annotations.InjectProperties;
import io.jettra.wui.core.annotations.CrudView;
import io.jettra.wui.core.annotations.CrudHandler;
import io.jettra.wui.complex.Center;
import io.jettra.wui.sync.JettraSyncManager;
import io.jettra.wui.sync.SyncType;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import com.sun.net.httpserver.HttpExchange;
import io.jettra.wui.core.annotations.ViewSelectOne;
import io.jettra.wui.core.annotations.ViewDataTable;

/**
 * Utility class to handle MVC binding between ViewModels and UIComponents.
 */
public class JettraMVC {

    /**
     * Inyecta dependencias marcadas con @Inject.
     */
    public static void injectDependencies(Page page) {
        for (Field field : page.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                try {
                    field.setAccessible(true);
                    if (field.get(page) == null) {
                        Class<?> type = field.getType();
                        Class<?> implClass = type;
                        if (type.isInterface()) {
                            try {
                                // Convención simple: Interface + Impl
                                implClass = Class.forName(type.getName() + "Impl");
                            } catch (ClassNotFoundException e) {
                                System.err.println("Implementation not found for interface " + type.getName());
                                continue;
                            }
                        }
                        Object instance = implClass.getDeclaredConstructor().newInstance();
                        field.set(page, instance);
                    }
                } catch (Exception e) {
                    System.err.println("Error injecting dependency into " + field.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    /**
     * Scans a page for @InjectViewModel fields and initializes them.
     */
    public static void initializeViewModels(Page page) {
        for (Field field : page.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectViewModel.class)) {
                try {
                    field.setAccessible(true);
                    if (field.get(page) == null) {
                        Object viewModel = field.getType().getDeclaredConstructor().newInstance();
                        field.set(page, viewModel);
                    }
                } catch (Exception e) {
                    System.err.println("Error initializing ViewModel: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Inyecta archivos de propiedades en campos con @InjectProperties.
     */
    public static void injectProperties(Page page, Map<String, String> params) {
        String lang = params.getOrDefault("lang", "es");
        for (Field field : page.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectProperties.class)) {
                try {
                    InjectProperties anno = field.getAnnotation(InjectProperties.class);
                    String baseName = anno.name();
                    String fileName = baseName + "_" + lang + ".properties";
                    
                    Properties props = new Properties();
                    try (InputStream is = page.getClass().getClassLoader().getResourceAsStream(fileName)) {
                        if (is != null) {
                            props.load(new InputStreamReader(is, StandardCharsets.UTF_8));
                        } else {
                            // Intenta cargar el archivo base sin sufijo de idioma si no existe el específico
                            try (InputStream isBase = page.getClass().getClassLoader().getResourceAsStream(baseName + ".properties")) {
                                if (isBase != null) {
                                    props.load(new InputStreamReader(isBase, StandardCharsets.UTF_8));
                                }
                            }
                        }
                    }
                    
                    field.setAccessible(true);
                    field.set(page, props);
                } catch (Exception e) {
                    System.err.println("Error injecting properties into " + field.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    /**
     * Updates an injected ViewModel with data from a request (form parameters).
     */
    public static void updateModelFromRequest(Page page, Map<String, String> formData) {
        for (Field field : page.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectViewModel.class)) {
                try {
                    field.setAccessible(true);
                    Object viewModel = field.get(page);
                    if (viewModel != null) {
                        updateModelFields(viewModel, formData);
                    }
                } catch (Exception e) {
                    System.err.println("Error updating ViewModel from request: " + e.getMessage());
                }
            }
        }
    }

    private static void updateModelFields(Object model, Map<String, String> formData) {
        for (Field field : model.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String value = formData.get(field.getName());
            if (value != null) {
                try {
                    if (field.getType().equals(String.class)) {
                        field.set(model, value);
                    } else if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
                        field.set(model, Integer.parseInt(value));
                    } else if (field.getType().equals(double.class) || field.getType().equals(Double.class)) {
                        field.set(model, Double.parseDouble(value));
                    } else if (field.getType().equals(float.class) || field.getType().equals(Float.class)) {
                        field.set(model, Float.parseFloat(value));
                    } else if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
                        field.set(model, Long.parseLong(value));
                    } else if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) {
                        field.set(model, Boolean.parseBoolean(value));
                    } else if (field.getType().equals(java.time.LocalDate.class)) {
                        field.set(model, java.time.LocalDate.parse(value));
                    } else if (field.getType().equals(java.util.Date.class)) {
                        field.set(model, new java.text.SimpleDateFormat("yyyy-MM-dd").parse(value));
                    } else if (field.isAnnotationPresent(ViewSelectOne.class)) {
                        ViewSelectOne vso = field.getAnnotation(ViewSelectOne.class);
                        Class<?> sourceClass = null;
                        String pkg = model.getClass().getPackageName();
                        List<String> attempts = Arrays.asList(vso.source(), pkg + "." + vso.source(), pkg.replace(".model", ".repository") + "." + vso.source(), pkg.replace(".model", ".services") + "." + vso.source(), pkg.replace(".model", ".controller") + "." + vso.source());
                        for (String att : attempts) {
                            try { sourceClass = Class.forName(att, true, model.getClass().getClassLoader()); break; } catch(Exception ex) {}
                        }
                        if (sourceClass != null) {
                            try {
                                Method findById = sourceClass.getMethod("findById", String.class);
                                Object relObj = findById.invoke(null, value);
                                field.set(model, relObj);
                            } catch(Exception ex) {
                                try {
                                    Method findAll = sourceClass.getMethod(vso.method());
                                    Object res = findAll.invoke(null);
                                    if (res instanceof List) {
                                        for (Object obj : (List<?>) res) {
                                            if (value.equals(getIdValueForSource(obj))) {
                                                field.set(model, obj);
                                                break;
                                            }
                                        }
                                    }
                                } catch(Exception e2) {}
                            }
                        }
                    } else if (field.isAnnotationPresent(ViewDataTable.class)) {
                        Class<?> itemClass = null;
                        if (field.getGenericType() instanceof java.lang.reflect.ParameterizedType) {
                            itemClass = (Class<?>) ((java.lang.reflect.ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                        }
                        if (itemClass != null && value.startsWith("[") && value.endsWith("]")) {
                            List<Object> list = new ArrayList<>();
                            String content = value.substring(1, value.length() - 1).trim();
                            if (!content.isEmpty()) {
                                String[] items = content.split("\\},\\s*\\{");
                                for (String itemStr : items) {
                                    itemStr = itemStr.replace("{", "").replace("}", "").trim();
                                    if (itemStr.isEmpty()) continue;
                                    Object itemObj = itemClass.getDeclaredConstructor().newInstance();
                                    String[] pairs = itemStr.split(",");
                                    for (String pair : pairs) {
                                        String[] kv = pair.split(":");
                                        if (kv.length >= 2) {
                                            String k = kv[0].replace("\"", "").replace("'", "").trim();
                                            String v = kv[1].replace("\"", "").replace("'", "").trim();
                                            try {
                                                Field itemF = itemClass.getDeclaredField(k);
                                                itemF.setAccessible(true);
                                                if (itemF.getType().equals(String.class)) {
                                                    itemF.set(itemObj, v);
                                                } else if (itemF.getType().equals(int.class) || itemF.getType().equals(Integer.class)) {
                                                    itemF.set(itemObj, Integer.parseInt(v));
                                                } else if (itemF.getType().equals(double.class) || itemF.getType().equals(Double.class)) {
                                                    itemF.set(itemObj, Double.parseDouble(v));
                                                } else if (itemF.getType().equals(float.class) || itemF.getType().equals(Float.class)) {
                                                    itemF.set(itemObj, Float.parseFloat(v));
                                                } else if (itemF.getType().equals(long.class) || itemF.getType().equals(Long.class)) {
                                                    itemF.set(itemObj, Long.parseLong(v));
                                                } else if (itemF.getType().equals(boolean.class) || itemF.getType().equals(Boolean.class)) {
                                                    itemF.set(itemObj, Boolean.parseBoolean(v));
                                                }
                                            } catch(Exception ex) {}
                                        }
                                    }
                                    list.add(itemObj);
                                }
                            }
                            field.set(model, list);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error setting field " + field.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    private static String getIdValueForSource(Object item) {
        if (item == null) return "";
        if (item instanceof String) return (String) item;
        if (item instanceof Number) return item.toString();
        
        Class<?> clazz = item.getClass();
        try {
            List<String> commonNames = Arrays.asList("code", "id", "id" + clazz.getSimpleName());
            for (Field f : clazz.getDeclaredFields()) {
                if (commonNames.stream().anyMatch(name -> name.equalsIgnoreCase(f.getName()))) {
                    f.setAccessible(true);
                    Object val = f.get(item);
                    if (val != null) return val.toString();
                }
            }
            for (Method m : clazz.getMethods()) {
                if (m.getName().equalsIgnoreCase("getCode") || m.getName().equalsIgnoreCase("getId") || m.getName().equalsIgnoreCase("getid" + clazz.getSimpleName())) {
                    if (m.getParameterCount() == 0) {
                        Object val = m.invoke(item);
                        if (val != null) return val.toString();
                    }
                }
            }
            for (Field f : clazz.getDeclaredFields()) {
                if (f.getName().toLowerCase().contains("id") || f.getName().toLowerCase().contains("code")) {
                    f.setAccessible(true);
                    Object val = f.get(item);
                    if (val != null) return val.toString();
                }
            }
        } catch (Exception e) {}
        return item.toString();
    }

    /**
     * Synchronizes UI component values with model data.
     * This scans the component tree and finds components whose name/id matches a field in the model.
     */
    public static void updateViewFromModel(Page page) {
        for (Field field : page.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectViewModel.class)) {
                try {
                    field.setAccessible(true);
                    Object viewModel = field.get(page);
                    if (viewModel != null) {
                        syncComponentsWithModel(page, viewModel);
                    }
                } catch (Exception e) {
                    System.err.println("Error updating view from model: " + e.getMessage());
                }
            }
        }
    }

    private static void syncComponentsWithModel(UIComponent component, Object model) {
        // If it's an input component with a 'name', try to sync its value
        String name = component.getProperties().get("name");
        if (name != null) {
            try {
                Field field = model.getClass().getDeclaredField(name);
                field.setAccessible(true);
                Object val = field.get(model);
                if (val != null) {
                    component.setProperty("value", val.toString());
                }
            } catch (NoSuchFieldException e) {
                // Not a model field, skip
            } catch (Exception e) {
                System.err.println("Error syncing component " + name + ": " + e.getMessage());
            }
        }

        // Recurse into children
        for (UIComponent child : component.getChildren()) {
            syncComponentsWithModel(child, model);
        }
    }

    /**
     * Procesa la anotación @CrudView si está presente en la página.
     */
    public static void processCrudView(Page page) {
        if (page.getClass().isAnnotationPresent(CrudView.class)) {
            try {
                CrudView anno = page.getClass().getAnnotation(CrudView.class);
                if (!anno.autoRender()) {
                    return;
                }
                Class<?> modelClass = anno.model();
                Class<?> repoClass = anno.repository();

                if (modelClass == null || repoClass == null) {
                    System.err.println("[JettraMVC] Error: Could not resolve model or repository class for @CrudView");
                    return;
                }

                // Obtener propiedades de mensajes inyectadas
                Properties msg = null;
                for (Field field : page.getClass().getDeclaredFields()) {
                    if (field.isAnnotationPresent(InjectProperties.class)) {
                        field.setAccessible(true);
                        msg = (Properties) field.get(page);
                        break;
                    }
                }

                // Intentar obtener el handler generado por Annotation Processing
                CrudHandler<?> handler = getCrudHandler(page.getClass());

                io.jettra.wui.complex.CrudView crudComponent = new io.jettra.wui.complex.CrudView(modelClass, repoClass, msg, handler);
                crudComponent.setEditable(anno.editable());
                crudComponent.setReportEnabled(anno.report());
                crudComponent.setReportShowViewer(anno.reportShowViewer());
                crudComponent.setReportAllowPrint(anno.reportAllowPrint());
                crudComponent.setReportAllowPdf(anno.reportAllowPdf());
                crudComponent.setReportAllowExcel(anno.reportAllowExcel());
                crudComponent.setReportAllowCsv(anno.reportAllowCsv());
                crudComponent.setReportOrientation(anno.reportOrientation());
                crudComponent.setReportCustomTitle(anno.reportTitle());
                crudComponent.setReportHeaderColor(anno.reportHeaderColor());
                crudComponent.setParentPage(page);
                crudComponent.build();
                // Buscar el Center en JettraDashboardPage si aplica
                try {
                    Field centerField = null;
                    Class<?> current = page.getClass();
                    while(current != null && centerField == null) {
                        try {
                            centerField = current.getDeclaredField("center");
                        } catch(Exception e) {
                            current = current.getSuperclass();
                        }
                    }
                    
                    if (centerField != null) {
                        centerField.setAccessible(true);
                        Object center = centerField.get(page);
                        if (center instanceof io.jettra.wui.complex.Center) {
                            ((io.jettra.wui.complex.Center) center).add(crudComponent);
                        } else {
                            page.add(crudComponent);
                        }
                    } else {
                        page.add(crudComponent);
                    }
                } catch (Exception e) {
                    page.add(crudComponent);
                }

            } catch (Exception e) {
                System.err.println("[JettraMVC] Error processing @CrudView: " + e.getMessage());
            }
        }
    }

    private static Class<?> resolveClass(String name, String defaultPackage) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName(defaultPackage + "." + name);
            } catch (ClassNotFoundException e2) {
                return null;
            }
        }
    }

    /**
     * Procesa la lógica de persistencia para @CrudView en el POST.
     */
    public static boolean handleCrudGet(Page page, Map<String, String> params) {
        if (page.getClass().isAnnotationPresent(CrudView.class)) {
            try {
                String action = params.get("action");
                if ("report".equals(action)) {
                    CrudView anno = page.getClass().getAnnotation(CrudView.class);
                    Class<?> modelClass = anno.model();
                    Class<?> repoClass = anno.repository();
                    String format = params.get("format");
                    if (format == null) format = "pdf";
                    boolean print = "true".equals(params.get("print"));
                    String id = params.get("id");
                    generateReport(page, modelClass, repoClass, format, print, id);
                    return true;
                }
            } catch (Exception e) {
                System.err.println("[JettraMVC] Error in handleCrudGet: " + e.getMessage());
            }
        }
        return false;
    }

    /**
     * Obtiene el handler generado para una página.
     */
    public static CrudHandler<?> getCrudHandler(Class<?> pageClass) {
        try {
            String handlerClassName = pageClass.getName() + "CrudHandler";
            Class<?> handlerClass = Class.forName(handlerClassName);
            return (CrudHandler<?>) handlerClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Procesa la lógica de persistencia para @CrudView en el POST.
     */
    public static boolean handleCrudPost(Page page, Map<String, String> params) {
        if (page.getClass().isAnnotationPresent(CrudView.class)) {
            try {
                CrudHandler<Object> handler = (CrudHandler<Object>) getCrudHandler(page.getClass());
                CrudView anno = page.getClass().getAnnotation(CrudView.class);
                Class<?> modelClass = anno.model();
                Class<?> repoClass = anno.repository();
                String action = params.get("action");
                if (action == null || action.isEmpty()) return false;

                if ("save".equals(action)) {
                    Object model;
                    if (handler != null) {
                        model = handler.createInstance();
                        handler.updateFields(model, params);
                        handler.save(model);
                    } else {
                        model = modelClass.getDeclaredConstructor().newInstance();
                        updateModelFields(model, params);
                        Method save = repoClass.getMethod("save", modelClass);
                        save.invoke(null, model);
                    }
                    
                    String entityName = modelClass.getSimpleName();
                    JettraSyncManager.notifyChange(entityName, SyncType.UPDATE, "System"); 
                    return true;
                } else if ("delete".equals(action)) {
                    String code = params.get("code"); 
                    if (code == null) code = params.get("id");
                    if (code == null && modelClass.getDeclaredFields().length > 0) {
                        String firstFieldName = modelClass.getDeclaredFields()[0].getName();
                        code = params.get(firstFieldName);
                    }
                    if (code == null) {
                        for (Field f : modelClass.getDeclaredFields()) {
                            if (f.getName().toLowerCase().contains("id") || f.getName().toLowerCase().contains("code")) {
                                code = params.get(f.getName());
                                if (code != null) break;
                            }
                        }
                    }
                    
                    if (handler != null) {
                        handler.delete(code);
                    } else {
                        Method delete = repoClass.getMethod("delete", String.class);
                        delete.invoke(null, code);
                    }
                    
                    String entityName = modelClass.getSimpleName();
                    JettraSyncManager.notifyChange(entityName, SyncType.DELETE, "System");
                    return true;
                } else if ("report".equals(action)) {
                    String format = params.get("format");
                    if (format == null) format = "pdf";
                    boolean print = "true".equals(params.get("print"));
                    String id = params.get("id");
                    generateReport(page, modelClass, repoClass, format, print, id);
                    return true;
                }
            } catch (Exception e) {
                System.err.println("[JettraMVC] Error in handleCrudPost: " + e.getMessage());
            }
        }
        return false;
    }

    public static void generateReport(Page page, Class<?> modelClass, Class<?> repoClass, String format, boolean print) {
        generateReport(page, modelClass, repoClass, format, print, null);
    }

    public static void generateReport(Page page, Class<?> modelClass, Class<?> repoClass, String format, boolean print, String id) {
        try {
            // 1. Obtener datos
            List<Object> data = new ArrayList<>();
            if (id != null && !id.trim().isEmpty()) {
                Method findById = repoClass.getMethod("findById", String.class);
                Object singleObj = findById.invoke(null, id);
                if (singleObj != null) {
                    data.add(singleObj);
                }
            } else {
                Method findAll = repoClass.getMethod("findAll");
                data.addAll((List<?>) findAll.invoke(null));
            }

            // 2. Cargar JettraReport vía reflexión para evitar dependencia circular
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Class<?> reportClass = null;
            try {
                reportClass = Class.forName("com.jettra.report.Report", true, loader);
            } catch (Exception e) {
                loader = page.getClass().getClassLoader();
                reportClass = Class.forName("com.jettra.report.Report", true, loader);
            }
            
            Class<?> textElementClass = Class.forName("com.jettra.report.Report$TextElement", true, loader);
            Class<?> tableClass = Class.forName("com.jettra.report.Report$Table", true, loader);
            Class<?> columnClass = Class.forName("com.jettra.report.Report$Column", true, loader);
            Class<?> headerClass = Class.forName("com.jettra.report.Report$Header", true, loader);
            Class<?> footerClass = Class.forName("com.jettra.report.Report$Footer", true, loader);
            Class<?> detailClass = Class.forName("com.jettra.report.Report$Detail", true, loader);

            Object report = reportClass.getConstructor(String.class).newInstance("Reporte de " + modelClass.getSimpleName());
            
            // Set Data
            Method setData = reportClass.getMethod("setData", List.class);
            setData.invoke(report, data);

            // Orientation
            Object pageSettings = reportClass.getMethod("getPageSettings").invoke(report);
            Class<?> orientationEnum = Class.forName("com.jettra.report.Report$PageSettings$Orientation", true, loader);
            
            CrudView anno = page.getClass().getAnnotation(CrudView.class);
            String orientationStr = (anno != null) ? anno.reportOrientation() : "PORTRAIT";
            String customTitle = (anno != null) ? anno.reportTitle() : "";
            String headerColor = (anno != null) ? anno.reportHeaderColor() : "#000000";

            Object orientationVal = Enum.valueOf((Class<Enum>)orientationEnum, orientationStr.toUpperCase());
            pageSettings.getClass().getMethod("setOrientation", orientationEnum).invoke(pageSettings, orientationVal);

            // Header
            Object header = reportClass.getMethod("getHeader").invoke(report);
            String finalTitle = (customTitle != null && !customTitle.isEmpty()) ? customTitle : "REPORTE DE " + modelClass.getSimpleName().toUpperCase();
            Object titleEl = textElementClass.getConstructor(String.class).newInstance(finalTitle);
            
            textElementClass.getMethod("setFontColor", String.class).invoke(titleEl, headerColor);
            textElementClass.getMethod("setBold", boolean.class).invoke(titleEl, true);
            textElementClass.getMethod("setFontSize", int.class).invoke(titleEl, 14);

            headerClass.getMethod("addElement", Class.forName("com.jettra.report.Report$ReportElement")).invoke(header, titleEl);

            // Table
            Object table = tableClass.getConstructor().newInstance();
            Method addColumn = tableClass.getMethod("addColumn", columnClass);
            
            Field[] fields = modelClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(io.jettra.wui.core.annotations.ViewDataTable.class) && !field.getAnnotation(io.jettra.wui.core.annotations.ViewDataTable.class).showRowInMasterTable()) {
                    continue;
                }
                String detailExpression = field.getName();
                if (field.isAnnotationPresent(io.jettra.wui.core.annotations.TableColumnField.class)) {
                    io.jettra.wui.core.annotations.TableColumnField tcf = field.getAnnotation(io.jettra.wui.core.annotations.TableColumnField.class);
                    if (!tcf.field().isEmpty()) {
                        // JettraReport doesn't support nested expressions like field.subfield directly yet
                        // but we can pass the field name and handle it in the viewer/exporter if needed.
                        // For now, we use the field name.
                        detailExpression = field.getName(); 
                    }
                }
                
                Object col = columnClass.getConstructor(String.class, String.class, int.class)
                        .newInstance(field.getName().toUpperCase(), detailExpression, 100);
                
                if (field.getName().equalsIgnoreCase("id") || field.getName().equalsIgnoreCase("code")) {
                    columnClass.getMethod("setFontColor", String.class).invoke(col, headerColor);
                    columnClass.getMethod("setBold", boolean.class).invoke(col, true);
                }
                
                addColumn.invoke(table, col);
            }

            Object detail = reportClass.getMethod("getDetail").invoke(report);
            detailClass.getMethod("addElement", Class.forName("com.jettra.report.Report$ReportElement", true, loader)).invoke(detail, table);

            // Footer
            Object footer = reportClass.getMethod("getFooter").invoke(report);
            Object footerEl = textElementClass.getConstructor(String.class).newInstance("Generado por JettraStack");
            footerClass.getMethod("addElement", Class.forName("com.jettra.report.Report$ReportElement")).invoke(footer, footerEl);

            // 3. Exportar a un archivo temporal
            String tmpFile = "report_" + System.currentTimeMillis() + "." + format;
            String exportMethod = "exportTo" + format.substring(0, 1).toUpperCase() + format.substring(1).toLowerCase();
            reportClass.getMethod(exportMethod, String.class).invoke(report, tmpFile);

            // 4. Enviar al navegador
            java.io.File file = new java.io.File(tmpFile);
            if (file.exists()) {
                byte[] bytes = java.nio.file.Files.readAllBytes(file.toPath());
                String contentType = "application/octet-stream";
                if ("pdf".equals(format)) contentType = "application/pdf";
                else if ("excel".equals(format) || "xlsx".equals(format)) contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                else if ("csv".equals(format)) contentType = "text/csv";
                
                page.getCurrentExchange().getResponseHeaders().set("Content-Type", contentType);
                if (print && "pdf".equals(format)) {
                    page.getCurrentExchange().getResponseHeaders().set("Content-Disposition", "inline; filename=" + file.getName());
                } else {
                    page.getCurrentExchange().getResponseHeaders().set("Content-Disposition", "attachment; filename=" + file.getName());
                }
                page.getCurrentExchange().sendResponseHeaders(200, bytes.length);
                page.getCurrentExchange().getResponseBody().write(bytes);
                page.getCurrentExchange().getResponseBody().close();
                file.delete();
            }
        } catch (Exception e) {
            System.err.println("[JettraMVC] Error generating report: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
