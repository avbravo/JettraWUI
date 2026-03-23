# Paragraph Component

The `Paragraph` (`<p>`) component is a wrapper for rendering block-level text content across the application. It applies default font styles to ensure legibility across the standard Jettra aesthetics.

## Usage Example

```java
import io.jettra.wui.components.Paragraph;
import io.jettra.wui.core.Page;

public class OverviewPage extends Page {
    public OverviewPage() {
        super("Overview");
        
        Paragraph p1 = new Paragraph("Welcome to the platform overview. Here we discuss all related metrics.");
        Paragraph p2 = new Paragraph("Feel free to browse all directories.");
        
        add(p1);
        add(p2);
    }
}
```
