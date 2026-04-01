# JettraWUI SelectOneIcon Guide

The `SelectOneIcon` component creates a custom selector dialog tailored for displaying a list of options with accompanying icons (such as SVG files, material icons, or flags). Since standard browser `<select>` boxes do not easily support images or SVGs, this component provides a rich alternative.

> **Note:** The dropdown options render as a custom DOM layout (using Divs) rather than native `<option>` tags, allowing them to intercept clicks, inject innerHTML (SVGs), and toggle visibility dynamically.

## 1. Creating a SelectOneIcon

Initialize the component, providing its field `name` and its default label:

```java
SelectOneIcon flagSelect = new SelectOneIcon("locale", "Language");
```

## 2. Setting Options

Use the `addOption(String value, String label, String iconHtml)` method to add a new option. The `iconHtml` parameter expects text or an HTML fragment (e.g., `<svg>...</svg>`).

```java
flagSelect.addOption("en", "English", "🇬🇧");
flagSelect.addOption("es", "Espanol", "🇪🇸");
flagSelect.addOption("it", "Italiano", "🇮🇹");
```

## 3. Controlling Defaults

You can update the default option presented to the user with `setSelectedValue(value, label, iconHtml)`:

```java
flagSelect.setSelectedValue("es", "Espanol", "🇪🇸");
```

## 4. Behavior with Labels
You can optionally hide the selected label and only display the icon inside the main trigger (for compact interfaces or headers). Set `.setShowLabelInTrigger(false);` during initialization:

```java
SelectOneIcon compactSelect = new SelectOneIcon("theme", "Theme")
    .setShowLabelInTrigger(false)
    .addOption("dark", "Dark", "🌙")
    .addOption("light", "Light", "☀️");
```

## 5. Event Handling
The `SelectOneIcon` handles options locally using its internal script logic (`jettraSelectOption()`), which assigns the selected value to a hidden input holding the `name` you provided upon creation.

If you are using special names like `lang` or `theme`, the default script may execute page reloads or theme changes automatically.
You can always manually inspect `document.getElementById('your_name_here').value` for the active field selection before submitting it via an AJAX request.
