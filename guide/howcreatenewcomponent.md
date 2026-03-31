# How to Create a New Component in JettraWUI

Follow these step-by-step instructions to create, document, and register a new UI component in the JettraWUI framework.

## 1. Plan the Component
- **Purpose**: Define what the component should do (e.g., provide a structural base, an input field, or a complex visual).
- **Structure**: Decide if it's a simple HTML tag or a complex assembly of multiple components.
- **Styling**: All components should follow the Jettra design system (modern, dark-themed, and interactive).

## 2. Implement the Java Class
Create the Java file in the appropriate package under `io.jettra.wui.complex` or `io.jettra.wui.components`.

### Key Elements to Include:
- **Base Class**: Inherit from `UIComponent`, `Div`, or a similar existing component.
- **Constructor**: Initialize properties, classes, and default styles.
- **Methods**: Add functionality to interact with the component (e.g., adding sub-components, setting values).

### Example Structure:
```java
package io.jettra.wui.complex;

import io.jettra.wui.components.Div;
import io.jettra.wui.core.UIComponent;

public class MyNewComponent extends Div {
    public MyNewComponent() {
        super();
        this.addClass("j-my-new-component");
        this.setStyle("background", "rgba(15, 23, 42, 0.4)");
        // Add more initialization here
    }
}
```

## 3. Register in WebDesigner
Ensure the component is available in the Visual Designer palette.

1.  Open `WebDesignerPage.java` in the `JettraWebExample` project.
2.  Locate the `createCategorizedPalette()` method.
3.  Add your new component name to the appropriate category.

```java
// Example in WebDesignerPage.java
addPaletteCategory(palette, "Custom Category", new String[]{"MyNewComponent", "AnotherComponent"});
```

4.  Update the JavaScript logic in `WebDesignerPage.java` (`setupDesignerAssets` method) to provide a visual representation of your component on the canvas.

```javascript
// Inside addComponentToCanvas function
case 'MyNewComponent':
    content = '<div class="my-component-preview">Custom Component View</div>';
    break;
```

## 4. Documentation
Create a Markdown file under the `/guide/` directory of the `JettraWUI` project.

- **Title**: Use the component name as the main header.
- **Overview**: Describe the component's functionality.
- **Usage Example**: Provide code snippets for Java implementation.
- **Style Classes**: Document the class names and CSS properties used.

## 5. Examples
Create a demo page in the `JettraWebExample` project.

1.  Create a new page class: `MyNewComponentPage.java` inside `com.jettra.example.pages`.
2.  Register the page in `JettraWebExample` main class (e.g., `JettraWebExample.java`).
3.  Add a link to the navigation menu in `DashboardBasePage.java`.

Following these steps ensures that new components are fully integrated and easy to use.
