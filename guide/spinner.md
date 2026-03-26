# Spinner Component Guide

The `Spinner` component is a numerical input with increment and decrement buttons, allowing users to select a value within a specified range.

## Basic Usage

```java
Spinner spinner = new Spinner("quantity", 10)
    .setMin(0)
    .setMax(100)
    .setStep(1);
```

## Features

- **Minimum Value**: Prevents the counter from going below a certain threshold.
- **Maximum Value**: Prevents the counter from exceeding a certain threshold.
- **Increment (Step)**: Defines the amount to add or subtract on each click.
- **Hidden Input**: Automatically maintains a hidden input field for seamless form submission.

## Orientation and Styling

The `Spinner` is designed with a modern, glassmorphism-friendly aesthetic that integrates perfectly with the Jettra theme. It uses `var(--jettra-accent)` for the buttons and standard font colors for the text.

## API Summary

| Method | Description | Returns |
| --- | --- | --- |
| `Spinner(String name, double value)` | Constructor with name and initial value. | `Spinner` |
| `.setMin(double min)` | Sets the minimum allowed value. | `Spinner` |
| `.setMax(double max)` | Sets the maximum allowed value. | `Spinner` |
| `.setStep(double step)` | Sets the increment/decrement amount. | `Spinner` |

### Parameters
- `name`: The name attribute for the hidden input (used in forms).
- `value`: The initial value shown in the spinner.
