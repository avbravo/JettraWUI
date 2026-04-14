package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

/**
 * Componente Alert para mostrar mensajes de advertencia, error o información en JettraWUI.
 */
public class Alert extends UIComponent {

    public Alert() {
        super("div");
        this.initialClasses = "j-alert";
        // Estilos predeterminados (oculto inicialmente)
        this.setStyle("display", "none");
        this.setStyle("padding", "15px");
        this.setStyle("margin-bottom", "20px");
        this.setStyle("border", "1px solid transparent");
        this.setStyle("border-radius", "4px");
        this.setStyle("text-align", "center");
        this.setStyle("font-weight", "bold");
    }

    public void setType(String type) {
        if ("warning".equals(type)) {
            this.setStyle("color", "#8a6d3b");
            this.setStyle("background-color", "#fcf8e3");
            this.setStyle("border-color", "#faebcc");
        } else if ("error".equals(type)) {
            this.setStyle("color", "#a94442");
            this.setStyle("background-color", "#f2dede");
            this.setStyle("border-color", "#ebccd1");
        } else if ("info".equals(type)) {
            this.setStyle("color", "#31708f");
            this.setStyle("background-color", "#d9edf7");
            this.setStyle("border-color", "#bce8f1");
        } else if ("success".equals(type)) {
            this.setStyle("color", "#3c763d");
            this.setStyle("background-color", "#dff0d8");
            this.setStyle("border-color", "#d6e9c6");
        }
    }

    public void showMessage(String message) {
        this.setContent(message);
        this.setStyle("display", "block");
    }

    public void hide() {
        this.setStyle("display", "none");
        this.setContent("");
    }

    @Override
    public Alert setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Alert setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Alert setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Alert addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Alert removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Alert setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Alert setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Alert addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Alert add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
