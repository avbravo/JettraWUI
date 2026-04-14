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

    @Override
    public RadioGroupButton setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public RadioGroupButton setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public RadioGroupButton setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public RadioGroupButton addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public RadioGroupButton removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public RadioGroupButton setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public RadioGroupButton setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public RadioGroupButton addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public RadioGroupButton add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
