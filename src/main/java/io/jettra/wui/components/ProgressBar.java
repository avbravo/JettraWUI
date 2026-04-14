package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

/**
 * ProgressBar component for displaying process completion status.
 */
public class ProgressBar extends Div {
    private UIComponent bar;
    private UIComponent label;
    private double value = 0;
    private double max = 100;
    private String color = "var(--jettra-accent)";
    private boolean showPercent = true;

    public ProgressBar() {
        super();
        this.setId("pb-" + java.util.UUID.randomUUID().toString().substring(0, 8));
        this.addClass("j-progressbar-container");
        this.setStyle("width", "100%")
            .setStyle("height", "24px") // Increased height to fit text
            .setStyle("background", "rgba(255,255,255,0.05)")
            .setStyle("border-radius", "12px")
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
        
        this.label = new UIComponent("div") {};
        this.label.addClass("j-progressbar-label");
        this.label.setStyle("position", "absolute")
                  .setStyle("top", "50%")
                  .setStyle("left", "50%")
                  .setStyle("transform", "translate(-50%, -50%)")
                  .setStyle("font-size", "12px")
                  .setStyle("font-weight", "bold")
                  .setStyle("color", "#fff")
                  .setStyle("text-shadow", "0 0 5px #000")
                  .setStyle("pointer-events", "none")
                  .setStyle("z-index", "2");
        
        this.add(bar).add(label);
    }

    public ProgressBar(double value, double max) {
        this();
        this.value = value;
        this.max = max;
        updateBar();
    }

    public ProgressBar setShowPercent(boolean show) {
        this.showPercent = show;
        this.label.setStyle("display", show ? "block" : "none");
        updateBar();
        return this;
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
        if (showPercent) {
            this.label.setContent(Math.round(percentage) + "%");
        }
    }

    @Override
    public String render() {
        String script = "<script>" +
                        "window.updateProgressBar = function(id, val, mx) {" +
                        "  var el = document.getElementById(id);" +
                        "  if(!el) return;" +
                        "  var bar = el.querySelector('.j-progressbar-fill');" +
                        "  var lbl = el.querySelector('.j-progressbar-label');" +
                        "  var pct = (val / (mx || 100)) * 100;" +
                        "  bar.style.width = pct + '%';" +
                        "  if(lbl) lbl.innerText = Math.round(pct) + '%';" +
                        "};" +
                        "</script>";
        return super.render() + script;
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

    @Override
    public ProgressBar setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public ProgressBar setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public ProgressBar setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public ProgressBar addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public ProgressBar removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public ProgressBar setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public ProgressBar setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public ProgressBar addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public ProgressBar add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
