package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class PDFViewer extends UIComponent {
    public PDFViewer(String url) {
        super("iframe");
        setProperty("src", url);
        setStyle("width", "100%").setStyle("height", "600px").setStyle("border", "none");
    }

    public PDFViewer setUrl(String url) {
        setProperty("src", url);
        return this;
    }

    @Override
    public PDFViewer setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public PDFViewer setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public PDFViewer setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public PDFViewer addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public PDFViewer removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public PDFViewer setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public PDFViewer setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public PDFViewer addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public PDFViewer add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
