package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class BarCode extends UIComponent {

    private String getFormat() { return format; }
    
    private String text = "123456789012";
    private String format = "CODE128"; 
    private int width = 2;
    private int height = 100;
    private String lineColor = "#000000";
    private String background = "#ffffff";
    private boolean displayValue = true;
    private String bcId;

    public BarCode(String id) {
        super("svg"); // JsBarcode optimally works on SVG or Canvas (using SVG here for scalable crispness)
        this.bcId = id;
        setProperty("id", id);
    }

    public BarCode setText(String text) {
        this.text = text;
        return this;
    }

    public BarCode setFormat(String format) {
        this.format = format;
        return this;
    }

    public BarCode setWidth(int width) {
        this.width = width;
        return this;
    }

    public BarCode setHeight(int height) {
        this.height = height;
        return this;
    }

    public BarCode setLineColor(String lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    public BarCode setBackground(String background) {
        this.background = background;
        return this;
    }

    public BarCode setDisplayValue(boolean displayValue) {
        this.displayValue = displayValue;
        return this;
    }

    @Override
    public String render() {
        StringBuilder js = new StringBuilder();
        
        js.append("<script src=\"https://cdn.jsdelivr.net/npm/jsbarcode@3.11.0/dist/JsBarcode.all.min.js\"></script>\n");
        js.append("<script>\n");
        js.append("document.addEventListener('DOMContentLoaded', function() {\n");
        js.append("  setTimeout(function() {\n");
        js.append("    var el = document.getElementById('").append(bcId).append("');\n");
        js.append("    if(!el) return;\n");
        js.append("    try { \n");
        js.append("      JsBarcode(el, \"").append(text.replace("\"", "\\\"")).append("\", {\n");
        js.append("        format: \"").append(format).append("\",\n");
        js.append("        width: ").append(width).append(",\n");
        js.append("        height: ").append(height).append(",\n");
        js.append("        lineColor: \"").append(lineColor).append("\",\n");
        js.append("        background: \"").append(background).append("\",\n");
        js.append("        displayValue: ").append(displayValue).append("\n");
        js.append("      });\n");
        js.append("    } catch(e) { console.error('Error generating Barcode: ', e); }\n");
        js.append("  }, 150);\n");
        js.append("});\n");
        js.append("</script>\n");
        
        return super.render() + js.toString();
    }
}
