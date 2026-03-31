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
}
