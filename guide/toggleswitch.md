# ToggleSwitch Component

The `ToggleSwitch` component is a modern, glassmorphism-styled switch used for toggling boolean states (On/Off). It replaces standard checkboxes with a more visually appealing sliding switch that can display custom text or icons for each state.

## Usage

### Basic ToggleSwitch

```java
import io.jettra.wui.components.ToggleSwitch;

ToggleSwitch toggle = new ToggleSwitch("my-toggle", "myToggleName", "true");
toggle.setLabels("ON", "OFF");

// Set the initial state
toggle.setProperty("checked", "checked");

// Add an event listener
toggle.setProperty("onchange", "console.log('Toggled!', this.checked)");
```

### ToggleSwitch with Icons

You can use standard emojis or SVG strings as labels for the states.

```java
ToggleSwitch themeToggle = new ToggleSwitch("theme-toggle", "theme", "dark");
themeToggle.setLabels("🌙", "☀️");
```

## Internal Structure

The `ToggleSwitch` works by wrapping a hidden standard HTML `<input type="checkbox">` inside a `<label>`. This means that:
- It maintains full accessibility and form submission compatibility.
- Properties like `onchange`, `checked`, and `disabled` are automatically applied to the internal `input` element instead of the label, ensuring that events like `this.checked` work intuitively inside inline JavaScript.

## Styling

The component uses robust CSS defined in `JettraTheme.java`. It automatically inherits the `var(--jettra-accent)` color for its active state and uses `var(--jettra-text)` for its labels, fitting seamlessly into the 3D-glassmorphism aesthetic.
