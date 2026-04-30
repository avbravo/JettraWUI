package io.jettra.wui.complex;
import io.jettra.wui.core.UIComponent;

public class Modal extends UIComponent {
    private String padding = "30px";
    private String backgroundColor = "#161b22";
    private String border = "1px solid #30363d";
    private String display = "none";
    private String position = "fixed";
    private String top = "50%";
    private String left = "50%";
    private String transform = "translate(-50%, -50%)";
    private String margin = "0";
    private String width = "fit-content";
    private String height = "fit-content";
    private String maxWidth = "90vw";
    private String maxHeight = "90vh";
    private String overflow = "auto";
    private String zIndex = "1000";
    private String minWidth = "300px";
    private String boxShadow = "0 0 50px rgba(0,255,255,0.2), 0 0 100px rgba(0,0,0,0.8)";

    public Modal(String id) {
        super("div");
        setProperty("id", id);
        this.initialClasses = "j-component";
        
        applyDefaultStyles();
    }

    private void applyDefaultStyles() {
        this.setStyle("padding", padding);
        this.setStyle("background-color", backgroundColor);
        this.setStyle("border", border);
        this.setStyle("display", display);
        this.setStyle("position", position);
        this.setStyle("top", top);
        this.setStyle("left", left);
        this.setStyle("transform", transform);
        this.setStyle("margin", margin);
        this.setStyle("width", width);
        this.setStyle("height", height);
        this.setStyle("max-width", maxWidth);
        this.setStyle("max-height", maxHeight);
        this.setStyle("overflow", overflow);
        this.setStyle("z-index", zIndex);
        this.setStyle("min-width", minWidth);
        this.setStyle("box-shadow", boxShadow);
        
        // Layout adaptivo
        this.setStyle("display", "flex");
        this.setStyle("flex-direction", "column");
        this.setStyle("gap", "15px");
        this.setStyle("display", "none"); 
    }

    public Modal setPadding(String padding) {
        this.padding = padding;
        return setStyle("padding", padding);
    }

    public Modal setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return setStyle("background-color", backgroundColor);
    }

    public Modal setBorder(String border) {
        this.border = border;
        return setStyle("border", border);
    }

    public Modal setDisplay(String display) {
        this.display = display;
        return setStyle("display", display);
    }

    public Modal setPosition(String position) {
        this.position = position;
        return setStyle("position", position);
    }

    public Modal setTop(String top) {
        this.top = top;
        return setStyle("top", top);
    }

    public Modal setLeft(String left) {
        this.left = left;
        return setStyle("left", left);
    }

    public Modal setTransform(String transform) {
        this.transform = transform;
        return setStyle("transform", transform);
    }

    public Modal setMargin(String margin) {
        this.margin = margin;
        return setStyle("margin", margin);
    }

    public Modal setWidth(String width) {
        this.width = width;
        return setStyle("width", width);
    }

    public Modal setHeight(String height) {
        this.height = height;
        return setStyle("height", height);
    }

    public Modal setMaxWidth(String maxWidth) {
        this.maxWidth = maxWidth;
        return setStyle("max-width", maxWidth);
    }

    public Modal setMaxHeight(String maxHeight) {
        this.maxHeight = maxHeight;
        return setStyle("max-height", maxHeight);
    }

    public Modal setOverflow(String overflow) {
        this.overflow = overflow;
        return setStyle("overflow", overflow);
    }

    public Modal setZIndex(String zIndex) {
        this.zIndex = zIndex;
        return setStyle("z-index", zIndex);
    }

    public Modal setMinWidth(String minWidth) {
        this.minWidth = minWidth;
        return setStyle("min-width", minWidth);
    }

    public Modal setBoxShadow(String boxShadow) {
        this.boxShadow = boxShadow;
        return setStyle("box-shadow", boxShadow);
    }

    @Override
    public String render() {
        String mainHtml = super.render();
        String id = properties.get("id");
        if (id != null && !id.isEmpty()) {
            String scriptId = "move_" + id.replaceAll("[^a-zA-Z0-9]", "_");
            String script = "<script id=\"" + scriptId + "\">" +
                "setTimeout(function() {" +
                "  var m = document.getElementById('" + id + "');" +
                "  if(m && m.parentNode !== document.body) {" +
                "    document.body.appendChild(m);" +
                "  }" +
                "  var s = document.getElementById('" + scriptId + "');" +
                "  if(s) s.remove();" +
                "}, 50);" +
                "</script>";
            return mainHtml + "\n" + script;
        }
        return mainHtml;
    }

    @Override
    public Modal setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Modal setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Modal setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Modal addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Modal removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Modal setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Modal setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Modal addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Modal add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
