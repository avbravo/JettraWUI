# Draw Component

The `Draw` component integrates Excalidraw, a powerful whiteboard and sketching tool, directly into your JettraWUI application. It provides users with a complete interface including a drawing palette, history state, and the ability to save or load their sketches.

**Component Info**
- **Class:** `io.jettra.wui.components.Draw`
- **Category:** Tools / Design

## Usage

Create a new instance of the `Draw` component by passing a unique DOM ID, width, and height. The width and height define the container size. Note that Excalidraw behaves best when using pixels or full viewport sizing.

```java
Draw designBoard = new Draw("main-diagram-board", 800, 600);
content.add(designBoard);
```

## Features Supported by Default

Because this component wraps Excalidraw, it natively includes:
- **Palette**: Pens, rectangles, ellipses, arrows, text, and library import capabilities.
- **Export & Import**: Native ability (via the built-in Excalidraw menu) to save to disk (`.excalidraw` or `.png`) and load existing `.excalidraw` documents.
- **Dark Mode**: Works beautifully on dark themes.

## Important Note regarding Dependencies
The `Draw` component relies on external CDNs (unpkg) to fetch React, ReactDOM, and `@excalidraw/excalidraw`. To work correctly in production, make sure the client's network can access `unpkg.com`. To host it entirely offline, you would need to download the bundled JS files and modify the `Draw` component to load them directly from `JettraServer` static resources.
