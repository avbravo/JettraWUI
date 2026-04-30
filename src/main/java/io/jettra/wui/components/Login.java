package io.jettra.wui.components;

/**
 * Componente compuesto de Login funcional que agrupa controles de
 * autenticación.
 */
public class Login extends Form {

    private TextBox username;
    private TextBox password;
    private Button loginButton;
    private Button forgotPasswordButton;
    private Alert alertMessage;

    public Login(String action) {
        super("login-form", action);
        this.initialClasses = "j-login";

        username = new TextBox("text", "username");
        username.setPlaceholder("Username");

        password = new TextBox("password", "password");
        password.setPlaceholder("Password");

        loginButton = new Button("Login").setType("submit");
        forgotPasswordButton = new Button("Forgot Password?");
        // Se pueden añadir propiedades CSS predeterminadas para que parezca enlace
        forgotPasswordButton.setProperty("class", "j-forgot-btn");

        alertMessage = new Alert();
        alertMessage.setType("warning"); // Default warning
        
        // Aplica estilo 3D
        style3D();
        
        add(alertMessage);
        add(username);
        add(password);
        add(loginButton);
        add(forgotPasswordButton);
    }

    public Login style3D() {

        try {
            this.setProperty("style", "margin: 10% auto; padding: 30px; width: 350px; "
                    + "background: rgba(20, 30, 50, 0.85); box-shadow: 0 0 20px cyan; "
                    + "border-radius: 15px; backdrop-filter: blur(10px); "
                    + "color: white; font-family: 'Courier New', monospace; text-align: center;");

            this.getUsername().setProperty("style", "width: 100%; padding: 10px; margin-bottom: 15px; "
                    + "background: #0f172a; border: 1px solid cyan; color: white; border-radius: 5px;");

            this.getPassword().setProperty("style", "width: 100%; padding: 10px; margin-bottom: 15px; "
                    + "background: #0f172a; border: 1px solid cyan; color: white; border-radius: 5px;");

            this.getLoginButton().setProperty("style", "width: 100%; padding: 12px; margin-bottom: 10px; "
                    + "background: cyan; color: #0f172a; border: none; border-radius: 5px; font-weight: bold; cursor: pointer; "
                    + "box-shadow: 0 0 10px cyan;");

            this.getForgotPasswordButton().setProperty("style", "background: transparent; border: none; "
                    + "color: cyan; cursor: pointer; text-decoration: underline; font-size: 0.9em;");
        } catch (Exception e) {
        }
        return this;
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

    public Alert getAlertMessage() {
        return alertMessage;
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
