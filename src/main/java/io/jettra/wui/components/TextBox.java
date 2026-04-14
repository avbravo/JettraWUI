package io.jettra.wui.components;
import io.jettra.wui.core.UIComponent;
public class TextBox extends UIComponent {
    public TextBox(String type, String name) {
        super("input");
        setProperty("type", type);
        setProperty("name", name);
        this.initialClasses = "j-input";
    }
    public TextBox setPlaceholder(String placeholder) {
        setProperty("placeholder", placeholder);
        return this;
    }
    public TextBox setValue(String value) {
        setProperty("value", value);
        return this;
    }

    @Override
    public TextBox setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public TextBox setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public TextBox setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public TextBox setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public TextBox addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public TextBox removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public TextBox setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public TextBox addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public TextBox add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
