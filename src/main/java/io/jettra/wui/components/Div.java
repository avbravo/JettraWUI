package io.jettra.wui.components;
import io.jettra.wui.core.UIComponent;
public class Div extends UIComponent {
    public Div() {
        super("div");
        this.initialClasses = "j-component";
    }

    @Override
    public Div setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Div setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Div setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Div addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Div removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Div setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Div setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Div addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Div add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
