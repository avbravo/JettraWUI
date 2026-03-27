package io.jettra.wui.complex;

import io.jettra.wui.components.Div;

/**
 * Divide component that provides a styled visual separator for layouts.
 */
public class Divide extends Div {
    public Divide() {
        super();
        this.setStyle("border-top", "2px solid var(--jettra-accent)")
            .setStyle("margin", "25px 0")
            .setStyle("opacity", "0.4")
            .setStyle("width", "100%")
            .setStyle("box-shadow", "0 0 10px var(--jettra-glow)");
        this.addClass("j-divide");
    }
}
