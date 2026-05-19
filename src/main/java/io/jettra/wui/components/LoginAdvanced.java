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

    public LoginAdvanced(String action) {
        super("login-advanced-form", action);
        this.initialClasses = "j-login j-login-advanced";

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

    public LoginAdvanced setApplicationName(String name) {
        if (appNameLabel != null) {
            appNameLabel.setContent(name);
        }
        return this;
    }

    public LoginAdvanced setLogoUrl(String url) {
        if (logo != null) {
            if (url != null && !url.isEmpty()) {
                logo.setContent("<img src=\"" + url + "\" style=\"width:60px; height:60px; object-fit:contain;\" />");
            } else {
                logo.setContent(defaultLogoSvg);
            }
        }
        return this;
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
