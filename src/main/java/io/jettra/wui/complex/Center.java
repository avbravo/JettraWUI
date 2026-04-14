package io.jettra.wui.complex;
import io.jettra.wui.core.UIComponent;
public class Center extends UIComponent {
    public Center() {
        super("div");
        this.initialClasses = "j-center";
    }

    @Override
    public Center setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Center setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Center setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Center addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Center removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Center setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Center setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Center addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Center add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
