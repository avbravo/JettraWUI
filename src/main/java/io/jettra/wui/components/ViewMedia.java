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

    @Override
    public ViewMedia setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public ViewMedia setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public ViewMedia setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public ViewMedia addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public ViewMedia removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public ViewMedia setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public ViewMedia setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public ViewMedia addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public ViewMedia add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
