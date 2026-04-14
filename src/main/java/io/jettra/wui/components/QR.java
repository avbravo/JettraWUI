package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class QR extends UIComponent {

    private String text = "JettraStack";
    private int width = 128;
    private int height = 128;
    private String colorDark = "#000000";
    private String colorLight = "#ffffff";
    private String qrId;

    public QR(String id) {
        super("div");
        this.qrId = id;
        setProperty("id", id);
        setStyle("display", "inline-block");
        setStyle("padding", "10px");
        setStyle("background", colorLight);
        setStyle("border-radius", "8px");
    }

    public QR setText(String text) {
        this.text = text;
        return this;
    }

    public QR setWidth(int width) {
        this.width = width;
        return this;
    }

    public QR setHeight(int height) {
        this.height = height;
        return this;
    }

    public QR setColorDark(String colorDark) {
        this.colorDark = colorDark;
        return this;
    }

    public QR setColorLight(String colorLight) {
        this.colorLight = colorLight;
        setStyle("background", colorLight);
        return this;
    }

    @Override
    public String render() {
        StringBuilder js = new StringBuilder();
        
        // Ensure library is imported securely
        js.append("<script src=\"https://cdnjs.cloudflare.com/ajax/libs/qrcodejs/1.0.0/qrcode.min.js\"></script>\n");
        js.append("<script>\n");
        js.append("document.addEventListener('DOMContentLoaded', function() {\n");
        js.append("  setTimeout(function() {\n");
        js.append("    var el = document.getElementById('").append(qrId).append("');\n");
        js.append("    if(!el) return;\n");
        js.append("    el.innerHTML = '';\n"); // Clear context if navigating back
        js.append("    new QRCode(el, {\n");
        js.append("      text: \"").append(text.replace("\"", "\\\"")).append("\",\n");
        js.append("      width: ").append(width).append(",\n");
        js.append("      height: ").append(height).append(",\n");
        js.append("      colorDark : \"").append(colorDark).append("\",\n");
        js.append("      colorLight : \"").append(colorLight).append("\",\n");
        js.append("      correctLevel : QRCode.CorrectLevel.H\n");
        js.append("    });\n");
        js.append("  }, 100);\n");
        js.append("});\n");
        js.append("</script>\n");
        
        return super.render() + js.toString();
    }

    @Override
    public QR setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public QR setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public QR setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public QR addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public QR removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public QR setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public QR setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public QR addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public QR add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
