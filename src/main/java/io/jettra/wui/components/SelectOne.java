package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

/**
 * Representa un combo box o select element en HTML.
 */
public class SelectOne extends UIComponent {
    
    public SelectOne(String name) {
        super("select");
        setProperty("name", name);
        this.initialClasses = "j-select j-component";
        this.setStyle("padding", "8px 12px")
            .setStyle("border-radius", "4px")
            .setStyle("border", "1px solid var(--jettra-border)")
            .setStyle("background-color", "var(--jettra-bg-primary)")
            .setStyle("color", "var(--jettra-text)")
            .setStyle("font-family", "inherit")
            .setStyle("font-size", "14px")
            .setStyle("cursor", "pointer")
            .setStyle("outline", "none")
            .setStyle("transition", "all 0.3s ease");
    }

    /**
     * Agrega una opción al selector.
     * @param value el valor interno de la opción.
     * @param label la etiqueta visual mostrada al usuario.
     * @return 
     */
    public SelectOne addOption(String value, String label) {
        UIComponent option = new UIComponent("option") {};
        option.setProperty("value", value);
        option.setContent(label);
        add(option);
        return this;
    }
}
