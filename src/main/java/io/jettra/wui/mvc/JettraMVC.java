package io.jettra.wui.mvc;

import io.jettra.wui.core.Page;
import io.jettra.wui.core.UIComponent;
import io.jettra.wui.core.annotations.Inject;
import io.jettra.wui.core.annotations.InjectViewModel;
import io.jettra.wui.core.annotations.JettraViewModel;
import io.jettra.wui.core.annotations.InjectProperties;
import io.jettra.wui.core.annotations.CrudView;
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
                    }
                    // Add more types as needed
                } catch (Exception e) {
                    System.err.println("Error setting field " + field.getName() + ": " + e.getMessage());
                }
            }
        }
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

                io.jettra.wui.complex.CrudView crudComponent = new io.jettra.wui.complex.CrudView(modelClass, repoClass, msg);
                
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
    public static boolean handleCrudPost(Page page, Map<String, String> params) {
        if (page.getClass().isAnnotationPresent(CrudView.class)) {
            try {
                CrudView anno = page.getClass().getAnnotation(CrudView.class);
                Class<?> modelClass = anno.model();
                Class<?> repoClass = anno.repository();
                String action = params.get("action");
                if (action == null || action.isEmpty()) return false;

                if ("save".equals(action)) {
                    Object model = modelClass.getDeclaredConstructor().newInstance();
                    updateModelFields(model, params);
                    Method save = repoClass.getMethod("save", modelClass);
                    save.invoke(null, model);
                    
                    String entityName = modelClass.getSimpleName();
                    JettraSyncManager.notifyChange(entityName, SyncType.UPDATE, "System"); 
                    return true;
                } else if ("delete".equals(action)) {
                    String code = params.get("code"); 
                    if (code == null) code = params.get("id");
                    
                    Method delete = repoClass.getMethod("delete", String.class);
                    delete.invoke(null, code);
                    
                    String entityName = modelClass.getSimpleName();
                    JettraSyncManager.notifyChange(entityName, SyncType.DELETE, "System");
                    return true;
                }
            } catch (Exception e) {
            }
        }
        return false;
    }
}
