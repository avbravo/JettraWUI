# Script Component

The `Script` component injects pure JavaScript code into your Page. It is highly useful for client-side behaviors, such as defining modals, alert popups, or custom DOM manipulation hooks within a Jettra server-side rendered application.

## Usage Example

```java
import io.jettra.wui.components.Script;
import io.jettra.wui.components.Button;
import io.jettra.wui.core.Page;

public class AnalyticsPage extends Page {
    public AnalyticsPage() {
        super("Analytics");
        
        Button btn = new Button("Click Me");
        btn.setProperty("onclick", "sayHello()");
        add(btn);

        // Define your JS
        String js = "function sayHello() { alert('Hello from Jettra!'); }";
        
        // Inject script block
        Script myScript = new Script(js);
        add(myScript);
    }
}
```
