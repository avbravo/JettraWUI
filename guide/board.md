# Board Component

The `Board` component provides a structural base for organized board-style layouts like Kanban, Scrum, or multi-column dashboards.

## Overview
- **Structure**: A header followed by a configurable number of columns.
- **Layout**: Uses a responsive grid for columns.
- **Purpose**: Managing and visualizing workflows or categorized data.

## Usage

### Simple Board
To create a basic board with 3 columns:

```java
Board board = new Board("Development Workflow", 3);
board.addComponent(0, new Header(3, "To Do"));
board.addComponent(1, new Header(3, "In Progress"));
board.addComponent(2, new Header(3, "Done"));
```

### Adding Components to Columns
You can add any `UIComponent` to a specific column index (0-indexed).

```java
board.addComponent(0, new Card("Develop Board Component", "Implement header and grid columns."));
board.addComponent(1, new Card("Document Board", "Create guide and examples."));
```

## Styling
The `Board` component includes several CSS classes for customization:
- `.j-board`: The main container.
- `.j-board-column`: Individual column containers.

## Example Layout
The default style provides a modern dark theme with:
- `backdrop-filter: blur(10px)`
- `background: rgba(15, 23, 41, 0.4)`
- `border-radius: 20px`

## Interactive Features
For interactive boards (like Kanban with drag-and-drop), you should use the `Board` component in combination with `Script` to handle the `ondragover` and `ondrop` events on the column containers.
