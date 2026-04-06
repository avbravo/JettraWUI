package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class Time extends UIComponent {

    public Time(String id, String label) {
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
        input.setProperty("type", "time");
        input.setProperty("id", id);
        input.setProperty("name", id);
        input.addClass("j-input");
        add(input);
    }
    
    public Time setMin(String minTime) {
        getChildren().get(getChildren().size() - 1).setProperty("min", minTime);
        return this;
    }
    
    public Time setMax(String maxTime) {
        getChildren().get(getChildren().size() - 1).setProperty("max", maxTime);
        return this;
    }
    
    public Time setValue(String time) {
        getChildren().get(getChildren().size() - 1).setProperty("value", time);
        return this;
    }
    
    public Time setOnChange(String jsFunction) {
        getChildren().get(getChildren().size() - 1).setProperty("onchange", jsFunction);
        return this;
    }
}
