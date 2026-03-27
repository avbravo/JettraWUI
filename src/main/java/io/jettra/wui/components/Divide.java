package io.jettra.wui.components;

/**
 * Divide component that provides a 3D stylistic separator line.
 */
public class Divide extends Div {

    public Divide() {
        super();
        this.addClass("j-divide");
        this.setStyle("border-top", "2px solid var(--jettra-accent)");
        this.setStyle("margin", "15px 0");
        this.setStyle("opacity", "0.5");
        this.setStyle("width", "100%");
        this.setStyle("box-shadow", "0 0 5px var(--jettra-glow)");
    }
}
