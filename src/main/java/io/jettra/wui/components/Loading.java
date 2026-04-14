package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

/**
 * A Loading indicator component that displays an animated SVG spinner.
 */
public class Loading extends UIComponent {

    public enum Size {
        SM, MD, LG, XL
    }

    private Size size = Size.MD;
    private String color = "var(--jettra-accent)";

    public Loading() {
        super("div");
        addClass("j-loading");
        setStyle("display", "inline-flex");
        setStyle("align-items", "center");
        setStyle("justify-content", "center");
        updateSvg();
    }

    /**
     * Sets the size of the loading spinner.
     */
    public Loading setSize(Size size) {
        this.size = size;
        updateSvg();
        return this;
    }

    /**
     * Sets the color of the loading spinner.
     */
    public Loading setColor(String color) {
        this.color = color;
        updateSvg();
        return this;
    }

    private void updateSvg() {
        int px = 24;
        switch (size) {
            case SM: px = 16; break;
            case MD: px = 24; break;
            case LG: px = 32; break;
            case XL: px = 48; break;
        }

        String svg = "<svg width=\"" + px + "\" height=\"" + px + "\" viewBox=\"0 0 24 24\" xmlns=\"http://www.w3.org/2000/svg\">"
                + "<style>.spinner_anim{transform-origin:center;animation:spinner_spin .75s infinite linear}@keyframes spinner_spin{100%{transform:rotate(360deg)}}</style>"
                + "<path d=\"M12 2v4M12 18v4M4.93 4.93l2.83 2.83M16.24 16.24l2.83 2.83M2 12h4M18 12h4M4.93 19.07l2.83-2.83M16.24 7.76l2.83-2.83\" "
                + "class=\"spinner_anim\" fill=\"none\" stroke=\"" + this.color + "\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\" />"
                + "</svg>";

        setContent(svg);
    }

    @Override
    public Loading setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Loading setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Loading setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Loading addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Loading removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Loading setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Loading setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Loading addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Loading add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
