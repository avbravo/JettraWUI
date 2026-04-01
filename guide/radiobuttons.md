# JettraWUI RadioButton and RadioGroupButton Guide

The `RadioButton` component is used when you want the user to select only one option from a predefined set of mutually exclusive options. To manage the mutual exclusivity, you should wrap your `RadioButton` components inside a `RadioGroupButton`.

## 1. Creating a Radio Group

The `RadioGroupButton` creates a logical and visual grouping container for your radio buttons. It ensures all child radio buttons share the same `name` attribute, so the browser handles the exclusive selection automatically.

```java
// Create the group
RadioGroupButton genderGroup = new RadioGroupButton("genderGroup");
```

## 2. Adding Radio Buttons

You can create `RadioButton` components and add them to the group. The group will automatically assign its name to each radio button added.

```java
// Create individual radio buttons
RadioButton optMale = new RadioButton("opt_male", "male");
RadioButton optFemale = new RadioButton("opt_female", "female");

// Create labels for them
Label labelMale = new Label("opt_male", "Male");
Label labelFemale = new Label("opt_female", "Female");

// Wrap in a layout and add to the group
Div maleRow = new Div().setStyle("display", "flex").add(optMale).add(labelMale);
Div femaleRow = new Div().setStyle("display", "flex").add(optFemale).add(labelFemale);

genderGroup.addRadio(maleRow);
genderGroup.addRadio(femaleRow);
```

### Fluent API Equivalents
Instead of setting properties line by line, you can configure your components during creation. The `RadioGroupButton` provides the `addRadio` method to easily enclose the components.

```java
RadioGroupButton group = new RadioGroupButton("paymentMethod")
    .addRadio(new Div().setStyle("display", "flex")
        .add(new RadioButton("pm_card", "card"))
        .add(new Label("pm_card", "Credit Card")))
    .addRadio(new Div().setStyle("display", "flex")
        .add(new RadioButton("pm_paypal", "paypal"))
        .add(new Label("pm_paypal", "PayPal")));
```

## 3. Styling
The `RadioGroupButton` renders as a `div` with `display: flex` and `flex-direction: column` by default. You can change this to `row` if you prefer horizontal alignment:

```java
genderGroup.setStyle("flex-direction", "row");
```

Radio buttons use the CSS variable `--jettra-accent` for their accent color, giving them a modern glow that matches the rest of your Jettra application.
