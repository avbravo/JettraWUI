package io.jettra.wui.components;

/**
 * Componente compuesto de Login funcional que agrupa controles de autenticación.
 */
public class Login extends Form {
    
    private TextBox username;
    private TextBox password;
    private Button loginButton;
    private Button forgotPasswordButton;

    public Login(String action) {
        super("login-form", action);
        this.initialClasses = "j-login";

        username = new TextBox("text", "username");
        username.setPlaceholder("Username");

        password = new TextBox("password", "password");
        password.setPlaceholder("Password");

        loginButton = new Button("Login");
        forgotPasswordButton = new Button("Forgot Password?");
        // Se pueden añadir propiedades CSS predeterminadas para que parezca enlace
        forgotPasswordButton.setProperty("class", "j-forgot-btn");

        add(username);
        add(password);
        add(loginButton);
        add(forgotPasswordButton);
    }

    public TextBox getUsername() {
        return username;
    }

    public TextBox getPassword() {
        return password;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getForgotPasswordButton() {
        return forgotPasswordButton;
    }
}
