package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

/**
 * Representa un combo box de seleccion multiple HTML (select multiple).
 */
public class SelectMany extends UIComponent {
    
    public SelectMany(String name) {
        super("select");
        setProperty("name", name);
        setProperty("multiple", "true");
        this.initialClasses = "j-select-many j-component";
        this.setStyle("padding", "8px 12px")
            .setStyle("border-radius", "4px")
            .setStyle("border", "1px solid var(--jettra-border)")
            .setStyle("background-color", "var(--jettra-bg-primary)")
            .setStyle("color", "var(--jettra-text)")
            .setStyle("font-family", "inherit")
            .setStyle("font-size", "14px")
            .setStyle("outline", "none")
            .setStyle("transition", "all 0.3s ease")
            .setStyle("min-height", "80px"); // Multiple select needs more height
    }

    /**
     * Agrega una opción al selector múltiple.
     * @param value el valor interno de la opción.
     * @param label la etiqueta visual mostrada al usuario.
     * @return this component
     */
    public SelectMany addOption(String value, String label) {
        UIComponent option = new UIComponent("option") {};
        option.setProperty("value", value);
        option.setContent(label);
        add(option);
        return this;
    }
}
