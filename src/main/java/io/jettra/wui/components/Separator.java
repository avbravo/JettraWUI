package io.jettra.wui.components;

/**
 * Separator component that can be used anywhere in the application to separate visual items.
 */
public class Separator extends Div {

    public Separator() {
        super();
        this.addClass("j-separator");
        this.setStyle("border-top", "1px solid var(--jettra-border)");
        this.setStyle("margin", "10px 0");
        this.setStyle("width", "100%");
    }
}
