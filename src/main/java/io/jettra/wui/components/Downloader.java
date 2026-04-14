package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class Downloader extends UIComponent {
    public Downloader(String text, String url) {
        super("a");
        setContent(text);
        setProperty("href", url);
        setProperty("download", "");
        addClass("j-btn").addClass("j-btn-primary");
    }

    public Downloader setUrl(String url) {
        setProperty("href", url);
        return this;
    }

    public Downloader setFilename(String filename) {
        setProperty("download", filename);
        return this;
    }

    @Override
    public Downloader setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Downloader setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Downloader setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Downloader addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Downloader removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Downloader setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Downloader setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Downloader addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Downloader add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
