# Draw Component

The `Draw` component provides an advanced, interactive sketching and diagramming canvas based on the Excalidraw engine. It is designed for creating flowcharts, wireframes, and freehand drawings with a modern 3D aesthetic.

## Features

- **Multi-tool Support**: Rectangle, Diamond, Ellipse, Arrow, Line, Draw (Freehand), and Text.
- **Excalidraw API Integration**: Full access to the underlying canvas state and elements.
- **Dynamic Palette**: Support for drag-and-drop tool selection and customizable toolsets.
- **Persistence**: Save and load diagrams using the `.jdraw` (JSON) format.
- **Responsiveness**: Auto-scaling canvas that fits within JettraWUI layouts.

## Basic Usage

```java
import io.jettra.wui.components.Draw;

// Create a new drawing canvas
Draw sketchpad = new Draw("my-sketch", 1200, 800);
sketchpad.setStyle("width", "100%").setStyle("height", "500px");

// Add to center
center.add(sketchpad);
```

## Integration with JettraWUI Palette

To allow drag-and-drop interaction from a custom palette, use the `handleToolDrop` Javascript helper:

```javascript
function handleToolDrop(event) {
  event.preventDefault();
  const toolType = event.dataTransfer.getData('tool');
  const api = window['excalidrawAPI_my-sketch'];
  if(api) {
    api.updateScene({ appState: { activeTool: { type: toolType } } });
  }
}
```

## Save and Load Logic

The component interacts with the `excalidrawAPI_[id]` global object:

```javascript
// Save diagram
const elements = api.getSceneElements();
const data = JSON.stringify({ elements });
// ... save to file ...

// Load diagram
api.updateScene({ elements: jsonData.elements });
```

## Styling

The `Draw` component is fully compatible with JettraWUI themes. It supports glassmorphism effects and 3D transitions when wrapped in a `.j-card` or `.j-3d-effect` container.
