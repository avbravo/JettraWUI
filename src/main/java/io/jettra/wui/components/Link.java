package io.jettra.wui.components;
import io.jettra.wui.core.UIComponent;

public class Link extends UIComponent {
    public Link(String href, String text) {
        super("a");
        setProperty("href", href);
        setContent(text);
        this.setStyle("color", "#0ff");
        this.setStyle("text-decoration", "none");
        this.setStyle("cursor", "pointer");
    }
}
