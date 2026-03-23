package io.jettra.wui.components;
import io.jettra.wui.core.UIComponent;

public class Style extends UIComponent {
    public Style(String cssRules) {
        super("style");
        setContent(cssRules);
    }
}
