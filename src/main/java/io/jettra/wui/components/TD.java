package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class TD extends UIComponent {
    public TD() {
        super("td");
    }

    public TD(String content) {
        super("td");
        setContent(content);
    }

    public TD(UIComponent... components) {
        super("td");
        for (UIComponent component : components) {
            add(component);
        }
    }
}
