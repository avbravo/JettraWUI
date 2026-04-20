package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

/**
 * Representa un combo box o select element en HTML.
 */
public class SelectOne extends UIComponent {
    
    private String defaultValue;
    private boolean allowAddItem = false;
    
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

    public SelectOne setAllowAddItem(boolean allowAddItem) {
        this.allowAddItem = allowAddItem;
        return this;
    }

    public boolean isAllowAddItem() {
        return allowAddItem;
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
        if (allowAddItem) {
            boolean hasAddOption = false;
            for (UIComponent child : getChildren()) {
                if ("__add_item__".equals(child.getProperties().get("value"))) {
                    hasAddOption = true;
                    break;
                }
            }
            if (!hasAddOption) {
                this.addOption("__add_item__", "Add item...");
            }
            
            String checkScript = "let el = this; let isAdd = el.multiple ? Array.from(el.options).some(o => o.selected && o.value === '__add_item__') : el.value === '__add_item__'; if(isAdd){ let val = prompt('Insert new item:'); if(val && val.trim() !== ''){ let opt = document.createElement('option'); opt.value = val; opt.text = val; opt.selected = true; el.add(opt, el.options[el.options.length - 1]); if(!el.multiple) el.value = val; } if(el.multiple) Array.from(el.options).forEach(o => {if(o.value === '__add_item__') o.selected = false;}); else if(!val) el.selectedIndex = 0; } ";
            String currentOnChange = getProperties().get("onchange");
            if (currentOnChange == null) {
                setProperty("onchange", checkScript);
            } else if (!currentOnChange.contains("__add_item__")) {
                setProperty("onchange", checkScript + currentOnChange);
            }
        }

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

    @Override
    public SelectOne setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public SelectOne setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public SelectOne setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public SelectOne addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public SelectOne removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public SelectOne setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public SelectOne setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public SelectOne addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public SelectOne add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
