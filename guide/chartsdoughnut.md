---
title: ChartsDoughnut
description: Generate a Doughnut Chart
---

# ChartsDoughnut

The `ChartsDoughnut` component allows you to generate responsive doughnut charts using JettraWUI, powered by Chart.js.

## Basic Usage

```java
ChartsDoughnut chart = new ChartsDoughnut("myDoughnut");
chart.setLabels("Red", "Blue", "Yellow");
chart.addDataset("Dataset 1", new Number[]{300, 50, 100}, 
    new String[]{"#ff6384", "#36a2eb", "#ffce56"}, 
    new String[]{"#fff", "#fff", "#fff"});
```

## Methods
- `setLabels(String... labels)`: Sets the labels for the chart.
- `addDataset(String label, Number[] data)`: Adds a dataset.
- `addDataset(String label, Number[] data, String[] bgColors, String[] borderColors)`: Adds a dataset with styling.
