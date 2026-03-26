package io.jettra.wui.components;
import io.jettra.wui.core.UIComponent;
public class Button extends UIComponent {
    public Button(String text) {
        super("button");
        setContent(text);
        this.initialClasses = "j-btn";
    }

    public Button id(String id) {
        setId(id);
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
