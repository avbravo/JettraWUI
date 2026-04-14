package io.jettra.wui.components;

/**
 * Separator component that can be used anywhere in the application to separate visual items.
 */
public class Separator extends Div {

    public Separator() {
        super();
        this.addClass("j-separator");
        this.setStyle("border-top", "1px solid var(--jettra-border)");
        this.setStyle("margin", "10px 0");
        this.setStyle("width", "100%");
    }

    @Override
    public Separator setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Separator setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Separator setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Separator addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Separator removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Separator setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Separator setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Separator addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Separator add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
