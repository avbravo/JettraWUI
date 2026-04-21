# JettraWUI: Complete Component Reference & Architecture

## 1. Framework Overview
JettraWUI is a Java-to-HTML UI framework designed for developers who want to build futuristic, high-performance web applications without leaving the Java ecosystem. It emphasizes **3D aesthetics, Glassmorphism, and Neumorphism**, providing a rich visual experience out of the box.

### Vision
- **Zero HTML/CSS/JS**: Build complex interfaces purely in Java.
- **Futuristic Design**: Pre-integrated 3D and blur effects.
- **Fluent API**: Highly productive code structure using method chaining.
- **Event-Driven**: Seamless server-client communication.

---

## 2. Architecture & Core Concepts

### UIComponent
The base class for everything. It manages properties, styles, children, and events.
[Read more about Architecture](../guide/arquitectura.md)

### Page
The entry point of any JettraWUI application. It handles the `<html>`, `<head>`, and `<body>` tags.

### Event System
JettraWUI uses a JSON-based event communication.
[Read more about Events](../guide/events.md)

### Theme System (New)
Modern themes including 3D, Material, Plain, and TailwindCSS support.
[Read more about Themes](../guide/temas.md)

---

## 3. Component Library Index

### Form & Input Components
- **[Button](../guide/button.md)** / **[Buttons](../guide/buttons.md)**: Standard and group buttons.
- **[TextBox](../guide/textarea.md)**: Input field for text.
- **[TextArea](../guide/textarea.md)**: Multi-line text input.
- **[SelectOne](../guide/selectone.md)** / **[SelectMany](../guide/selectmany.md)**: Dropdown and multi-selection lists.
- **[SelectOneIcon](../guide/selectoneicon.md)**: Selection with icons and "Add Item" support.
- **[RadioButtons](../guide/radiobuttons.md)**: Group of radio selections.
- **[CheckboxGroup](../guide/checkboxgroup.md)**: Group of checkboxes.
- **[ToggleSwitch](../guide/toggleswitch.md)**: Modern switch toggle.
- **[DatePicker](../guide/datepicker.md)** / **[Time](../guide/time.md)**: Date and time selection.
- **[OtpValidator](../guide/otpvalidator.md)**: Secure OTP input.

### Layout & Containers
- **[Grid](../guide/grid.md)**: Flexible CSS Grid layout.
- **[Card](../guide/card.md)**: Rich container with 3D/Glassmorphism effects.
- **[Panel](../guide/panel.md)**: Collapsible and structured panel.
- **[TabView](../guide/tabview.md)**: Organized tabbed content.
- **[FormGroup](../guide/formgroup.md)**: Form element wrapper.
- **[Board](../guide/board.md)**: Kanban-style board container.

### Complex & Advanced Components
- **[Datatable](../guide/datatable.md)**: Powerful data grid with sorting and filtering.
- **[Tree](../guide/tree.md)**: Hierarchical tree structure.
- **[Kanban](../guide/kanban.md)**: Visual task management.
- **[Schedule](../guide/schedule.md)** / **[ScheduleControl](../guide/schedulecontrol.md)**: Calendar and event scheduling.
- **[Organigram](../guide/organigram.md)**: Organizational charts.
- **[Timeline](../guide/timeline.md)**: Sequential event display.

### Media & Visualization
- **[Charts (Bar, Pie, Line, Radar, Doughnut)](../guide/charstbar.md)**: Data visualization powered by Chart.js.
- **[Map](../guide/map.md)**: Interactive map integration.
- **[PdfViewer](../guide/pdfviewer.md)**: Embedded PDF viewer.
- **[ViewMedia](../guide/viewmedia.md)**: Image and video gallery.
- **[Avatar](../guide/avatar.md)**: User profile representation.

### Utilities & Feedback
- **[Alert](../guide/uso_componente_alert.md)** / **[Notification](../guide/notification.md)**: User feedback systems.
- **[ProgressBar](../guide/progressbar.md)**: Visual progress indication.
- **[Loading](../guide/loading.md)**: Spinner and overlay indicators.
- **[BarCode](../guide/barcode.md)** / **[QR](../guide/qr.md)**: Dynamic code generation.
- **[FileUpload](../guide/fileupload.md)** / **[Downloader](../guide/downloader.md)**: Asset management.
- **[Captcha](../guide/catcha.md)**: Security verification.
- **[CreditCard](../guide/creditcard.md)**: Specialized payment input.

---

## 4. Advanced Features

### MVC Integration
How to use JettraWUI in a Model-View-Controller architecture.
[Read more about MVC](../guide/mvc.md)

### Page Synchronization
Ensuring real-time updates between client and server.
[Read more about JettraPage Sincronized](../guide/jettrapagesincronized.md)

### Drawing & Graphics
Canvas-based drawing capabilities.
[Read more about Draw](../guide/draw.md)

---

## 5. Development Guide
- **[How to create a new component](../guide/howcreatenewcomponent.md)**
- **[Validations](../guide/validations.md)**
- **[Icons Reference](../guide/icons.md)**

---

*© 2026 JettraStack - All rights reserved.*

