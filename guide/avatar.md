# Avatar Component Guide

The `Avatar` component is a versatile UI element used to represent users or entities with images, labels, or icons. It is part of the `io.jettra.wui.complex` package.

## Component Features

- **Personalization**: Supports colors, icons, images, and text labels.
- **Shapes**: Supports circular (`CIRCLE`) and rounded corners (`ROUNDED`).
- **Sizes**: 5 predefined sizes from `XS` (24px) to `XL` (96px).
- **Badges**: Built-in support for status indicators (e.g., online, busy).
- **Grouping**: Support for overlapping avatar groups through `AvatarGroup`.

## API Reference

### Avatar Class

#### Factory Methods
- `Avatar.image(String src, String alt)`: Create an avatar with an image.
- `Avatar.label(String text, String bgColor)`: Create an avatar with initials/text.
- `Avatar.icon(String svgOrEmoji)`: Create an avatar with an icon.

#### Fluent Setters
- `setShape(Shape shape)`: Set shape as `Shape.CIRCLE` or `Shape.ROUNDED`.
- `setSize(Size size)`: Set size as `Size.XS, SM, MD, LG, XL`.
- `setBadge(String color)`: Add a status badge with a specific color.

### AvatarGroup Class
- Overlapping container for multiple `Avatar` components.

---

## Usage Examples

### 1. Basic Circular Image Avatar
```java
Avatar avatar = Avatar.image("https://example.com/profile.jpg", "User Profile")
    .setShape(Avatar.Shape.CIRCLE)
    .setSize(Avatar.Size.LG);
```

### 2. Label Avatar with Initials
```java
Avatar avatar = Avatar.label("JD", "#4f46e5")
    .setShape(Avatar.Shape.CIRCLE)
    .setSize(Avatar.Size.MD);
```

### 3. Icon Avatar (using SVG)
```java
Avatar avatar = Avatar.icon("<svg width='18' height='18' ...></svg>")
    .setShape(Avatar.Shape.CIRCLE)
    .setSize(Avatar.Size.SM);
```

### 4. Avatar with Online Status Badge
```java
Avatar avatar = Avatar.image("user.png", "User")
    .setBadge("#22c55e"); // Green
```

### 5. Overlapping Avatar Group
```java
AvatarGroup group = new AvatarGroup();
group.add(Avatar.image("u1.jpg", "U1").setSize(Avatar.Size.MD));
group.add(Avatar.image("u2.jpg", "U2").setSize(Avatar.Size.MD));
group.add(Avatar.label("+3", "#334155").setSize(Avatar.Size.MD));
```

## Styling (JettraTheme)

The component uses global CSS classes:
- `.j-avatar`: Base style.
- `.j-avatar-circle`, `.j-avatar-rounded`: Shapes.
- `.j-avatar-badge`: Positioning for the status badge.
- `.j-avatar-group`: Negative margin and overlap effect on hover.
