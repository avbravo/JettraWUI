package io.jettra.wui.components;
import io.jettra.wui.core.UIComponent;

public class Link extends UIComponent {
    public Link(String href, String text) {
        super("a");
        setProperty("href", href);
        setContent(text);
        this.setStyle("color", "#0ff");
        this.setStyle("text-decoration", "none");
        this.setStyle("cursor", "pointer");
    }

    @Override
    public Link setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Link setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Link setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Link addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Link removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Link setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Link setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Link addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Link add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
