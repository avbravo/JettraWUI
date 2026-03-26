# JettraWUI Event Handling Guide

## Introduction
JettraWUI provides a declarative, component-based event handling system. This allows developers to attach logic directly to UI components using lambdas, similar to Vaadin or JavaFX, avoiding manual request parsing.

## Basic Usage

### Click Listeners
You can add a `ClickListener` to any `Button` or `UIComponent`.

```java
Button myButton = new Button("Click Me");
myButton.addClickListener(() -> {
    System.out.println("Button clicked!");
    // Update ViewModel or add scripts
    this.add(new Script("alert('Clicked!');"));
});
```

### Component IDs
For events to work reliably in loops (like tables), it is recommended to set a stable ID:

```java
editBtn.setId("edit-" + entity.getId());
```

## Page Lifecycle
To use events, your page should extend `io.jettra.wui.core.Page` (or a subclass like `DashboardBasePage`) and use the following hooks:

- `onInit(Map<String, String> params)`: Build your component tree and attach listeners here.
- `onGet(Map<String, String> params)`: Logic for GET requests after listeners have fired.
- `onPost(Map<String, String> params)`: Logic for POST requests (form submissions).

## Example: CRUD Table
In an event-driven page, an "Edit" button in a table would look like this:

```java
Button editBtn = new Button("Edit");
editBtn.addClickListener(() -> {
    this.viewModel.setData(item.getData());
    this.add(new Script("showModal();"));
});
```

The `Page` class automatically handles the dispatching of these events before calling `render()`.
