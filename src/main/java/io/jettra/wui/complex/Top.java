package io.jettra.wui.complex;
import io.jettra.wui.core.UIComponent;
public class Top extends UIComponent {
    public Top() {
        super("div");
        this.initialClasses = "j-top";
    }

    @Override
    public Top setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Top setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Top setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Top addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Top removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Top setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Top setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Top addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Top add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
