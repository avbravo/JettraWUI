package io.jettra.wui.mvc;

import io.jettra.wui.core.Page;
import io.jettra.wui.core.UIComponent;
import io.jettra.wui.core.annotations.Inject;
import io.jettra.wui.core.annotations.InjectViewModel;
import io.jettra.wui.core.annotations.JettraViewModel;
import io.jettra.wui.core.annotations.InjectProperties;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
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
}
