# JettraWUI Button Guide

The `Button` component is used to trigger actions in the UI. It supports a traditional setup as well as a more concise **Fluent API**.

## Button Creation

### 1. Traditional Way
Create the button and configure it using separate method calls:

```java
Button btn = new Button("Salvar");
btn.setId("saveBtn");
btn.addClass("custom-padding");
btn.setStyle("margin-top", "10px");
btn.addClickListener(() -> {
    System.out.println("Button Clicked!");
});
```

### 2. Fluent API Way
Configure the button in a single chain of method calls:

```java
Button btn = new Button("Eliminar")
    .id("delBtn")
    .addClass("custom-padding")
    .setStyle("margin-top", "10px")
    .addClickListener(() -> {
        System.out.println("Deleted!");
    });
```

## Specialized Styles

You can apply predefined thematic styles using fluent methods:

- `.primary()`: Core action style (Blue).
- `.secondary()`: Alternative action (Gray).
- `.help()`: Assistance/Knowledge (Purple).
- `.danger()`: Destructive action (Red).
- `.info()`: Information/Detail (Cyan).
- `.warning()`: Cautionary action (Yellow).

### Fluent Style Example

```java
Button deleteBtn = new Button("¡Confirmar!")
    .danger()
    .id("confirmDelete");

Button helpBtn = new Button("Ver Guía")
    .help();
```

## Summary of Fluent Methods

| Method | Description | Returns |
| --- | --- | --- |
| `.id(String)` | Sets the component ID. | `Button` |
| `.addClass(String)` | Appends a CSS class. | `Button` |
| `.setStyle(String, String)` | Sets an inline CSS style. | `Button` |
| `.addClickListener(ClickListener)` | Adds a server-side click event. | `Button` |
| `.primary()` | Applies the primary style. | `Button` |
| `.secondary()` | Applies the secondary style. | `Button` |
| `.help()` | Applies the help style. | `Button` |
| `.danger()` | Applies the danger style. | `Button` |
| `.info()` | Applies the info style. | `Button` |
| `.warning()` | Applies the warning style. | `Button` |
