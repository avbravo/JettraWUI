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
}
