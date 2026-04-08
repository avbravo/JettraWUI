package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class Card extends UIComponent {

    public Card() {
        super("div");
        addClass("j-card");
        addClass("j-component");
        setStyle("border", "1px solid var(--jettra-border)");
        setStyle("border-radius", "8px");
        setStyle("padding", "15px");
        setStyle("background", "rgba(255,255,255,0.05)");
        setStyle("box-shadow", "0 4px 6px rgba(0,0,0,0.3)");
        setStyle("display", "flex");
        setStyle("flex-direction", "column");
        setStyle("gap", "10px");
        setStyle("transition", "transform 0.2s, box-shadow 0.2s");
    }
}
