# ProgressBar Component

The `ProgressBar` component is used to show the completion status of an operation or process.

## Basic Usage

```java
ProgressBar pb = new ProgressBar(50, 100);
center.add(pb);
```

## Features

- **Custom Colors**: Set the track and fill colors.
- **Indeterminate State**: Useful for processes where the duration is unknown.
- **Fluent API**: Easy setup with method chaining.

## Methods

| Method | Description |
| --- | --- |
| `setValue(double)` | Sets the current progress value. |
| `setMax(double)` | Sets the maximum progress value (default 100). |
| `setColor(String)` | Sets the progress bar fill color. |
| `setIndeterminate(boolean)` | Enables or disables an indeterminate animation. |

## Example with Indeterminate State

```java
ProgressBar pb = new ProgressBar();
pb.setIndeterminate(true);
pb.setColor("cyan");
center.add(pb);
```
