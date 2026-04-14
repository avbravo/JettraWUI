package io.jettra.wui.complex;
import io.jettra.wui.core.UIComponent;
public class Left extends UIComponent {
    public Left() {
        super("div");
        this.initialClasses = "j-left";
    }

    @Override
    public Left setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Left setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Left setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Left addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Left removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Left setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Left setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Left addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Left add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
