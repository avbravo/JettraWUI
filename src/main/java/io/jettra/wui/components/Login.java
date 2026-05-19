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
    private Div headerContainer;
    private Div logo;
    private Label appNameLabel;

    private static final String defaultLogoSvg = 
        "<svg viewBox=\"0 0 100 100\" width=\"60\" height=\"60\" xmlns=\"http://www.w3.org/2000/svg\">"
        + "<defs>"
        + "  <linearGradient id=\"logoGrad\" x1=\"0%\" y1=\"0%\" x2=\"100%\" y2=\"100%\">"
        + "    <stop offset=\"0%\" stop-color=\"#00f2fe\" />"
        + "    <stop offset=\"100%\" stop-color=\"#4facfe\" />"
        + "  </linearGradient>"
        + "</defs>"
        + "<polygon points=\"50,15 90,38 90,82 50,95 10,82 10,38\" fill=\"none\" stroke=\"url(#logoGrad)\" stroke-width=\"4\" stroke-linejoin=\"round\" />"
        + "<polygon points=\"50,25 80,42 80,75 50,85 20,75 20,42\" fill=\"none\" stroke=\"url(#logoGrad)\" stroke-width=\"2\" stroke-linejoin=\"round\" opacity=\"0.6\" />"
        + "<path d=\"M50,30 L50,70\" stroke=\"url(#logoGrad)\" stroke-width=\"6\" stroke-linecap=\"round\" />"
        + "<path d=\"M35,45 L50,30 L65,45\" fill=\"none\" stroke=\"url(#logoGrad)\" stroke-width=\"6\" stroke-linecap=\"round\" stroke-linejoin=\"round\" />"
        + "</svg>";

    public Login(String action) {
        super("login-form", action);
        this.initialClasses = "j-login";

        // Create header with logo and app name
        headerContainer = new Div();
        headerContainer.setId("login-header");
        headerContainer.setStyle("display", "flex")
                       .setStyle("flex-direction", "column")
                       .setStyle("align-items", "center")
                       .setStyle("margin-bottom", "25px");
        
        logo = new Div();
        logo.setContent(defaultLogoSvg);
        headerContainer.add(logo);
        
        appNameLabel = new Label("Jettra Stack");
        appNameLabel.setId("login-appname");
        appNameLabel.setStyle("font-size", "1.6em")
                    .setStyle("font-weight", "bold")
                    .setStyle("letter-spacing", "2px")
                    .setStyle("color", "#00f2fe")
                    .setStyle("margin-top", "10px")
                    .setStyle("margin-bottom", "5px")
                    .setStyle("font-family", "'Segoe UI', Roboto, sans-serif");
        headerContainer.add(appNameLabel);
        
        Label taglineLabel = new Label("Secure Portal");
        taglineLabel.setStyle("font-size", "0.8em")
                    .setStyle("color", "rgba(255, 255, 255, 0.6)")
                    .setStyle("letter-spacing", "1px")
                    .setStyle("text-transform", "uppercase");
        headerContainer.add(taglineLabel);
        
        add(headerContainer);

        username = new TextBox("text", "username");
        username.setPlaceholder("Username");

        password = new TextBox("password", "password");
        password.setPlaceholder("Password");

        loginButton = new Button("Login").setType("submit");
        forgotPasswordButton = new Button("Forgot Password?");
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

    public Login setApplicationName(String name) {
        if (appNameLabel != null) {
            appNameLabel.setContent(name);
        }
        return this;
    }

    public Login setLogoUrl(String url) {
        if (logo != null) {
            if (url != null && !url.isEmpty()) {
                logo.setContent("<img src=\"" + url + "\" style=\"width:60px; height:60px; object-fit:contain;\" />");
            } else {
                logo.setContent(defaultLogoSvg);
            }
        }
        return this;
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
