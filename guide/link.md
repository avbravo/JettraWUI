# Link Component

The `Link` component creates a standard anchor tag `<a>` configured with Jettra's default hyperlink colors and decoration settings. It allows redirection without reloading the entire SPA structure.

## Usage Example

```java
import io.jettra.wui.components.Link;
import io.jettra.wui.core.Page;

public class NavigationPage extends Page {
    public NavigationPage() {
        super("Nav");
        
        Link l1 = new Link("/dashboard", "Go to Dashboard");
        Link l2 = new Link("https://google.com", "External Search");
        
        add(l1);
        add(l2);
    }
}
```
