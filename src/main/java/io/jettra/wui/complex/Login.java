package io.jettra.wui.complex;
import io.jettra.wui.components.*;

public class Login extends Form {
    public Login(String action) {
        super("login-form", action);
        this.setStyle("max-width", "400px");
        this.setStyle("margin", "0 auto");
        
        Header header = new Header(2, "Secure Access");
        header.setStyle("text-align", "center");
        add(header);
        
        Label userLabel = new Label("username", "Identification Code");
        TextBox userField = new TextBox("text", "username");
        userField.setProperty("required", "true");
        add(userLabel).add(userField);
        
        Label passLabel = new Label("password", "Authorization Key");
        TextBox passField = new TextBox("password", "password");
        passField.setProperty("required", "true");
        add(passLabel).add(passField);
        
        Button submit = new Button("AUTHENTICATE");
        submit.setStyle("margin-top", "20px");
        add(submit);
    }

    @Override
    public Login setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public Login setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public Login setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public Login addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public Login removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public Login setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public Login setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public Login addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public Login add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
