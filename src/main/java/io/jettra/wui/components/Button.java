package io.jettra.wui.components;
import io.jettra.wui.core.UIComponent;
public class Button extends UIComponent {
    private String text = "";
    private String icon = "";

    public Button(String text) {
        super("button");
        this.text = text;
        updateContent();
        this.initialClasses = "j-btn";
    }

    private void updateContent() {
        if (icon != null && !icon.isEmpty()) {
            setContent("<span class=\"j-btn-icon\">" + icon + "</span> " + text);
        } else {
            setContent(text);
        }
    }

    public Button setIcon(String icon) {
        this.icon = icon;
        updateContent();
        return this;
    }

    public Button id(String id) {
        setId(id);
        return this;
    }

    @Override
    public Button setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    public Button addClass(String className) {
        super.addClass(className);
        return this;
    }

    public Button setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    public Button addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    public Button primary() {
        return addClass("j-btn-primary");
    }

    public Button secondary() {
        return addClass("j-btn-secondary");
    }

    public Button help() {
        return addClass("j-btn-help");
    }

    public Button danger() {
        return addClass("j-btn-danger");
    }

    public Button info() {
        return addClass("j-btn-info");
    }

    public Button warning() {
        return addClass("j-btn-warning");
    }
}
