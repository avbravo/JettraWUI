package io.jettra.wui.components;
import io.jettra.wui.core.UIComponent;
public class Span extends UIComponent {
    public Span(String text) {
        super("span");
        setContent(text);
    }

    @Override
    public Span setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Span setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Span setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Span addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Span removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Span setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Span setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Span addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Span add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
