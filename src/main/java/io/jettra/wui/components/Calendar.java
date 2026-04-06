package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

public class Calendar extends UIComponent {

    public Calendar() {
        super("div");
        addClass("j-calendar");
        // Inline CSS mapping to support internal basic look if user doesn't inject css
        setStyle("border", "1px solid var(--jettra-border)");
        setStyle("border-radius", "8px");
        setStyle("overflow", "hidden");
        setStyle("background", "var(--jettra-surface)");
    }
    
    @Override
    public String render() {
        // Quick generation of a standard month placeholder calendar
        StringBuilder html = new StringBuilder();
        
        // Header
        html.append("<div style='background:rgba(0,0,0,0.2); padding:10px; text-align:center; font-weight:bold; border-bottom:1px solid var(--jettra-border); color:var(--jettra-accent);'>")
            .append("  <span style='cursor:pointer; float:left'>&lt;</span>")
            .append("  Current Month")
            .append("  <span style='cursor:pointer; float:right'>&gt;</span>")
            .append("</div>");
            
        // Days Row
        html.append("<div style='display:grid; grid-template-columns:repeat(7, 1fr); text-align:center; padding:10px; font-weight:bold; font-size:0.8rem; opacity:0.8'>");
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : days) {
            html.append("<div>").append(day).append("</div>");
        }
        html.append("</div>");
        
        // Grid
        html.append("<div style='display:grid; grid-template-columns:repeat(7, 1fr); padding:10px; gap:5px;'>");
        for(int i=1; i<=31; i++) {
            String cellStyle = "padding:10px; text-align:center; border-radius:4px; transition:all 0.2s; cursor:pointer;";
            if (i == 15) { // mock current day
                html.append("<div style='").append(cellStyle).append(" background:var(--jettra-accent); color:#fff;'>").append(i).append("</div>");
            } else {
                html.append("<div style='").append(cellStyle).append(" background:rgba(255,255,255,0.05);' onmouseover=\"this.style.background='rgba(255,255,255,0.1)'\" onmouseout=\"this.style.background='rgba(255,255,255,0.05)'\">").append(i).append("</div>");
            }
        }
        html.append("</div>");
        
        setContent(html.toString());
        return super.render();
    }
}
