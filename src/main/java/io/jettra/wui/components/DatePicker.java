package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class DatePicker extends UIComponent {

    public DatePicker(String id, String label) {
        super("div");
        setProperty("id", id + "-container");
        setStyle("display", "flex");
        setStyle("flex-direction", "column");
        setStyle("gap", "5px");

        if (label != null && !label.isEmpty()) {
            UIComponent lbl = new UIComponent("label") {};
            lbl.setContent(label);
            lbl.setProperty("for", id);
            lbl.setStyle("font-weight", "500").setStyle("color", "var(--jettra-text)").setStyle("font-size", "0.9rem");
            add(lbl);
        }

        UIComponent input = new UIComponent("input") {};
        input.setProperty("type", "date");
        input.setProperty("id", id);
        input.setProperty("name", id);
        input.addClass("j-input");
        add(input);
    }
    
    public DatePicker setMin(String minDate) {
        getChildren().get(getChildren().size() - 1).setProperty("min", minDate);
        return this;
    }
    
    public DatePicker setMax(String maxDate) {
        getChildren().get(getChildren().size() - 1).setProperty("max", maxDate);
        return this;
    }
    
    public DatePicker setValue(String date) {
        getChildren().get(getChildren().size() - 1).setProperty("value", date);
        return this;
    }

    public DatePicker setEditable(boolean editable) {
        if (!editable) {
            getChildren().get(getChildren().size() - 1).setProperty("readonly", "readonly");
            getChildren().get(getChildren().size() - 1).setStyle("background-color", "rgba(255,255,255,0.05)");
        } else {
            getChildren().get(getChildren().size() - 1).getProperties().remove("readonly");
            getChildren().get(getChildren().size() - 1).getStyles().remove("background-color");
        }
        return this;
    }

    public DatePicker setType(String type) {
        getChildren().get(getChildren().size() - 1).setProperty("type", type);
        return this;
    }

    public DatePicker setFormat(String format) {
        getChildren().get(getChildren().size() - 1).setProperty("data-format", format);
        return this;
    }
    
    public DatePicker setOnChange(String jsFunction) {
        getChildren().get(getChildren().size() - 1).setProperty("onchange", jsFunction);
        return this;
    }
}
