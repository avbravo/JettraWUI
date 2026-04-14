package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class TD extends UIComponent {
    public TD() {
        super("td");
    }

    public TD(String content) {
        super("td");
        setContent(content);
    }

    public TD(UIComponent... components) {
        super("td");
        for (UIComponent component : components) {
            add(component);
        }
    }

    @Override
    public TD setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public TD setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public TD setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public TD addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public TD removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public TD setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public TD setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public TD addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public TD add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
