package io.jettra.wui.validations;

import io.jettra.wui.core.UIComponent;
import java.lang.reflect.Field;

public class JettraValidations {

    public static void apply(UIComponent component, Class<?> modelClass, String fieldName) {
        try {
            Field field = modelClass.getDeclaredField(fieldName);
            
            if (field.isAnnotationPresent(NotNull.class) || 
                field.isAnnotationPresent(NotEmpty.class) || 
                field.isAnnotationPresent(NotBlank.class)) {
                component.setProperty("required", "true");
            }
            
            if (field.isAnnotationPresent(Email.class)) {
                component.setProperty("type", "email");
            }
            
            if (field.isAnnotationPresent(Min.class)) {
                component.setProperty("min", String.valueOf(field.getAnnotation(Min.class).value()));
            }
            
            if (field.isAnnotationPresent(Max.class)) {
                component.setProperty("max", String.valueOf(field.getAnnotation(Max.class).value()));
            }
            
            if (field.isAnnotationPresent(DecimalMin.class)) {
                component.setProperty("min", field.getAnnotation(DecimalMin.class).value());
            }
            
            if (field.isAnnotationPresent(DecimalMax.class)) {
                component.setProperty("max", field.getAnnotation(DecimalMax.class).value());
            }

            if (field.isAnnotationPresent(Pattern.class)) {
                component.setProperty("pattern", field.getAnnotation(Pattern.class).regexp());
            }
            
            if (field.isAnnotationPresent(Size.class)) {
                Size size = field.getAnnotation(Size.class);
                if (size.min() > 0) {
                    component.setProperty("minlength", String.valueOf(size.min()));
                }
                if (size.max() < Integer.MAX_VALUE) {
                    component.setProperty("maxlength", String.valueOf(size.max()));
                }
            }
            
        } catch (NoSuchFieldException e) {
            System.err.println("[JettraValidations] Field not found: " + fieldName + " in " + modelClass.getName());
        }
    }
}
