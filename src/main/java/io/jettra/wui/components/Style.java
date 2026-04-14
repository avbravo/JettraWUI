package io.jettra.wui.components;
import io.jettra.wui.core.UIComponent;

public class Style extends UIComponent {
    public Style(String cssRules) {
        super("style");
        setContent(cssRules);
    }

    @Override
    public Style setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Style setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Style setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Style addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Style removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Style setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Style setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Style addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Style add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
