# JettraWUI: Complete Component Reference & Architecture

## 1. Framework Overview
JettraWUI is a Java-to-HTML UI framework designed for developers who want to build futuristic, high-performance web applications without leaving the Java ecosystem. It emphasizes **3D aesthetics, Glassmorphism, and Neumorphism**, providing a rich visual experience out of the box.

### Vision
- **Zero HTML/CSS/JS**: Build complex interfaces purely in Java.
- **Futuristic Design**: Pre-integrated 3D and blur effects.
- **Fluent API**: Highly productive code structure using method chaining.
- **Event-Driven**: Seamless server-client communication.

---

## 2. Architecture

### UIComponent
The base class for everything. It manages:
- **Properties**: HTML attributes.
- **Styles**: Inline CSS rules.
- **Children**: Nested `UIComponent` objects.
- **Events**: Click and custom events.

### Page
The entry point of any JettraWUI application. It handles the `<html>`, `<head>`, and `<body>` tags, integrates the `JettraTheme`, and orchestrates the rendering of the component tree.

### Event System
JettraWUI uses a JSON-based event communication.
- `@OnEvent(name="...")`: Annotation to handle client-side events in Java.
- `jtFire`: JavaScript bridge to trigger server-side updates.

---

## 3. Basic Components

### Button
Standard button element with thematic support.
```java
Button btn = new Button("Save")
    .primary()
    .setId("saveBtn")
    .addClickListener(() -> System.out.println("Saved!"));
```

### TextBox
Input field for text, passwords, or emails.
```java
TextBox input = new TextBox("text", "username")
    .setPlaceholder("Enter username")
    .addClass("j-input");
```

### Label
Associates text with a form element.
```java
Label lbl = new Label("username", "Username");
```

---

## 4. Layout Components

### Grid
Creates a CSS Grid container.
```java
Grid layout = new Grid(3, "20px");
layout.add(card1).add(card2).add(card3);
```

### Card
A visually rich container with title, subtitle, and content.
```java
Card card = new Card()
    .setTitle("Profile")
    .setImageUrl("profile.png")
    .add(new Button("Edit"));
```

### FormGroup (New)
A specialized `Div` that wraps form elements with the `form-group` class.
```java
FormGroup group = new FormGroup()
    .add(new Label("email", "Email"))
    .add(new TextBox("email", "email"));
```

---

## 5. Complex & Utility Components

### Datatable
Advanced component for displaying tabular data with automatic rendering of rows.
```java
Datatable table = new Datatable("usersTable");
table.addColumn("ID").addColumn("Name").addColumn("Status");
// ... add rows
```

### Alert & Notification
Used for user feedback.
```java
Alert alert = new Alert().setType("error").showMessage("Access Denied");
Notification.show("Success", "Record updated");
```

### BarCode & QR
Generates dynamic codes.
```java
QR code = new QR("https://jettra.io").setSize(200);
BarCode bc = new BarCode("12345").setFormat("CODE128");
```

---

## 6. Dashboard Architecture
The Dashboard is a high-level layout component that organizes the app into four main areas:
- **Top**: Header, user info, global actions.
- **Left**: Main navigation menu.
- **Center**: Primary content area (forms, tables, dashboards).
- **Footer**: Meta-info and status.

### Example Dashboard Setup
```java
Dashboard dash = new Dashboard();
dash.getTop().add(new Label("App Title"));
dash.getLeft().add(new Menu().add(new MenuItem("Home")));
dash.getCenter().add(new WelcomePage());
```

---

## 7. Fluent API Reference
All JettraWUI components (extending `UIComponent`) support the following chaining methods:

| Method | Description |
| --- | --- |
| `setId(String)` | Sets the HTML `id`. |
| `setProperty(String, String)` | Adds an HTML attribute. |
| `setStyle(String, String)` | Adds an inline CSS rule. |
| `addClass(String)` | Adds a CSS class. |
| `setContent(String)` | Sets the inner HTML of the component. |
| `add(UIComponent)` | Adds a child component. |
| `setUpdate(String)` | Defines which IDs to refresh on an event. |

---

*© 2026 JettraStack - All rights reserved.*
