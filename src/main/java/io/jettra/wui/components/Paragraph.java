package io.jettra.wui.components;
import io.jettra.wui.core.UIComponent;

public class Paragraph extends UIComponent {
    public Paragraph(String text) {
        super("p");
        setContent(text);
        this.setStyle("margin-bottom", "10px");
        this.setStyle("font-family", "sans-serif");
    }
}
