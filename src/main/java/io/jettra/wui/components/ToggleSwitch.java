package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class ToggleSwitch extends UIComponent {
    private UIComponent input;
    private Div slider;

    public ToggleSwitch(String id, String name, String value) {
        super("label");
        this.addClass("j-toggle");
        this.setProperty("for", id);
        this.setStyle("margin-right", "8px");
        
        input = new UIComponent("input") {};
        input.setProperty("type", "checkbox");
        input.setProperty("id", id);
        input.setProperty("name", name);
        input.setProperty("value", value);
        input.addClass("j-toggle-input");
        this.add(input);
        
        slider = new Div();
        slider.addClass("j-toggle-slider");
        this.add(slider);
    }
    
    public ToggleSwitch setLabels(String onText, String offText) {
        slider.setContent("<span class='j-toggle-on'>" + onText + "</span><span class='j-toggle-off'>" + offText + "</span>");
        return this;
    }
    
    @Override
    public UIComponent setProperty(String key, String value) {
        if ("onchange".equals(key) || "checked".equals(key) || "disabled".equals(key)) {
            input.setProperty(key, value);
        } else {
            super.setProperty(key, value);
        }
        return this;
    }
}
