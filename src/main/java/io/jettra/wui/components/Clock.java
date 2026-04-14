package io.jettra.wui.components;
import io.jettra.wui.core.UIComponent;

public class Clock extends UIComponent {
    public Clock(String id) {
        super("div");
        setProperty("id", id);
        this.initialClasses = "j-clock j-component";
        this.setStyle("font-family", "monospace");
        this.setStyle("font-size", "2.5rem");
        this.setStyle("text-align", "center");
        this.setStyle("color", "var(--jettra-accent)");
        this.setStyle("text-shadow", "0 0 15px var(--jettra-glow)");
        
        UIComponent script = new UIComponent("script"){};
        script.setContent("setInterval(() => { const el = document.getElementById('"+id+"'); if(el) el.innerText = new Date().toLocaleTimeString(); }, 1000);");
        add(script);
    }

    @Override
    public Clock setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Clock setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Clock setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Clock addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Clock removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Clock setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Clock setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Clock addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Clock add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
