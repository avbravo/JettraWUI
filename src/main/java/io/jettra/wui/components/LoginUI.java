package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

/**
 * LoginUI is a component that generates the <loginui> tag.
 */
public class LoginUI extends UIComponent {

    public LoginUI() {
        super("loginui");
        this.initialClasses = "j-loginui";
    }

    @Override
    public LoginUI setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public LoginUI setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public LoginUI setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public LoginUI addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public LoginUI removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public LoginUI setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public LoginUI setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public LoginUI addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public LoginUI add(UIComponent child) {
        super.add(child);
        return this;
    }
}
