package io.jettra.wui.components;
import io.jettra.wui.core.UIComponent;
public class Header extends UIComponent {
    public Header(int level, String text) {
        super("h" + Math.max(1, Math.min(6, level)));
        setContent(text);
        this.initialClasses = "j-header";
        this.setStyle("color", "var(--jettra-accent)");
        this.setStyle("text-shadow", "0 0 10px var(--jettra-glow)");
    }

    @Override
    public Header setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Header setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Header setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Header addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Header removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Header setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Header setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Header addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Header add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
