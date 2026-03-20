package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

/**
 * Representa un combo box o select element en HTML.
 */
public class SelectOne extends UIComponent {
    
    public SelectOne(String name) {
        super("select");
        setProperty("name", name);
        this.initialClasses = "j-select";
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
