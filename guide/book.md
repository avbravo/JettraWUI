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
[Read more about Architecture](arquitectura.md)

### Page
The entry point of any JettraWUI application. It handles the `<html>`, `<head>`, and `<body>` tags.

### Event System
JettraWUI uses a JSON-based event communication.
[Read more about Events](events.md)

### Theme System (New)
Modern themes including 3D, Material, Plain, and TailwindCSS support.
[Read more about Themes](temas.md)

---

## 3. Component Library Index

### Form & Input Components
- **[Button](button.md)** / **[Buttons](buttons.md)**: Standard and group buttons.
- **[TextBox](textarea.md)**: Input field for text.
- **[TextArea](textarea.md)**: Multi-line text input.
- **[SelectOne](selectone.md)** / **[SelectMany](selectmany.md)**: Dropdown and multi-selection lists.
- **[SelectOneIcon](selectoneicon.md)**: Selection with icons and "Add Item" support.
- **[RadioButtons](radiobuttons.md)**: Group of radio selections.
- **[CheckboxGroup](checkboxgroup.md)**: Group of checkboxes.
- **[ToggleSwitch](toggleswitch.md)**: Modern switch toggle.
- **[DatePicker](datepicker.md)** / **[Time](time.md)**: Date and time selection.
- **[OtpValidator](otpvalidator.md)**: Secure OTP input.

### Layout & Containers
- **[Grid](grid.md)**: Flexible CSS Grid layout.
- **[Card](card.md)**: Rich container with 3D/Glassmorphism effects.
- **[Panel](panel.md)**: Collapsible and structured panel.
- **[TabView](tabview.md)**: Organized tabbed content.
- **[FormGroup](formgroup.md)**: Form element wrapper.
- **[Board](board.md)**: Kanban-style board container.

### Complex & Advanced Components
- **[Datatable](datatable.md)**: Powerful data grid with sorting and filtering.
- **[Tree](tree.md)**: Hierarchical tree structure.
- **[Kanban](kanban.md)**: Visual task management.
- **[Schedule](schedule.md)** / **[ScheduleControl](schedulecontrol.md)**: Calendar and event scheduling.
- **[Organigram](organigram.md)**: Organizational charts.
- **[Timeline](timeline.md)**: Sequential event display.

### Media & Visualization
- **[Charts (Bar, Pie, Line, Radar, Doughnut)](charstbar.md)**: Data visualization powered by Chart.js.
- **[Map](map.md)**: Interactive map integration.
- **[PdfViewer](pdfviewer.md)**: Embedded PDF viewer.
- **[ViewMedia](viewmedia.md)**: Image and video gallery.
- **[Avatar](avatar.md)**: User profile representation.

### Utilities & Feedback
- **[Alert](uso_componente_alert.md)** / **[Notification](notification.md)**: User feedback systems.
- **[ProgressBar](progressbar.md)**: Visual progress indication.
- **[Loading](loading.md)**: Spinner and overlay indicators.
- **[BarCode](barcode.md)** / **[QR](qr.md)**: Dynamic code generation.
- **[FileUpload](fileupload.md)** / **[Downloader](downloader.md)**: Asset management.
- **[Captcha](catcha.md)**: Security verification.
- **[CreditCard](creditcard.md)**: Specialized payment input.

---

## 4. Advanced Features

### MVC Integration
How to use JettraWUI in a Model-View-Controller architecture.
[Read more about MVC](mvc.md)

### Page Synchronization
Ensuring real-time updates between client and server.
[Read more about JettraPage Sincronized](jettrapagesincronized.md)

### Drawing & Graphics
Canvas-based drawing capabilities.
[Read more about Draw](draw.md)

---

## 5. Development Guide
- **[How to create a new component](howcreatenewcomponent.md)**
- **[Validations](validations.md)**
- **[Icons Reference](icons.md)**

---

*© 2026 JettraStack - All rights reserved.*
