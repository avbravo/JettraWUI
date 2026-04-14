package io.jettra.wui.components;
import io.jettra.wui.core.UIComponent;
public class Form extends UIComponent {
    public Form(String id, String action) {
        super("form");
        setProperty("id", id);
        setProperty("action", action);
        setProperty("method", "POST");
        this.initialClasses = "j-component";
    }

    @Override
    public Form setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Form setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Form setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Form addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Form removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Form setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Form setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Form addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Form add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
