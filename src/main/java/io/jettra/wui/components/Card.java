package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;
import java.util.Map;

/**
 * JettraWUI Card Component
 * A flexible container for presenting content visually appealingly.
 */
public class Card extends UIComponent {
    private String title = "";
    private String subtitle = "";
    private String content = "";
    private String imageUrl = "";
    private String width = "300px";

    public Card() {
        super("div");
        this.addClass("j-card").addClass("j-component");
        this.setStyle("background", "rgba(10,20,30,0.8)")
            .setStyle("border", "1px solid var(--jettra-accent)")
            .setStyle("border-radius", "8px")
            .setStyle("overflow", "hidden")
            .setStyle("display", "flex")
            .setStyle("flex-direction", "column")
            .setStyle("transition", "transform 0.2s, box-shadow 0.2s")
            .setStyle("box-shadow", "0 4px 15px rgba(0,0,0,0.5)");
            
        this.setProperty("onmouseover", "this.style.transform='translateY(-5px)'; this.style.boxShadow='0 10px 25px rgba(0,255,255,0.2)'");
        this.setProperty("onmouseout", "this.style.transform='translateY(0)'; this.style.boxShadow='0 4px 15px rgba(0,0,0,0.5)'");
    }

    public Card setTitle(String title) {
        this.title = title;
        return this;
    }

    public Card setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public Card setContentText(String content) {
        this.content = content;
        return this;
    }

    public Card setImageUrl(String url) {
        this.imageUrl = url;
        return this;
    }

    public Card setWidth(String width) {
        this.width = width;
        this.setStyle("width", width);
        return this;
    }

    @Override
    public String render() {
        if (width != null && !width.isEmpty()) {
            this.setStyle("width", width);
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("<").append(tag);
        
        if (!update.isEmpty()) {
            properties.put("data-update", update);
        }
        if (!initialClasses.isEmpty() && !properties.containsKey("class")) {
            properties.put("class", initialClasses.trim());
        }
        if (!clickListeners.isEmpty() && !properties.containsKey("onclick")) {
            properties.put("onclick", "jtFire('" + getId() + "')");
        }
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            builder.append(" ").append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
        }
        if (!styles.isEmpty()) {
            builder.append(" style=\"");
            for (Map.Entry<String, String> entry : styles.entrySet()) {
                builder.append(entry.getKey()).append(":").append(entry.getValue()).append("; ");
            }
            builder.append("\"");
        }
        builder.append(">");

        StringBuilder innerHtml = new StringBuilder();
        
        // Image
        if (imageUrl != null && !imageUrl.isEmpty()) {
            innerHtml.append("<img src=\"").append(imageUrl).append("\" style=\"width:100%; height:auto; border-bottom:1px solid rgba(255,255,255,0.1);\" />");
        }
        
        // Body container
        innerHtml.append("<div style=\"padding: 15px; display: flex; flex-direction: column; gap: 10px; flex: 1;\">");
        
        if (title != null && !title.isEmpty()) {
            innerHtml.append("<h3 style=\"margin: 0; color: var(--jettra-accent); font-size: 1.25rem;\">").append(title).append("</h3>");
        }
        
        if (subtitle != null && !subtitle.isEmpty()) {
            innerHtml.append("<span style=\"color: #9aa; font-size: 0.9rem;\">").append(subtitle).append("</span>");
        }
        
        if (content != null && !content.isEmpty()) {
            innerHtml.append("<p style=\"margin: 0; color: var(--jettra-text); font-size: 0.95rem; line-height: 1.5;\">").append(content).append("</p>");
        }
        
        StringBuilder childrenHtmlBuilder = new StringBuilder();
        for (io.jettra.wui.core.UIComponent child : this.getChildren()) {
            childrenHtmlBuilder.append(child.render());
        }
        String childrenHtml = childrenHtmlBuilder.toString();
        if (childrenHtml != null && !childrenHtml.isEmpty()) {
            innerHtml.append("<div style=\"margin-top: auto; padding-top: 15px; border-top: 1px dashed rgba(255,255,255,0.1); display: flex; gap: 10px; align-items: center;\">");
            innerHtml.append(childrenHtml);
            innerHtml.append("</div>");
        }
        
        innerHtml.append("</div>");
        builder.append(innerHtml.toString());
        
        builder.append("</").append(tag).append(">\n");
        return builder.toString();
    }

    @Override
    public Card setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Card setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Card setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Card addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Card removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Card setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Card addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Card add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
