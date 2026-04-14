package io.jettra.wui.components;
import io.jettra.wui.core.UIComponent;
public class Label extends UIComponent {
    public Label(String text) {
        this("", text);
    }
    public Label(String forId, String text) {
        super("label");
        if (forId != null && !forId.isEmpty()) {
            setProperty("for", forId);
        }
        setContent(text);
        this.setStyle("display", "block").setStyle("margin-bottom", "5px").setStyle("font-size", "0.9em");
    }

    @Override
    public Label setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Label setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Label setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Label addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Label removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Label setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Label setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Label addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Label add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
