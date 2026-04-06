---
title: ChartsLine
description: Generate a Line Chart
---

# ChartsLine

The `ChartsLine` component allows you to generate responsive line charts using JettraWUI.

## Basic Usage

```java
ChartsLine chart = new ChartsLine("myLine");
chart.setLabels("Week 1", "Week 2", "Week 3");
chart.addDataset("Growth", new Number[]{10, 15, 25});
```

## Methods
- `setLabels(String... labels)`: Sets the labels for the chart.
- `addDataset(String label, Number[] data)`: Adds a dataset.
- `addDataset(String label, Number[] data, String[] bgColors, String[] borderColors)`: Adds a dataset with styling.
