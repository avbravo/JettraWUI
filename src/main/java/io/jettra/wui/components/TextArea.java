package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

/**
 * TextArea component for multi-line text input.
 */
public class TextArea extends UIComponent {

    public TextArea(String id, String value) {
        super("textarea");
        this.setId(id);
        this.setContent(value);
        this.addClass("j-input");
        this.setStyle("width", "100%").setStyle("box-sizing", "border-box");
    }

    public TextArea setValue(String value) {
        this.setContent(value);
        return this;
    }

    @Override
    public TextArea setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public TextArea setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public TextArea setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public TextArea addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public TextArea removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public TextArea setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public TextArea setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public TextArea addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public TextArea add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
