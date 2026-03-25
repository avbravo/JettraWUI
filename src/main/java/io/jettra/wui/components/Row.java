package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class Row extends UIComponent {
    public Row() {
        super("tr");
    }

    public Row(UIComponent... tds) {
        super("tr");
        for (UIComponent td : tds) {
            add(td);
        }
    }
}
