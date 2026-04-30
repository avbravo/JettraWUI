# JettraWUI TextBox Guide

The `TextBox` component is used for single-line text input.

## TextBox Creation

### Fluent API Way
Configure the text box in a single chain of method calls:

```java
TextBox input = new TextBox("text", "username")
    .setPlaceholder("Enter username")
    .setValue("guest")
    .setReadonly(false)
    .setDisplay("block");
```

## Summary of Fluent Methods

| Method | Description | Returns |
| --- | --- | --- |
| `.setId(String)` | Sets the component ID. | `TextBox` |
| `.setPlaceholder(String)` | Sets the placeholder text. | `TextBox` |
| `.setValue(String)` | Sets the initial value. | `TextBox` |
| `.setReadonly(boolean)` | Enables or disables read-only mode. | `TextBox` |
| `.setDisplay(String)` | Sets the display mode (none, block). | `TextBox` |
| `.addClass(String)` | Appends a CSS class. | `TextBox` |
| `.setStyle(String, String)` | Sets an inline CSS style. | `TextBox` |

> [!NOTE]
> All `TextBox` components include the `"j-input"` class by default.
