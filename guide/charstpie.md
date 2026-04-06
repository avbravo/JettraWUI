---
title: ChartsPie
description: Generate a Pie Chart
---

# ChartsPie

The `ChartsPie` component allows you to generate responsive pie charts using JettraWUI.

## Basic Usage

```java
ChartsPie chart = new ChartsPie("myPie");
chart.setLabels("Apple", "Banana", "Cherry");
chart.addDataset("Fruits", new Number[]{50, 20, 30}, 
    new String[]{"#ff0000", "#ffff00", "#ff00ff"}, 
    new String[]{"#fff", "#fff", "#fff"});
```

## Methods
- `setLabels(String... labels)`: Sets the labels for the chart.
- `addDataset(String label, Number[] data)`: Adds a dataset.
- `addDataset(String label, Number[] data, String[] bgColors, String[] borderColors)`: Adds a dataset with styling.
