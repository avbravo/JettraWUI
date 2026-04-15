# Grid

The `Grid` component provides a CSS Grid layout container for arranging components in a multi-column responsive layout.

## Usage

```java
Grid myGrid = new Grid(3, "20px"); // 3 columns, 20px gap

myGrid.add(new Card().setTitle("Card 1"));
myGrid.add(new Card().setTitle("Card 2"));
myGrid.add(new Card().setTitle("Card 3"));
```

## Constructor
- `Grid(int columns, String gap)`: Initializes a grid layout.
    - `columns`: Number of columns (repeated using `1fr`).
    - `gap`: The CSS gap value between grid items.

## Features
- Extends `Div`
- Uses `display: grid`
- Responsive and flexible
- Fluent API support for all methods

## Customizing Layout
You can manually override grid properties using `setStyle`:

```java
grid.setStyle("grid-template-columns", "1fr 2fr 1fr");
```
