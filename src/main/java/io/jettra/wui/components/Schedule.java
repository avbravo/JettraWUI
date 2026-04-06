package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;
import java.util.ArrayList;
import java.util.List;

public class Schedule extends UIComponent {

    private List<Event> events = new ArrayList<>();

    public Schedule() {
        super("div");
        addClass("j-schedule");
        setStyle("border", "1px solid var(--jettra-border)");
        setStyle("border-radius", "8px");
        setStyle("overflow", "hidden");
        setStyle("background", "var(--jettra-surface)");
    }
    
    public void addEvent(String title, String day, String startTime) {
        events.add(new Event(title, day, startTime));
    }
    
    @Override
    public String render() {
        StringBuilder html = new StringBuilder();
        
        // Header
        html.append("<div style='background:rgba(0,0,0,0.2); padding:10px; text-align:center; font-weight:bold; border-bottom:1px solid var(--jettra-border); color:var(--jettra-accent);'>")
            .append("  Weekly Schedule")
            .append("</div>");
            
        // Days Row
        html.append("<div style='display:grid; grid-template-columns:50px repeat(5, 1fr); text-align:center; border-bottom:1px solid rgba(255,255,255,0.1); font-weight:bold; font-size:0.8rem; opacity:0.8'>");
        html.append("<div>Time</div>");
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri"};
        for (String day : days) {
            html.append("<div style='border-left:1px solid rgba(255,255,255,0.1); padding:5px;'>").append(day).append("</div>");
        }
        html.append("</div>");
        
        // Grid (8AM to 5PM logic placeholder)
        for(int h=8; h<=17; h++) {
            html.append("<div style='display:grid; grid-template-columns:50px repeat(5, 1fr); border-bottom:1px solid rgba(255,255,255,0.05); min-height:40px;'>");
            html.append("<div style='font-size:0.7rem; text-align:right; padding-right:5px; opacity:0.6; padding-top:5px;'>").append(h).append(":00</div>");
            for(int d=0; d<5; d++) {
                html.append("<div style='border-left:1px solid rgba(255,255,255,0.05); position:relative;'>");
                
                // Add events for this slot
                for(Event e : events) {
                    if (e.day.equalsIgnoreCase(days[d]) && e.startTime.startsWith(h+":")) {
                        html.append("<div style='background:var(--jettra-accent); color:#fff; font-size:0.7rem; padding:2px 4px; margin:2px; border-radius:3px; cursor:pointer;'>")
                            .append(e.title).append("</div>");
                    }
                }
                
                html.append("</div>");
            }
            html.append("</div>");
        }
        
        setContent(html.toString());
        return super.render();
    }
    
    private static class Event {
        String title, day, startTime;
        Event(String title, String day, String startTime) {
            this.title = title; this.day = day; this.startTime = startTime;
        }
    }
}
