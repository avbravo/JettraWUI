# FormGroup

The `FormGroup` component is a specialized `Div` designed to wrap form elements (usually a `Label` and an input component like `TextBox`). It automatically adds the `form-group` CSS class for consistent styling.

## Usage

```java
FormGroup groupNombre = new FormGroup();
groupNombre.add(new Label("nombre", "Nombre:"));
TextBox inputNombre = new TextBox("text", "nombre")
    .setId("personaNombre")
    .addClass("j-input");
groupNombre.add(inputNombre);
```

## Features
- Extends `Div`
- Automatically adds `form-group` class
- Supports Fluent API for all standard `UIComponent` properties
- Simplifies form construction code

## Inherited Methods
Since it extends `Div`, it supports all standard `UIComponent` methods:
- `setId(String id)`
- `setStyle(String key, String value)`
- `addClass(String className)`
- `setProperty(String key, String value)`
- `add(UIComponent child)`
- etc.
