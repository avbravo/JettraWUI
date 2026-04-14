package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class Catcha extends UIComponent {

    private int amountOfImagesToValidate = 3;
    private String catchaId;
    private String onValidateJs = "window.show3DMessage('Captcha Passed', 'You selected the required amount of images.');";

    public Catcha(String id) {
        super("div");
        this.catchaId = id;
        setProperty("id", id);
        setStyle("border", "1px solid var(--jettra-border)");
        setStyle("border-radius", "8px");
        setStyle("background", "var(--jettra-glass)");
        setStyle("padding", "15px");
        setStyle("max-width", "350px");
        setStyle("display", "inline-block");
    }

    public Catcha setAmountOfImagesToValidate(int amount) {
        this.amountOfImagesToValidate = amount;
        return this;
    }

    public Catcha setOnValidate(String jsCallback) {
        this.onValidateJs = jsCallback;
        return this;
    }

    @Override
    public String render() {
        StringBuilder html = new StringBuilder();
        
        html.append("<div style='margin-bottom: 15px;'>");
        html.append("<p style='margin:0; font-weight:bold; color:var(--jettra-accent);'>Human Verification</p>");
        html.append("<small style='opacity:0.7'>Select at least ").append(amountOfImagesToValidate).append(" highlighted shapes to verify.</small>");
        html.append("</div>");
        
        html.append("<div style='display:grid; grid-template-columns: repeat(3, 1fr); gap: 10px;' id='").append(catchaId).append("_grid'>");
        // Create 9 mock tiles
        for (int i = 0; i < 9; i++) {
            String randomColor = i % 2 == 0 ? "rgba(100, 100, 255, 0.2)" : "rgba(100, 255, 100, 0.2)";
            html.append("<div style='background:").append(randomColor).append(";")
                .append("height:80px; border-radius:4px; cursor:pointer; display:flex; justify-content:center; align-items:center; transition: all 0.2s;' ")
                .append("onclick=\"this._selected = !this._selected; this.style.outline=this._selected?'3px solid var(--jettra-accent)':'none'; evalCaptcha_").append(catchaId).append("();\"")
                .append(">")
                .append("</div>");
        }
        html.append("</div>");
        setContent(html.toString());

        StringBuilder js = new StringBuilder();
        js.append("<script>\n");
        js.append("window.evalCaptcha_").append(catchaId).append(" = function() {\n");
        js.append("  var grid = document.getElementById('").append(catchaId).append("_grid');\n");
        js.append("  if(!grid) return;\n");
        js.append("  var squares = grid.children;\n");
        js.append("  var selectedCount = 0;\n");
        js.append("  for(var i=0; i<squares.length; i++) {\n");
        js.append("     if(squares[i]._selected) selectedCount++;\n");
        js.append("  }\n");
        js.append("  if(selectedCount >= ").append(amountOfImagesToValidate).append(") {\n");
        js.append("      // Reset immediately to avoid firing multiple times unintentionally\n");
        js.append("      for(var i=0; i<squares.length; i++) { squares[i]._selected = false; squares[i].style.outline = 'none'; }\n");
        js.append("      ").append(onValidateJs).append("\n");
        js.append("  }\n");
        js.append("};\n");
        js.append("</script>\n");
        
        return super.render() + js.toString();
    }

    @Override
    public Catcha setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Catcha setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Catcha setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Catcha addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Catcha removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Catcha setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Catcha setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Catcha addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Catcha add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
