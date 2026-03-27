# TextArea Component

The `TextArea` component is a multi-line text input field, extending `UIComponent` with `textarea` tag.

## Usage

```java
import io.jettra.wui.components.TextArea;

// Create a TextArea with ID "description" and initial content
TextArea description = new TextArea("description", "Enter your text here...");
description.setStyle("height", "150px");
```

## Features

- **Multi-line Input**: Ideal for comments, descriptions, or code snippets.
- **Styling**: Supports standard `setStyle` and `addClass` methods.
- **Value Management**: Use `setValue(String)` to programmatically change content.
- **Glassmorphism Integration**: Inherits `.j-input` styles for a premium look in Jettra environments.

## Example in a Form

```java
Form feedbackForm = new Form();
feedbackForm.add(new Label("cmt", "Comments:"));
feedbackForm.add(new TextArea("comments", "").setStyle("margin-top", "10px"));
feedbackForm.add(new Button("Submit"));
```
