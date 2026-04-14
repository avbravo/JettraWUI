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
        super("div"); // Wrapper div
        this.bcId = id;
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
        
        js.append("<svg id=\"").append(bcId).append("\"></svg>\n");
        js.append("<script>\n");
        js.append("(function() {\n");
        js.append("  function renderBc() {\n");
        js.append("    var checkDom = setInterval(function() {\n");
        js.append("      var el = document.getElementById('").append(bcId).append("');\n");
        js.append("      if(el) {\n");
        js.append("        clearInterval(checkDom);\n");
        js.append("        try { \n");
        js.append("      JsBarcode(el, \"").append(text.replace("\"", "\\\"")).append("\", {\n");
        js.append("        format: \"").append(format).append("\",\n");
        js.append("        width: ").append(width).append(",\n");
        js.append("        height: ").append(height).append(",\n");
        js.append("        lineColor: \"").append(lineColor).append("\",\n");
        js.append("        background: \"").append(background).append("\",\n");
        js.append("        displayValue: ").append(displayValue).append("\n");
        js.append("      });\n");
        js.append("        } catch(e) { console.error('Error generating Barcode: ', e); }\n");
        js.append("      }\n");
        js.append("    }, 100);\n");
        js.append("  }\n");
        js.append("  if (typeof JsBarcode === 'undefined') {\n");
        js.append("    if (!document.getElementById('jsbarcode-lib')) {\n");
        js.append("      var s = document.createElement('script');\n");
        js.append("      s.id = 'jsbarcode-lib';\n");
        js.append("      s.innerText = ");
        js.append("\"").append(BarCodeExtension.JS_CODE.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "")).append("\";\n");
        js.append("      document.head.appendChild(s);\n");
        js.append("    }\n");
        js.append("    var checkInterval = setInterval(function() {\n");
        js.append("      if (typeof JsBarcode !== 'undefined') {\n");
        js.append("        clearInterval(checkInterval);\n");
        js.append("        renderBc();\n");
        js.append("      }\n");
        js.append("    }, 50);\n");
        js.append("  } else { setTimeout(renderBc, 100); }\n");
        js.append("})();\n");
        js.append("</script>\n");
        
        setContent(js.toString());
        return super.render();
    }

    @Override
    public BarCode setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public BarCode setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public BarCode setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public BarCode addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public BarCode removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public BarCode setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public BarCode setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public BarCode addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public BarCode add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
