package io.jettra.wui.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to simplify the creation of CRUD interfaces.
 * When applied to a Page, the framework will automatically generate the CRUD UI.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CrudView {
    /**
     * The ViewModel class name or alias.
     */
    String model();

    /**
     * The Repository class name or alias.
     */
    String repository() default "";
}
