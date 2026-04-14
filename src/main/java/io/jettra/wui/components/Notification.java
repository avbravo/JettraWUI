package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;
import java.util.UUID;

/**
 * Componente Notification para mostrar notificaciones flotantes (toast-like) en JettraWUI.
 * Se puede configurar el tipo (success, error, warning, info), la posición, duración y si se puede cerrar manualmente.
 */
public class Notification extends UIComponent {

    private final String id;
    private int durationMs = 3000;
    private boolean autoClose = true;
    
    public Notification() {
        super("div");
        this.id = "notify-" + UUID.randomUUID().toString().substring(0, 8);
        this.setProperty("id", this.id);
        this.initialClasses = "j-notification";
        
        // Estilos predeterminados (oculto e inicialmente fuera de pantalla si se anima, pero aquí lo haremos simple)
        this.setStyle("position", "fixed");
        this.setStyle("top", "20px");
        this.setStyle("right", "20px");
        this.setStyle("z-index", "9999");
        this.setStyle("padding", "15px 20px");
        this.setStyle("border-radius", "8px");
        this.setStyle("box-shadow", "0 4px 12px rgba(0,0,0,0.5)");
        this.setStyle("color", "#fff");
        this.setStyle("font-family", "sans-serif");
        this.setStyle("display", "none");
        this.setStyle("transition", "opacity 0.3s ease-in-out");
        this.setStyle("opacity", "0");
        this.setStyle("border", "1px solid rgba(255,255,255,0.2)");
        this.setStyle("backdrop-filter", "blur(10px)");
    }

    public void setType(String type) {
        switch (type) {
            case "warning" -> {
                this.setStyle("background-color", "rgba(245, 158, 11, 0.9)"); // Amber
                this.setStyle("border-color", "rgba(245, 158, 11, 1)");
            }
            case "error" -> {
                this.setStyle("background-color", "rgba(239, 68, 68, 0.9)"); // Red
                this.setStyle("border-color", "rgba(239, 68, 68, 1)");
            }
            case "info" -> {
                this.setStyle("background-color", "rgba(59, 130, 246, 0.9)"); // Blue
                this.setStyle("border-color", "rgba(59, 130, 246, 1)");
            }
            case "success" -> {
                this.setStyle("background-color", "rgba(16, 185, 129, 0.9)"); // Green
                this.setStyle("border-color", "rgba(16, 185, 129, 1)");
            }
            default -> {
                this.setStyle("background-color", "rgba(51, 65, 85, 0.9)"); // Default dark
                this.setStyle("border-color", "rgba(51, 65, 85, 1)");
            }
        }
    }
    
    public void setDurationMs(int ms) {
        this.durationMs = ms;
    }
    
    public void setAutoClose(boolean autoClose) {
        this.autoClose = autoClose;
    }

    public void showMessage(String message) {
        String script = "<script>" +
            "setTimeout(function() { " +
            "  var el = document.getElementById('" + this.id + "');" +
            "  if(el) { el.style.display = 'block'; setTimeout(function(){ el.style.opacity = '1'; }, 50); }" +
            "}, 100);\n";
            
        if (autoClose && durationMs > 0) {
            script += "setTimeout(function() { " +
            "  var el = document.getElementById('" + this.id + "');" +
            "  if(el) { el.style.opacity = '0'; setTimeout(function(){ el.style.display = 'none'; }, 300); }" +
            "}, " + (100 + durationMs) + ");";
        }
        script += "</script>";

        this.setContent(message + script);
        // By unsetting display:none here temporarily to let the script handle its visibility lifecycle
        this.setStyle("display", "block");
        this.setStyle("opacity", "0"); // Starts invisible, script fades it in
    }

    @Override
    public Notification setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Notification setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Notification setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Notification addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Notification removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Notification setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Notification setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Notification addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Notification add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
