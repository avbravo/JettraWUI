package io.jettra.wui.components;
import io.jettra.wui.core.UIComponent;

public class Script extends UIComponent {
    public Script(String jsCode) {
        super("script");
        setContent(jsCode);
    }

    @Override
    public Script setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Script setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Script setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Script addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Script removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Script setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Script setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Script addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Script add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
