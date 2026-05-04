# TabView Component Guide

The `TabView` component allows you to organize content into multiple tabs. It supports different orientations for the tab list.

## Basic Usage

```java
TabView tabView = new TabView("My TabView")
    .addTab("Tab 1", new Paragraph("Content 1"))
    .addTab("Tab 2", new Paragraph("Content 2"))
    .addTab("Tab 3", new Button("Click me").primary());
```

## Orientations

You can change where the tabs are positioned relative to the content area using `setOrientation()`:

- `Orientation.TOP` (Default): Tabs at the top.
- `Orientation.BOTTOM`: Tabs at the bottom.
- `Orientation.LEFT`: Tabs on the left (sidebar style).
- `Orientation.RIGHT`: Tabs on the right.

### Example with Orientation

```java
TabView leftTabs = new TabView("Configuration")
    .setOrientation(TabView.Orientation.LEFT)
    .addTab("General", new Div().add(new Label("Name", "App Name")))
    .addTab("Security", new Div().add(new Label("Password", "******")));
```

## Styling

The `TabView` follows the Jettra theme automatically, using glassmorphism and accent colors for the active tab. Each tab button has the `.j-tab-btn` class, and the active tab has the `.active` class.

## API Summary

| Method | Description | Returns |
| --- | --- | --- |
| `TabView(String title)` | Constructor with a main title. | `TabView` |
| `.addTab(String title, UIComponent content)` | Adds a new tab. | `TabView` |
| `.setOrientation(Orientation orientation)` | Sets the tab list position. | `TabView` |

### Orientations Enum
`TabView.Orientation.TOP`, `TabView.Orientation.BOTTOM`, `TabView.Orientation.LEFT`, `TabView.Orientation.RIGHT`.
