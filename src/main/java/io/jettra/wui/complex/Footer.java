package io.jettra.wui.complex;
import io.jettra.wui.core.UIComponent;
public class Footer extends UIComponent {
    public Footer() {
        super("div");
        this.initialClasses = "j-footer";
    }

    @Override
    public Footer setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Footer setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Footer setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Footer addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Footer removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Footer setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Footer setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Footer addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Footer add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
