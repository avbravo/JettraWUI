# JettraWUI SelectOne Guide

The `SelectOne` component corresponds to a standard HTML `<select>` element. It represents a drop-down list of options, allowing the user to select exactly one item from the list.

## 1. Creating a SelectOne

To create a dropdown, initialize `SelectOne` with a unique name attribute, which serves as its identifier when processing forms.

```java
SelectOne countrySelect = new SelectOne("country");
```

## 2. Adding Options

Use the `addOption(String value, String label)` method to populate the select element with choices.

```java
countrySelect.addOption("", "Select a country...");
countrySelect.addOption("US", "United States");
countrySelect.addOption("ES", "Spain");
countrySelect.addOption("MX", "Mexico");
```

## 3. Event Handling

You can add standard properties like `onchange` to trigger JavaScript events when the user selects a different option.

```java
countrySelect.setProperty("onchange", "console.log('Selected: ' + this.value)");
```

### Fluent API Example

```java
SelectOne themeSelect = new SelectOne("theme")
    .addOption("light", "Light Theme")
    .addOption("dark", "Dark Theme")
    .addOption("system", "System Default");

themeSelect.setStyle("width", "200px");
```

## 4. Allowing Users to Add Items Dynamically

You can allow users to insert custom values into the `SelectOne` at runtime by setting the `allowAddItem` property to `true` (it is `false` by default).

```java
SelectOne customSelect = new SelectOne("custom")
    .addOption("1", "Option 1")
    .addOption("2", "Option 2")
    .setAllowAddItem(true);
```

When this property is enabled, an "Add item..." option is automatically appended to the list. If the user selects it, they will be prompted to enter a new value, which will be immediately added and selected in the drop-down.

## 5. Styling
By default, `SelectOne` applies the classes `j-select j-component` which styles the dropdown with JettraWUI's modern theme (dark backgrounds, borders, and typography). You can use `.setStyle("width", "100%")` to make it span a container.
