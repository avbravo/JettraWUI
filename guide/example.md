# JettraWUI Examples
To build a fully functional futuristic 3D Web UI from Java 25, simply assemble the components as follows:

```java
import io.jettra.wui.core.Page;
import io.jettra.wui.components.Form;
import io.jettra.wui.components.Button;
import io.jettra.wui.components.TextBox;

public class Main {
    public static void main(String[] args) {
        // 1. Initialize Root Page (adds <body> and CSS internally)
        Page page = new Page("Jettra Enclave 3D");

        // 2. Initialize Container
        Form form = new Form("login-form", "/api/auth");
        
        // 3. Add Controls
        TextBox username = new TextBox("text", "username");
        username.setPlaceholder("Agent Identifier...");
        
        TextBox password = new TextBox("password", "password");
        password.setPlaceholder("Passcode...");
        
        Button loginBtn = new Button("Unlock Terminal");
        loginBtn.setStyle("margin-top", "20px");

        // 4. Assemble Hierarchy
        form.add(username);
        form.add(password);
        form.add(loginBtn);
        
        page.add(form);

        // 5. Generate and Output HTML
        String rawHtml = page.render();
        System.out.println(rawHtml);
        
    }
}
```

## Dashboard and Page Example
To use the new `Dashboard`, `Menu` components, and `Page` lifecycle annotations:

```java
import io.jettra.wui.complex.*;
import io.jettra.wui.components.*;
import io.jettra.wui.core.annotations.*;

public class AdminDashboardPage extends Page {

    private Dashboard dashboard;

    public AdminDashboardPage() {
        super();
    }

    @Init
    public void initUI() {
        dashboard = new Dashboard();
        
        // 1. Setup Top
        Top top = new Top();
        dashboard.setTop(top);

        // 2. Setup Left with Menus
        Left left = new Left();
        Menu menu = new Menu();
        MenuBar fileMenu = new MenuBar("File");
        fileMenu.add(new MenuItem("Open"));
        fileMenu.add(new Separator());
        fileMenu.add(new MenuItem("Save", Icon.SAVE));
        menu.add(fileMenu);
        left.add(menu); // Assuming Left has an add() method
        
        dashboard.setLeft(left);

        // 3. Setup Center and Footer
        dashboard.setCenter(new Center());
        dashboard.setFooter(new Footer());
    }

    @PostConstructor
    public void postConstructor() {
        System.out.println("Page created successfully.");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("Cleaning up resources before navigating away...");
    }

    @OnEvent(name = "submit")
    public void onSubmit() {
        System.out.println("Received submit event from Dashboard.");
    }
}
```
