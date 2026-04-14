package io.jettra.wui.components;

/**
 * Divide component that provides a 3D stylistic separator line.
 */
public class Divide extends Div {

    public Divide() {
        super();
        this.addClass("j-divide");
        this.setStyle("border-top", "2px solid var(--jettra-accent)");
        this.setStyle("margin", "15px 0");
        this.setStyle("opacity", "0.5");
        this.setStyle("width", "100%");
        this.setStyle("box-shadow", "0 0 5px var(--jettra-glow)");
    }

    @Override
    public Divide setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Divide setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Divide setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Divide addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Divide removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Divide setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Divide setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Divide addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Divide add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
