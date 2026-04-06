package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class CheckBoxGroup extends UIComponent {

    public CheckBoxGroup(String name) {
        super("div");
        setProperty("role", "group");
        setProperty("name", name);
        setStyle("display", "flex");
        setStyle("flex-direction", "column");
        setStyle("gap", "10px");
        this.initialClasses = "j-checkboxgroup";
    }

    public CheckBoxGroup addCheckBox(UIComponent checkBoxWrapperOrButton) {
        String groupName = this.getProperties().get("name");
        if (groupName != null && !groupName.isEmpty()) {
            setCheckBoxNameRecursively(checkBoxWrapperOrButton, groupName);
        }
        add(checkBoxWrapperOrButton);
        return this;
    }

    private void setCheckBoxNameRecursively(UIComponent component, String groupName) {
        if (component instanceof CheckBox) {
            component.setProperty("name", groupName);
        }
        if (component.getChildren() != null) {
            for (UIComponent child : component.getChildren()) {
                if (child != null) {
                    setCheckBoxNameRecursively(child, groupName);
                }
            }
        }
    }
}
