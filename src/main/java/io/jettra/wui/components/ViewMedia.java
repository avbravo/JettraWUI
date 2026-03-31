package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class ViewMedia extends UIComponent {
    public ViewMedia(String url, boolean isVideo) {
        super(isVideo ? "video" : "audio");
        setProperty("src", url);
        setProperty("controls", "controls");
        if (isVideo) {
            setStyle("max-width", "100%");
        }
    }

    public ViewMedia setUrl(String url) {
        setProperty("src", url);
        return this;
    }

    public ViewMedia setAutoplay(boolean autoplay) {
        if (autoplay) {
            setProperty("autoplay", "autoplay");
            setProperty("muted", "muted"); // Usually needed for autoplay in browsers
        } else {
            properties.remove("autoplay");
            properties.remove("muted");
        }
        return this;
    }
}
