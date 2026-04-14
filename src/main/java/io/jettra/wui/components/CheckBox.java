package io.jettra.wui.components;
import io.jettra.wui.core.UIComponent;

public class CheckBox extends UIComponent {
    public CheckBox(String id, String name, String value) {
        super("input");
        setProperty("type", "checkbox");
        setProperty("id", id);
        setProperty("name", name);
        setProperty("value", value);
        this.setStyle("margin-right", "8px");
        this.setStyle("accent-color", "var(--jettra-accent)");
    }

    @Override
    public CheckBox setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }


    @Override
    public CheckBox setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public CheckBox setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public CheckBox setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public CheckBox addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public CheckBox removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public CheckBox setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public CheckBox addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public CheckBox add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
