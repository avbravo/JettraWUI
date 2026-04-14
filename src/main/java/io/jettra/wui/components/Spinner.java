package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;
import java.util.UUID;

public class Spinner extends UIComponent {
    private double min = 0;
    private double max = 100;
    private double step = 1;
    private double value = 0;
    private String name;
    private String spinnerId;

    public Spinner(String name, double value) {
        super("div");
        this.name = name;
        this.value = value;
        this.spinnerId = "spin-" + UUID.randomUUID().toString().substring(0, 8);
        
        addClass("j-spinner-container");
        setProperty("id", spinnerId + "-container");
        
        // Hidden input for form submission
        UIComponent hidden = new UIComponent("input") {};
        hidden.setProperty("type", "hidden");
        hidden.setProperty("name", name);
        hidden.setProperty("id", spinnerId + "-input");
        hidden.setProperty("value", String.valueOf(value));
        add(hidden);

        // UI Layout
        Div wrapper = new Div();
        wrapper.addClass("j-spinner-wrapper");
        wrapper.setStyle("display", "inline-flex").setStyle("align-items", "center")
               .setStyle("border", "1px solid var(--jettra-border)")
               .setStyle("border-radius", "8px").setStyle("background", "rgba(0,0,0,0.3)")
               .setStyle("overflow", "hidden");

        Button btnMinus = new Button("-");
        btnMinus.addClass("j-spinner-btn j-spinner-minus");
        btnMinus.setProperty("onclick", "jettraSpinnerStep('" + spinnerId + "', -1)");
        btnMinus.setStyle("width", "40px").setStyle("height", "40px").setStyle("background", "rgba(255,255,255,0.05)")
                .setStyle("border", "none").setStyle("color", "var(--jettra-accent)").setStyle("cursor", "pointer")
                .setStyle("font-size", "1.2rem").setStyle("font-weight", "bold");

        Div display = new Div();
        display.setId(spinnerId + "-display");
        display.addClass("j-spinner-display");
        display.setContent(String.valueOf(value));
        display.setStyle("min-width", "60px").setStyle("text-align", "center")
               .setStyle("font-family", "monospace").setStyle("font-size", "1.1rem")
               .setStyle("color", "var(--jettra-text)");

        Button btnPlus = new Button("+");
        btnPlus.addClass("j-spinner-btn j-spinner-plus");
        btnPlus.setProperty("onclick", "jettraSpinnerStep('" + spinnerId + "', 1)");
        btnPlus.setStyle("width", "40px").setStyle("height", "40px").setStyle("background", "rgba(255,255,255,0.05)")
                .setStyle("border", "none").setStyle("color", "var(--jettra-accent)").setStyle("cursor", "pointer")
                .setStyle("font-size", "1.2rem").setStyle("font-weight", "bold");

        wrapper.add(btnMinus).add(display).add(btnPlus);
        add(wrapper);
    }

    public Spinner setMin(double min) { this.min = min; return this; }
    public Spinner setMax(double max) { this.max = max; return this; }
    public Spinner setStep(double step) { this.step = step; return this; }

    @Override
    public String render() {
        String js = "<script>\n" +
            "if (typeof jettraSpinnerStep === 'undefined') {\n" +
            "  window.jettraSpinnerStep = function(id, direction) {\n" +
            "    const input = document.getElementById(id + '-input');\n" +
            "    const display = document.getElementById(id + '-display');\n" +
            "    const container = document.getElementById(id + '-container');\n" +
            "    if (!input || !display) return;\n" +
            "    \n" +
            "    let val = parseFloat(input.value);\n" +
            "    const min = parseFloat(container.getAttribute('data-min') || '-Infinity');\n" +
            "    const max = parseFloat(container.getAttribute('data-max') || 'Infinity');\n" +
            "    const step = parseFloat(container.getAttribute('data-step') || '1');\n" +
            "    \n" +
            "    val += (direction * step);\n" +
            "    if (val < min) val = min;\n" +
            "    if (val > max) val = max;\n" +
            "    \n" +
            "    // Fix floating point precision issues\n" +
            "    val = Math.round(val * 1000000) / 1000000;\n" +
            "    \n" +
            "    input.value = val;\n" +
            "    display.innerText = val;\n" +
            "  }\n" +
            "}\n" +
            "</script>\n";
        
        setProperty("data-min", String.valueOf(min));
        setProperty("data-max", String.valueOf(max));
        setProperty("data-step", String.valueOf(step));
        
        String debugEntry = "<!-- Spinner: " + name + " (initial: " + value + ") -->\n";
        return debugEntry + super.render() + js;
    }

    @Override
    public Spinner setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Spinner setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Spinner setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Spinner addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Spinner removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Spinner setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Spinner setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Spinner addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Spinner add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
