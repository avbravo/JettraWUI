package io.jettra.wui.complex;

import io.jettra.wui.core.UIComponent;

/**
 * TrafficLight component that represents a status using Red, Yellow, and Green lights.
 */
public class TrafficLight extends UIComponent {
    public enum Status { RED, YELLOW, GREEN }
    private Status status = Status.RED;

    public TrafficLight() {
        super("div");
        this.setStyle("display", "inline-flex")
            .setStyle("flex-direction", "column")
            .setStyle("gap", "12px")
            .setStyle("padding", "15px")
            .setStyle("background", "linear-gradient(145deg, #23272b, #1a1d21)")
            .setStyle("border", "2px solid #30363d")
            .setStyle("border-radius", "20px")
            .setStyle("width", "fit-content")
            .setStyle("box-shadow", "0 10px 30px rgba(0,0,0,0.5)");
    }

    public TrafficLight setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String render() {
        this.children.clear();
        this.add(createLight(Status.RED));
        this.add(createLight(Status.YELLOW));
        this.add(createLight(Status.GREEN));
        return super.render();
    }

    private UIComponent createLight(Status color) {
        UIComponent light = new UIComponent("div") {};
        light.setStyle("width", "40px")
             .setStyle("height", "40px")
             .setStyle("border-radius", "50%")
             .setStyle("border", "3px solid rgba(0,0,0,0.3)")
             .setStyle("transition", "all 0.4s ease");
        
        String glowColor = switch(color) {
            case RED -> "rgba(255, 68, 68, 0.8)";
            case YELLOW -> "rgba(255, 187, 51, 0.8)";
            case GREEN -> "rgba(0, 200, 81, 0.8)";
        };
        
        String activeColor = switch(color) {
            case RED -> "#ff4444";
            case YELLOW -> "#ffbb33";
            case GREEN -> "#00c851";
        };
        
        if (this.status == color) {
            light.setStyle("background-color", activeColor)
                 .setStyle("box-shadow", "0 0 20px " + glowColor + ", inset 0 0 10px rgba(255,255,255,0.4)");
        } else {
            light.setStyle("background-color", "#1a1d21")
                 .setStyle("box-shadow", "inset 0 4px 6px rgba(0,0,0,0.6)");
        }
        
        return light;
    }

    // Fluent API Overrides
    @Override
    public TrafficLight setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public TrafficLight setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public TrafficLight addClass(String className) {
        super.addClass(className);
        return this;
    }
}
