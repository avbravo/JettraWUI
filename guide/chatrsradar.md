---
title: ChartsRadar
description: Generate a Radar Chart
---

# ChartsRadar

The `ChartsRadar` component allows you to generate responsive radar charts using JettraWUI.

## Basic Usage

```java
ChartsRadar chart = new ChartsRadar("myRadar");
chart.setLabels("Speed", "Power", "Defense", "Agility");
chart.addDataset("Player 1", new Number[]{80, 90, 70, 85});
```

## Methods
- `setLabels(String... labels)`: Sets the labels for the chart.
- `addDataset(String label, Number[] data)`: Adds a dataset.
- `addDataset(String label, Number[] data, String[] bgColors, String[] borderColors)`: Adds a dataset with styling.
