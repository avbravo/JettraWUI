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

        // Area visual del selector (lo que muestra las tags o el texto default)
        displayArea = new Div();
        displayArea.setProperty("id", selectId + "_display");
        displayArea.setStyle("padding", "10px 15px")
                   .setStyle("border-radius", "8px")
                   .setStyle("border", "1px solid var(--jettra-accent)")
                   .setStyle("background", "rgba(0,0,0,0.6)")
                   .setStyle("color", "var(--jettra-text)")
                   .setStyle("cursor", "pointer")
                   .setStyle("display", "flex")
                   .setStyle("flex-wrap", "wrap")
                   .setStyle("gap", "5px")
                   .setStyle("min-height", "45px")
                   .setStyle("align-items", "center")
                   .setStyle("box-shadow", "4px 4px 0px rgba(0,255,255,0.2), inset 0 0 10px rgba(0,255,255,0.1)")
                   .setStyle("transition", "all 0.3s ease")
                   .setStyle("position", "relative");
        displayArea.setContent("<span style='color:#777; font-size:14px; opacity: 0.6;'>Seleccionar opciones...</span>");
        
        // Contenedor del Dropdown de opciones
        dropdownContainer = new Div();
        dropdownContainer.setProperty("id", selectId + "_dropdown");
        dropdownContainer.setStyle("display", "none")
                         .setStyle("position", "absolute")
                         .setStyle("top", "100%")
                         .setStyle("left", "0")
                         .setStyle("width", "100%")
                         .setStyle("max-height", "300px")
                         .setStyle("overflow-y", "auto")
                         .setStyle("background", "rgba(13, 17, 23, 0.95)")
                         .setStyle("backdrop-filter", "blur(20px)")
                         .setStyle("border", "2px solid var(--jettra-accent)")
                         .setStyle("border-radius", "0 0 12px 12px")
                         .setStyle("padding", "12px")
                         .setStyle("z-index", "1000")
                         .setStyle("box-shadow", "0 20px 50px rgba(0,0,0,0.8), 8px 8px 0px rgba(0,255,255,0.1)");

        // Evento toggle via JavaScript
        String toggleScript = "var d = document.getElementById('" + dropdownContainer.getId() + "');" +
                              "d.style.display = d.style.display === 'none' ? 'block' : 'none';";
        displayArea.setProperty("onclick", toggleScript);

        // Ocultar al hacer clic fuera (Inyección de Script general)
        UIComponent globalScript = new UIComponent("script") {};
        globalScript.setContent(
            "document.addEventListener('click', function(e) {" +
            "  var display = document.getElementById('" + selectId + "_display');" +
            "  var dropdown = document.getElementById('" + selectId + "_dropdown');" +
            "  if (display && dropdown && !display.contains(e.target) && !dropdown.contains(e.target)) {" +
            "      dropdown.style.display = 'none';" +
            "  }" +
            "});" +
            "window.updateSelectMany_" + selectId + " = function() {" +
            "  var checkboxes = document.querySelectorAll('#" + selectId + "_dropdown input[type=checkbox]');" +
            "  var display = document.getElementById('" + selectId + "_display');" +
            "  var selected = [];" +
            "  checkboxes.forEach(function(cb) {" +
            "     if(cb.checked) selected.push(cb.getAttribute('data-label'));" +
            "  });" +
            "  if(selected.length === 0) {" +
            "     display.innerHTML = '<span style=\\'color:#777; font-size:14px; opacity: 0.6;\\'>Seleccionar opciones...</span>';" +
            "  } else {" +
            "     var html = '';" +
            "     selected.forEach(function(s) {" +
            "       html += '<span style=\\'background:var(--jettra-accent); color:#000; padding:4px 12px; border-radius:15px; font-size:11px; font-weight:bold; box-shadow: 2px 2px 0px rgba(0,0,0,0.3), inset 1px 1px 1px rgba(255,255,255,0.4); border: 1px solid rgba(0,0,0,0.2); margin-right: 4px;\\'>' + s + '</span>';" +
            "     });" +
            "     display.innerHTML = html;" +
            "  }" +
            "};"
        );
        
        super.add(displayArea);
        super.add(dropdownContainer);
        super.add(globalScript);
    }

    /**
     * Agrega una opción al selector múltiple.
     * @param value el valor interno de la opción (se mandará en el form).
     * @param label la etiqueta visual mostrada al usuario.
     * @return this component
     */
    public SelectMany addOption(String value, String label) {
        Div row = new Div();
        row.setStyle("display", "flex")
           .setStyle("align-items", "center")
           .setStyle("gap", "10px")
           .setStyle("padding", "5px 0")
           .setStyle("cursor", "pointer");

        UIComponent cb = new UIComponent("input") {};
        cb.setProperty("type", "checkbox");
        cb.setProperty("name", this.fieldName);
        cb.setProperty("value", value);
        cb.setProperty("data-label", label);
        cb.setStyle("width", "18px")
          .setStyle("height", "18px")
          .setStyle("cursor", "pointer")
          .setStyle("accent-color", "var(--jettra-accent)");
        cb.setProperty("onchange", "window.updateSelectMany_" + selectId + "()");
        
        UIComponent span = new UIComponent("span") {};
        span.setContent(label);
        span.setStyle("font-size", "14px").setStyle("color", "#eee");
        
        row.add(cb).add(span);
        // Hacer que al cliquear el ROW marque el checkbox
        row.setProperty("onclick", "var cb = this.querySelector('input'); if(event.target !== cb) { cb.checked = !cb.checked; cb.dispatchEvent(new Event('change')); }");

        dropdownContainer.add(row);
        return this;
    }
}
