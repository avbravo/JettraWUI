package io.jettra.wui.components;

/**
 * Componente compuesto de Login Avanzado que integra una selección de roles
 * u opciones extras además de los controles básicos.
 */
public class LoginAdvanced extends Form {
    
    private TextBox username;
    private TextBox password;
    private SelectOne roleSelect;
    private Button loginButton;
    private Button forgotPasswordButton;

    public LoginAdvanced(String action) {
        super("login-advanced-form", action);
        this.initialClasses = "j-login j-login-advanced";

        username = new TextBox("text", "username");
        username.setPlaceholder("Username");

        password = new TextBox("password", "password");
        password.setPlaceholder("Password");
        
        roleSelect = new SelectOne("role");

        loginButton = new Button("Login");
        forgotPasswordButton = new Button("Forgot Password?");
        forgotPasswordButton.setProperty("class", "j-forgot-btn");

        add(username);
        add(password);
        add(roleSelect);
        add(loginButton);
        add(forgotPasswordButton);
    }

    public TextBox getUsername() {
        return username;
    }

    public TextBox getPassword() {
        return password;
    }

    public SelectOne getRoleSelect() {
        return roleSelect;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getForgotPasswordButton() {
        return forgotPasswordButton;
    }

    @Override
    public LoginAdvanced setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public LoginAdvanced setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public LoginAdvanced setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public LoginAdvanced addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public LoginAdvanced removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public LoginAdvanced setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public LoginAdvanced setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public LoginAdvanced addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public LoginAdvanced add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
