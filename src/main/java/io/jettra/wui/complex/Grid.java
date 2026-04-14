package io.jettra.wui.complex;
import io.jettra.wui.components.Div;

public class Grid extends Div {
    public Grid(int columns, String gap) {
        super();
        this.setStyle("display", "grid");
        this.setStyle("grid-template-columns", "repeat(" + columns + ", 1fr)");
        this.setStyle("gap", gap);
        this.initialClasses = "j-grid";
    }

    @Override
    public Grid setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Grid setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Grid setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Grid addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Grid removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Grid setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Grid setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Grid addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Grid add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
