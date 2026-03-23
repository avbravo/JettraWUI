# Style Component

The `Style` component allows you to inject raw CSS rules directly into the DOM inside a `<style>` tag. This is useful for component-scoped styles or override declarations without needing external stylesheets.

## Usage Example

```java
import io.jettra.wui.components.Style;
import io.jettra.wui.core.Page;

public class MyPage extends Page {
    public MyPage() {
        super("My Page");
        
        // Define your CSS strings
        String css = ".my-text { color: red; font-weight: bold; }";
        
        // Add to the page
        Style myStyle = new Style(css);
        add(myStyle);
    }
}
```
