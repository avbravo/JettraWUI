package io.jettra.wui.core;

import io.jettra.wui.events.ClickListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import java.util.concurrent.CopyOnWriteArrayList;

public abstract class UIComponent {
    protected String tag;
    protected String content = "";
    protected boolean rawContent = true;
    protected List<UIComponent> children = new CopyOnWriteArrayList<>();
    protected Map<String, String> properties = new LinkedHashMap<>();
    protected Map<String, String> styles = new LinkedHashMap<>();
    protected String initialClasses = "";
    protected List<ClickListener> clickListeners = new CopyOnWriteArrayList<>();

    public UIComponent(String tag) {
        this.tag = tag;
    }

    public String getId() {
        return properties.get("id");
    }

    public UIComponent setId(String id) {
        properties.put("id", id);
        return this;
    }

    public UIComponent addClickListener(ClickListener listener) {
        this.clickListeners.add(listener);
        if (getId() == null) {
            setId("jt-" + UUID.randomUUID().toString().substring(0, 8));
        }
        return this;
    }

    public List<ClickListener> getClickListeners() {
        return clickListeners;
    }

    public UIComponent setProperty(String key, String value) {
        properties.put(key, value);
        return this;
    }

    public UIComponent setStyle(String key, String value) {
        styles.put(key, value);
        return this;
    }

    public UIComponent addClass(String className) {
        if (properties.containsKey("class")) {
            properties.put("class", properties.get("class") + " " + className);
        } else {
            properties.put("class", initialClasses + " " + className);
        }
        return this;
    }

    public UIComponent removeClass(String className) {
        if (properties.containsKey("class")) {
            String classes = properties.get("class");
            String[] parts = classes.split("\\s+");
            StringBuilder sb = new StringBuilder();
            for (String p : parts) {
                if (!p.equals(className) && !p.isEmpty()) {
                    if (sb.length() > 0) sb.append(" ");
                    sb.append(p);
                }
            }
            properties.put("class", sb.toString());
        }
        return this;
    }


    public UIComponent add(UIComponent child) {
        children.add(child);
        return this;
    }

    public UIComponent setContent(String content) {
        this.content = content;
        this.rawContent = true;
        return this;
    }

    /**
     * Sets content and escapes it for safety (XSS protection).
     * @param content the content to escape
     */
    public UIComponent setEscapedContent(String content) {
        this.content = content;
        this.rawContent = false;
        return this;
    }


    private String escapeHtml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }

    public List<UIComponent> getChildren() {
        return children;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public Map<String, String> getStyles() {
        return styles;
    }

    public String getTag() {
        return tag;
    }

    public String render() {
        StringBuilder builder = new StringBuilder();
        builder.append("<").append(tag);
        
        // Ensure class property carries default styling logic
        if (!initialClasses.isEmpty() && !properties.containsKey("class")) {
            properties.put("class", initialClasses.trim());
        }

        // Auto-inject jettra event trigger if listeners exist and no onclick is manually set
        if (!clickListeners.isEmpty() && !properties.containsKey("onclick")) {
            properties.put("onclick", "jtFire('" + getId() + "')");
        }

        // Properties
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            builder.append(" ").append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
        }
        
        // Styles
        if (!styles.isEmpty()) {
            builder.append(" style=\"");
            for (Map.Entry<String, String> entry : styles.entrySet()) {
                builder.append(entry.getKey()).append(":").append(entry.getValue()).append("; ");
            }
            builder.append("\"");
        }
        
        builder.append(">");
        builder.append(rawContent ? content : escapeHtml(content));
        
        for (UIComponent child : children) {
            builder.append(child.render());
        }
        
        builder.append("</").append(tag).append(">\n");
        return builder.toString();
    }
}
