---
title: ChartsBar
description: Generate a Bar Chart
---

# ChartsBar

The `ChartsBar` component allows you to generate responsive bar charts using JettraWUI.

## Basic Usage

```java
ChartsBar chart = new ChartsBar("myBar");
chart.setLabels("January", "February", "March");
chart.addDataset("Sales", new Number[]{10, 20, 30});
```

## Methods
- `setLabels(String... labels)`: Sets the labels for the chart.
- `addDataset(String label, Number[] data)`: Adds a dataset.
- `addDataset(String label, Number[] data, String[] bgColors, String[] borderColors)`: Adds a dataset with styling.
