package io.jettra.wui.complex;
import io.jettra.wui.core.UIComponent;

public class Modal extends UIComponent {
    public Modal(String id) {
        super("div");
        setProperty("id", id);
        this.initialClasses = "j-component";
        this.setStyle("display", "none");
        this.setStyle("position", "fixed");
        this.setStyle("top", "50%");
        this.setStyle("left", "50%");
        this.setStyle("transform", "translate(-50%, -50%)");
        this.setStyle("margin", "0");
        this.setStyle("width", "fit-content");
        this.setStyle("height", "fit-content");
        this.setStyle("max-width", "90vw");
        this.setStyle("max-height", "90vh");
        this.setStyle("overflow", "auto");
        this.setStyle("z-index", "1000");
        this.setStyle("min-width", "300px");
        this.setStyle("box-shadow", "0 0 50px rgba(0,255,255,0.2), 0 0 100px rgba(0,0,0,0.8)");
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
