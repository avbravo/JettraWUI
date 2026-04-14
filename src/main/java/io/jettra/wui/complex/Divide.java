package io.jettra.wui.complex;

import io.jettra.wui.components.Div;

/**
 * Divide component that provides a styled visual separator for layouts.
 */
public class Divide extends Div {
    public Divide() {
        super();
        this.setStyle("border-top", "2px solid var(--jettra-accent)")
            .setStyle("margin", "25px 0")
            .setStyle("opacity", "0.4")
            .setStyle("width", "100%")
            .setStyle("box-shadow", "0 0 10px var(--jettra-glow)");
        this.addClass("j-divide");
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
