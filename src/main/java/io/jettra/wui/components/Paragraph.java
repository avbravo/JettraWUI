package io.jettra.wui.components;
import io.jettra.wui.core.UIComponent;

public class Paragraph extends UIComponent {
    public Paragraph(String text) {
        super("p");
        setContent(text);
        this.setStyle("margin-bottom", "10px");
        this.setStyle("font-family", "sans-serif");
    }

    @Override
    public Paragraph setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Paragraph setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Paragraph setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Paragraph addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Paragraph removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Paragraph setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Paragraph setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Paragraph addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Paragraph add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
