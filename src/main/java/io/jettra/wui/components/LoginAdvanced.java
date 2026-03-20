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
}
