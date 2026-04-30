# TrafficLight Component

The `TrafficLight` component is a visual indicator that represents different states using standard traffic light colors (Red, Yellow, Green).

## Usage

```java
TrafficLight light = new TrafficLight();
light.setStatus(TrafficLight.Status.GREEN);
```

## Statuses

- `TrafficLight.Status.RED`: Indicates a stopped or critical state.
- `TrafficLight.Status.YELLOW`: Indicates a warning or transitional state.
- `TrafficLight.Status.GREEN`: Indicates a go or healthy state.

## Features

- **Fluent API**: Supports method chaining.
- **Premium Design**: Includes realistic glows and 3D shadows.
- **Customizable**: Can be further styled using standard `setStyle` calls.

## Example

```java
Div container = new Div();
container.add(new TrafficLight().setStatus(TrafficLight.Status.RED));
container.add(new TrafficLight().setStatus(TrafficLight.Status.YELLOW));
container.add(new TrafficLight().setStatus(TrafficLight.Status.GREEN));
```
