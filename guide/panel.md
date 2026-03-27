# Panel Component

The `Panel` component is a layout container that allows you to organize other components into columns using a CSS Grid layout.

## Usage

```java
Panel myPanel = new Panel(2); // Create a 2-column panel
myPanel.add(0, new Label("Name:")); // Add to first column
myPanel.add(1, new TextBox("name", "Enter name")); // Add to second column
```

## Constructors

- `Panel(int columnCount)`: Creates a panel with the specified number of columns.

## Methods

- `add(int columnIndex, UIComponent component)`: Adds a component to the specified column (0-indexed).
- `add(UIComponent component)`: Adds a component to the first column (default behavior).

## CSS Classes
- `.j-panel`: Main container class.
- `.j-panel-column`: Column container class.
