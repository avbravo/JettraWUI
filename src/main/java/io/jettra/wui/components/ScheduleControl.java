package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class ScheduleControl extends UIComponent {

    public ScheduleControl(String id) {
        super("input");
        setProperty("id", id);
        setProperty("name", id);
        setProperty("type", "datetime-local");
        setProperty("step", "1"); // Allows seconds selection
        this.initialClasses = "j-input j-schedule";
        setStyle("padding", "8px")
            .setStyle("border", "1px solid var(--jettra-border)")
            .setStyle("border-radius", "4px")
            .setStyle("background", "rgba(0,0,0,0.3)")
            .setStyle("color", "var(--jettra-text)");
    }

    public ScheduleControl setValue(String value) {
        setProperty("value", value);
        return this;
    }

    public ScheduleControl setMin(String min) {
        setProperty("min", min);
        return this;
    }

    public ScheduleControl setMax(String max) {
        setProperty("max", max);
        return this;
    }

    @Override
    public ScheduleControl setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }
}
