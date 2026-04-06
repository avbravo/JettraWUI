# Loading Component

The `Loading` component provides an animated generic spinner indicating that a background process, request, or rendering task is occurring.

## Basic Usage

```java
import io.jettra.wui.components.Loading;

Loading loader = new Loading();
container.add(loader);
```

## Sizes
You can specify different sizes for the `Loading` component using the `Loading.Size` enum.

```java
Loading small = new Loading().setSize(Loading.Size.SM);
Loading medium = new Loading().setSize(Loading.Size.MD); // Default
Loading large = new Loading().setSize(Loading.Size.LG);
Loading extralarge = new Loading().setSize(Loading.Size.XL);
```

## Custom Colors

The component will automatically inherit `var(--jettra-accent)`, but you can explicitly specify the color dynamically:

```java
Loading customLoader = new Loading()
    .setColor("#FF5733")
    .setSize(Loading.Size.LG);
```

## API

- `setSize(Loading.Size size)`: Sets the dimension of the loader. Valid values: `SM, MD, LG, XL`.
- `setColor(String color)`: Overrides the SVG default color (accepts HEX, RGB, or CSS variables).
