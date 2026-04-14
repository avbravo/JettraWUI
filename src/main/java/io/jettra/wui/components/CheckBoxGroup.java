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

    @Override
    public CheckBoxGroup setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public CheckBoxGroup setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public CheckBoxGroup setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public CheckBoxGroup addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public CheckBoxGroup removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public CheckBoxGroup setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public CheckBoxGroup setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public CheckBoxGroup addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public CheckBoxGroup add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
