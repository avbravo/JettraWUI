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
        String groupName = this.getProperties().get("name");
        if (groupName != null && !groupName.isEmpty()) {
            setRadioNameRecursively(radioWrapperOrButton, groupName);
        }
        add(radioWrapperOrButton);
        return this;
    }

    private void setRadioNameRecursively(UIComponent component, String groupName) {
        if (component instanceof RadioButton) {
            component.setProperty("name", groupName);
        }
        if (component.getChildren() != null) {
            for (UIComponent child : component.getChildren()) {
                if (child != null) {
                    setRadioNameRecursively(child, groupName);
                }
            }
        }
    }
}
