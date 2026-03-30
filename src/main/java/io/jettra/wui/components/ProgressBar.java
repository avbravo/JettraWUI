package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

/**
 * ProgressBar component for displaying process completion status.
 */
public class ProgressBar extends Div {
    private UIComponent bar;
    private double value = 0;
    private double max = 100;
    private String color = "var(--jettra-accent)";

    public ProgressBar() {
        super();
        this.addClass("j-progressbar-container");
        this.setStyle("width", "100%")
            .setStyle("height", "12px")
            .setStyle("background", "rgba(255,255,255,0.05)")
            .setStyle("border-radius", "6px")
            .setStyle("overflow", "hidden")
            .setStyle("position", "relative")
            .setStyle("border", "1px solid rgba(255,255,255,0.1)");

        this.bar = new UIComponent("div") {};
        this.bar.addClass("j-progressbar-fill");
        this.bar.setStyle("height", "100%")
                .setStyle("width", "0%")
                .setStyle("background", color)
                .setStyle("transition", "width 0.3s ease")
                .setStyle("box-shadow", "0 0 10px " + color);
        
        this.add(bar);
    }

    public ProgressBar(double value, double max) {
        this();
        this.value = value;
        this.max = max;
        updateBar();
    }

    public ProgressBar setValue(double value) {
        this.value = value;
        updateBar();
        return this;
    }

    public double getValue() {
        return value;
    }

    public ProgressBar setMax(double max) {
        this.max = max;
        updateBar();
        return this;
    }

    public double getMax() {
        return max;
    }

    public ProgressBar setColor(String color) {
        this.color = color;
        this.bar.setStyle("background", color).setStyle("box-shadow", "0 0 10px " + color);
        return this;
    }

    private void updateBar() {
        double percentage = (value / max) * 100;
        this.bar.setStyle("width", percentage + "%");
    }

    public ProgressBar setIndeterminate(boolean indeterminate) {
        if (indeterminate) {
            this.bar.setStyle("width", "30%")
                    .setStyle("position", "absolute")
                    .addClass("j-progress-indeterminate");
            this.addStyle(".j-progress-indeterminate { animation: j-progress-move 1.5s infinite linear; } " +
                          "@keyframes j-progress-move { from { left: -30%; } to { left: 100%; } }");
        } else {
            this.bar.removeClass("j-progress-indeterminate")
                    .setStyle("position", "relative");
            updateBar();
        }
        return this;
    }
    
    private void addStyle(String css) {
        // Simple internal style injector for component-specific animations
        this.add(new Style(css));
    }
}
