package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class RadioGroupButton extends UIComponent {

    public RadioGroupButton(String name) {
        super("div");
        setProperty("role", "radiogroup");
        setProperty("name", name);
        setStyle("display", "flex");
        setStyle("flex-direction", "column");
        setStyle("gap", "10px");
        this.initialClasses = "j-radiogroup";
    }

    public RadioGroupButton addRadio(UIComponent radioWrapperOrButton) {
        // We can just add it, and assume the developer set the name properly or we find all RadioButtons inside
        // For simplicity we just add the component and let the property handle itself, or we recursively find it?
        // Let's just add it.
        add(radioWrapperOrButton);
        return this;
    }
}
