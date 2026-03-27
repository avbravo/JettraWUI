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
}
