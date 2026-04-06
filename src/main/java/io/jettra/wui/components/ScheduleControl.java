package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class ScheduleControl extends UIComponent {

    private boolean showTimeRemaining = false;
    private String onTimeReached = "";

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

    public ScheduleControl setShowTimeRemaining(boolean show) {
        this.showTimeRemaining = show;
        return this;
    }

    public ScheduleControl setOnTimeReached(String script) {
        this.onTimeReached = script;
        return this;
    }

    @Override
    public ScheduleControl setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public String render() {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class='j-schedule-container' style='display:flex;align-items:center;gap:10px;'>\n");
        sb.append(super.render());
        if (showTimeRemaining) {
            sb.append("<span id='").append(getId()).append("_remaining' class='j-schedule-remaining' style='font-family:monospace; color:var(--jettra-accent); font-weight:bold; font-size:1.1rem; padding: 0 5px;'>0h 0m 0s</span>\n");
        }
        sb.append("</div>\n");

        sb.append("<script>\n");
        sb.append("  (function() {\n");
        sb.append("      var el = document.getElementById('").append(getId()).append("');\n");
        sb.append("      var rem = document.getElementById('").append(getId()).append("_remaining');\n");
        sb.append("      var triggered = false;\n");
        sb.append("      if(el) {\n");
        sb.append("         setInterval(function() {\n");
        sb.append("             if(!el.value) { if(rem) {rem.innerHTML='';} triggered=false; return; }\n");
        sb.append("             var target = new Date(el.value).getTime();\n");
        sb.append("             var now = new Date().getTime();\n");
        sb.append("             var diff = target - now;\n");
        sb.append("             if(rem) {\n");
        sb.append("                 if(diff > 0) {\n");
        sb.append("                     var sec = Math.floor((diff / 1000) % 60);\n");
        sb.append("                     var min = Math.floor((diff / 1000 / 60) % 60);\n");
        sb.append("                     var hrs = Math.floor((diff / (1000 * 60 * 60)) % 24);\n");
        sb.append("                     var days = Math.floor(diff / (1000 * 60 * 60 * 24));\n");
        sb.append("                     rem.innerHTML = (days>0?days+'d ':'') + hrs+'h '+min+'m '+sec+'s';\n");
        sb.append("                 } else {\n");
        sb.append("                     rem.innerHTML = '0h 0m 0s';\n");
        sb.append("                 }\n");
        sb.append("             }\n");
        sb.append("             if(diff <= 0 && !triggered) {\n");
        sb.append("                 triggered = true;\n");
        sb.append("                 var ids = el.getAttribute('data-update');\n");
        sb.append("                 if(ids) { if(typeof jettraUpdate === 'function') jettraUpdate(ids); }\n");
        if (onTimeReached != null && !onTimeReached.isEmpty()) {
            sb.append("                 ").append(onTimeReached).append("\n");
        }
        sb.append("             }\n");
        sb.append("             if(diff > 0 && triggered) { triggered = false; }\n");
        sb.append("         }, 1000);\n");
        sb.append("      }\n");
        sb.append("  })();\n");
        sb.append("</script>\n");

        return sb.toString();
    }
}
