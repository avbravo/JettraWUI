package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;
import java.util.UUID;

/**
 * Representa un combo box de seleccion multiple interactivo (select multiple interactivo 3D).
 */
public class SelectMany extends UIComponent {
    
    private String fieldName;
    private Div dropdownContainer;
    private Div displayArea;
    private String selectId;
    private boolean isInline = false;
    
    public SelectMany(String name) {
        super("div");
        this.fieldName = name;
        this.selectId = "sm_" + UUID.randomUUID().toString().substring(0, 8);
        this.initialClasses = "j-select-many j-component";
        
        // Contenedor principal relativo
        this.setStyle("position", "relative")
            .setStyle("display", "inline-block")
            .setStyle("font-family", "inherit")
            .setStyle("min-width", "250px")
            .setStyle("width", "100%");

        // Area visual del selector
        displayArea = new Div();
        displayArea.setProperty("id", selectId + "_display");
        displayArea.addClass("j-3d-effect");
        displayArea.setStyle("padding", "10px 15px")
                   .setStyle("border-radius", "12px")
                   .setStyle("border", "1px solid rgba(0, 255, 255, 0.4)")
                   .setStyle("background", "rgba(15, 25, 40, 0.9)")
                   .setStyle("color", "#fff")
                   .setStyle("cursor", "pointer")
                   .setStyle("display", "flex")
                   .setStyle("flex-wrap", "wrap")
                   .setStyle("gap", "8px")
                   .setStyle("min-height", "50px")
                   .setStyle("max-height", "200px")
                   .setStyle("overflow-y", "auto")
                   .setStyle("align-items", "center")
                   .setStyle("box-sizing", "border-box")
                   .setStyle("box-shadow", "0 15px 35px rgba(0,0,0,0.4), inset 0 2px 2px rgba(255,255,255,0.05)")
                   .setStyle("transition", "all 0.3s")
                   .setStyle("position", "relative")
                   .setStyle("width", "100%")
                   .setStyle("padding-right", "40px") // Space for chevron
                   .setStyle("z-index", "10");
        
        displayArea.setContent("<span style='color:#666; font-size:13px; font-style:italic;'>Seleccione elementos...</span>");
        
        Span chevron = new Span("▾");
        chevron.setStyle("position", "absolute").setStyle("right", "15px").setStyle("top", "50%").setStyle("transform", "translateY(-50%)")
               .setStyle("color", "var(--jettra-accent)").setStyle("font-size", "20px").setStyle("pointer-events", "none");
        displayArea.add(chevron);

        // Contenedor del Dropdown de opciones
        dropdownContainer = new Div();
        dropdownContainer.setProperty("id", selectId + "_dropdown");
        dropdownContainer.addClass("j-3d-effect");
        dropdownContainer.setStyle("display", "none")
                         .setStyle("position", "absolute")
                         .setStyle("top", "110%")
                         .setStyle("left", "0")
                         .setStyle("width", "100%")
                         .setStyle("max-height", "300px")
                         .setStyle("overflow-y", "auto")
                         .setStyle("background", "rgba(10, 15, 25, 0.98)")
                         .setStyle("backdrop-filter", "blur(20px)")
                         .setStyle("border", "1px solid var(--jettra-accent)")
                         .setStyle("border-radius", "15px")
                         .setStyle("padding", "12px")
                         .setStyle("z-index", "2000")
                         .setStyle("box-shadow", "0 40px 100px rgba(0,0,0,0.8), 0 0 20px rgba(0,255,255,0.1)");

        // Evento toggle via JavaScript
        String toggleScript = "if(" + isInline + ") return; var d = document.getElementById('" + selectId + "_dropdown" + "');" +
                              "if(d) { " +
                              "  const isHidden = d.style.display === 'none';" +
                              "  d.style.display = isHidden ? 'block' : 'none';" +
                              "  if(isHidden) d.style.animation = 'jtSelectFadeIn 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275)';" +
                              "} event.stopPropagation();";
        displayArea.setProperty("onclick", toggleScript);

        // Ocultar al hacer clic fuera
        UIComponent globalScript = new UIComponent("script") {};
        globalScript.setContent(
            "(function() {" +
            "  if(!window.jtSelectStylesAdded) {" +
            "    const style = document.createElement('style');" +
            "    style.innerHTML = `@keyframes jtSelectFadeIn { from { opacity: 0; } to { opacity: 1; } }`;" +
            "    document.head.appendChild(style);" +
            "    window.jtSelectStylesAdded = true;" +
            "  }" +
            "  document.addEventListener('click', function(e) {" +
            "    var d = document.getElementById('" + selectId + "_dropdown');" +
            "    var disp = document.getElementById('" + selectId + "_display');" +
            "    if (d && disp && !d.contains(e.target) && !disp.contains(e.target)) d.style.display = 'none';" +
            "  });" +
            "  window.updateSelectMany_" + selectId + " = function() {" +
            "    var cbs = document.querySelectorAll('#" + selectId + "_dropdown input[type=checkbox]');" +
            "    var disp = document.getElementById('" + selectId + "_display');" +
            "    var selected = [];" +
            "    cbs.forEach(cb => { if(cb.checked) selected.push(cb.getAttribute('data-label')); });" +
            "    if(selected.length === 0) {" +
            "       disp.innerHTML = '<span style=\"color:#666; font-size:13px; font-style:italic;\">Seleccione elementos...</span><span style=\"position:absolute; right:15px; top:50%; transform:translateY(-50%); color:var(--jettra-accent); font-size:20px; pointer-events:none;\">▾</span>';" +
            "    } else {" +
            "       let html = '';" +
            "       selected.forEach(s => {" +
            "         html += '<span style=\"background:rgba(0,255,255,0.15); color:var(--jettra-accent); padding:4px 12px; border-radius:8px; font-size:11px; font-weight:700; border:1px solid var(--jettra-accent); box-shadow:0 0 10px var(--jettra-glow); display:flex; align-items:center; backdrop-filter:blur(5px);\">' + s + '</span>';" +
            "       });" +
            "       html += '<span style=\"position:absolute; right:15px; top:50%; transform:translateY(-50%); color:var(--jettra-accent); font-size:20px; pointer-events:none;\">▾</span>';" +
            "       disp.innerHTML = html;" +
            "    }" +
            "  };" +
            "})();"
        );
        
        super.add(displayArea);
        super.add(dropdownContainer);
        super.add(globalScript);
    }

    public SelectMany setInline(boolean inline) {
        this.isInline = inline;
        if (inline) {
            dropdownContainer.setStyle("display", "block")
                             .setStyle("position", "relative")
                             .setStyle("top", "0")
                             .setStyle("margin-top", "15px")
                             .setStyle("box-shadow", "none")
                             .setStyle("background", "rgba(0,0,0,0.2)");
            displayArea.setStyle("cursor", "default");
        }
        return this;
    }

    public SelectMany addOption(String value, String label) {
        Div row = new Div();
        row.setStyle("display", "flex").setStyle("align-items", "center").setStyle("gap", "12px").setStyle("padding", "10px")
           .setStyle("border-radius", "8px").setStyle("cursor", "pointer").setStyle("transition", "all 0.2s");
        
        row.setProperty("onmouseover", "this.style.background='rgba(0,255,255,0.05)'; this.style.transform='translateX(5px)'");
        row.setProperty("onmouseout", "this.style.background='transparent'; this.style.transform='translateX(0)'");

        UIComponent cb = new UIComponent("input") {};
        cb.setProperty("type", "checkbox");
        cb.setProperty("name", this.fieldName);
        cb.setProperty("value", value);
        cb.setProperty("data-label", label);
        cb.setStyle("width", "18px").setStyle("height", "18px").setStyle("cursor", "pointer").setStyle("accent-color", "var(--jettra-accent)");
        cb.setProperty("onchange", "window.updateSelectMany_" + selectId + "()");
        
        UIComponent span = new UIComponent("span") {};
        span.setContent(label);
        span.setStyle("font-size", "14px").setStyle("color", "#fff");
        
        row.add(cb).add(span);
        row.setProperty("onclick", "if(event.target.tagName !== 'INPUT') { var c = this.querySelector('input'); c.checked = !c.checked; c.dispatchEvent(new Event('change')); }");

        dropdownContainer.add(row);
        return this;
    }
}
