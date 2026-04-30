# Modal Component

The `Modal` component is a complex component that provides a dialog box/popup window that is displayed on top of the current page.

## Usage

```java
Modal modal = new Modal("myModal");
modal.add(new Header(3, "Title"));
modal.add(new Paragraph("This is a modal content."));
```

## Features

- **Responsive**: Adapts to different screen sizes.
- **Fluent API**: Supports method chaining.
- **Default Styling**: Comes with a premium dark theme by default.

## Default Properties
The `Modal` component comes with a set of default Java attributes that map to CSS properties. These can be configured using the Fluent API:

| Attribute | Default Value | Setter Method |
|-----------|---------------|---------------|
| `display` | `"none"` | `setDisplay(String)` |
| `position`| `"fixed"` | `setPosition(String)` |
| `top` | `"50%"` | `setTop(String)` |
| `left` | `"50%"` | `setLeft(String)` |
| `transform`| `"translate(-50%, -50%)"` | `setTransform(String)` |
| `padding` | `"30px"` | `setPadding(String)` |
| `backgroundColor`| `"#161b22"` | `setBackgroundColor(String)` |
| `border` | `"1px solid #30363d"` | `setBorder(String)` |
| `width` | `"fit-content"` | `setWidth(String)` |
| `height` | `"fit-content"` | `setHeight(String)` |
| `maxWidth`| `"90vw"` | `setMaxWidth(String)` |
| `maxHeight`| `"90vh"` | `setMaxHeight(String)` |
| `zIndex` | `"1000"` | `setZIndex(String)` |
| `minWidth`| `"300px"` | `setMinWidth(String)` |
| `boxShadow`| `Custom Glow` | `setBoxShadow(String)` |

## Fluent API Usage

The `Modal` component is designed for method chaining, making it easy to override defaults:

```java
Modal modal = new Modal("myModal")
    .setPadding("40px")
    .setBackgroundColor("#000")
    .setMaxWidth("800px")
    .setZIndex("2000")
    .add(new Header(3, "Title"))
    .add(new Paragraph("Content goes here..."))
    .add(new Button("Close").addClickListener(() -> { ... }));
```

## Fluent API Examples

The `Modal` component provides specific setter methods for all its major properties, supporting method chaining:

```java
modal.setId("deporteModal")
     .setPadding("40px")
     .setBackgroundColor("#000")
     .setBorder("2px solid cyan")
     .setZIndex("2000")
     .setMaxWidth("800px")
     .add(new Header(3, "Title"))
     .add(new Button("Save"));
```
