package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

/**
 * Representa un combo box o select element en HTML.
 */
public class SelectOne extends UIComponent {
    
    private String defaultValue;
    
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

    public SelectOne setDefault(String value) {
        this.defaultValue = value;
        return this;
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

    @Override
    public String render() {
        if (!getChildren().isEmpty()) {
            boolean found = false;
            if (defaultValue != null) {
                for (UIComponent child : getChildren()) {
                    if (child.getTag().equals("option")) {
                        String val = child.getProperties().get("value");
                        if (defaultValue.equals(val)) {
                            child.setProperty("selected", "selected");
                            found = true;
                        } else {
                            child.getProperties().remove("selected");
                        }
                    }
                }
            }
            
            if (!found) {
                // Select first option by default
                for (UIComponent child : getChildren()) {
                    if (child.getTag().equals("option")) {
                        child.setProperty("selected", "selected");
                        break;
                    }
                }
            }
        }
        return super.render();
    }
}
