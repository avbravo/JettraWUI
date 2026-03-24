package io.jettra.wui.complex;

import io.jettra.wui.core.UIComponent;

/**
 * Component for displaying user avatars with support for images, labels, and icons.
 */
public class Avatar extends UIComponent {
    public enum Shape { CIRCLE, ROUNDED }
    public enum Size { XS, SM, MD, LG, XL }

    public Avatar() {
        super("div");
        addClass("j-avatar");
    }

    /**
     * Creates an avatar with an image.
     */
    public static Avatar image(String src, String alt) {
        Avatar avatar = new Avatar();
        avatar.addClass("j-avatar-image");
        UIComponent img = new UIComponent("img") {};
        img.setProperty("src", src);
        if (alt != null) img.setProperty("alt", alt);
        avatar.add(img);
        return avatar;
    }

    /**
     * Creates an avatar with a label (e.g., initials).
     */
    public static Avatar label(String text, String bgColor) {
        Avatar avatar = new Avatar();
        avatar.addClass("j-avatar-label");
        avatar.setContent(text);
        if (bgColor != null) avatar.setStyle("background-color", bgColor);
        return avatar;
    }

    /**
     * Creates an avatar with an icon (SVG or character).
     */
    public static Avatar icon(String iconContent) {
        Avatar avatar = new Avatar();
        avatar.addClass("j-avatar-icon");
        avatar.setContent(iconContent);
        return avatar;
    }

    public Avatar setShape(Shape shape) {
        if (shape == Shape.CIRCLE) {
            addClass("j-avatar-circle");
        } else {
            addClass("j-avatar-rounded");
        }
        return this;
    }

    public Avatar setSize(Size size) {
        addClass("j-avatar-" + size.name().toLowerCase());
        return this;
    }

    public Avatar setBadge(String color) {
        UIComponent badge = new UIComponent("span") {};
        badge.addClass("j-avatar-badge");
        if (color != null) badge.setStyle("background-color", color);
        add(badge);
        return this;
    }
}
