package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;
import java.util.ArrayList;
import java.util.List;

public class Timeline extends UIComponent {

    private List<Node> nodes = new ArrayList<>();

    public Timeline() {
        super("div");
        addClass("j-timeline");
        setStyle("position", "relative");
        setStyle("padding", "20px 0");
        setStyle("margin-left", "20px");
    }
    
    public void addNode(String title, String description, String timestamp) {
        nodes.add(new Node(title, description, timestamp));
    }
    
    @Override
    public String render() {
        StringBuilder html = new StringBuilder();
        
        // Vertical line logic handled through border-left in CSS
        html.append("<div style='border-left: 2px solid var(--jettra-border); position: relative;'>");
        
        for (Node n : nodes) {
            html.append("<div style='position: relative; margin-bottom: 20px; padding-left: 20px;'>");
            // Circle
            html.append("<div style='position: absolute; left: -9px; top: 0; width: 16px; height: 16px; border-radius: 50%; background: var(--jettra-accent); border: 3px solid var(--jettra-surface);'></div>");
            
            // Content
            html.append("<div style='padding: 10px; background: rgba(255,255,255,0.03); border: 1px solid var(--jettra-border); border-radius: 8px;'>");
            html.append("<div style='font-size: 0.8rem; color: #aaa; margin-bottom: 5px;'>").append(n.timestamp).append("</div>");
            html.append("<div style='font-weight: bold; color: var(--jettra-accent); margin-bottom: 5px;'>").append(n.title).append("</div>");
            html.append("<div style='font-size: 0.9rem; color: var(--jettra-text);'>").append(n.description).append("</div>");
            html.append("</div>"); // End Content Wrapper
            
            html.append("</div>"); // End Node Wrapper
        }
        html.append("</div>");
        
        setContent(html.toString());
        return super.render();
    }
    
    private static class Node {
        String title, description, timestamp;
        Node(String title, String desc, String time) {
            this.title = title; this.description = desc; this.timestamp = time;
        }
    }
}
