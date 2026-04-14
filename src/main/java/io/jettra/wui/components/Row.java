package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class Row extends UIComponent {
    public Row() {
        super("tr");
    }

    public Row(UIComponent... tds) {
        super("tr");
        for (UIComponent td : tds) {
            add(td);
        }
    }

    @Override
    public Row setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Row setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Row setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Row addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Row removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Row setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Row setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Row addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Row add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
