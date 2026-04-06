# ChartJSExtension Component

`ChartJSExtension` is a structural component of JettraWUI that provides the `chart.min.js` library offline, avoiding dependencies on external CDNs.

## Features
- **Offline Packaging:** Integrates ChartJS 4.x into your UI seamlessly.
- **Auto Initialized:** Automatically included by `AbstractChart.java` internally.
- **No external calls:** Keeps your internal applications working without internet access.

## Usage
You generally do not need to use `ChartJSExtension` explicitly. All components derived from `AbstractChart` (like `ChartsBar`, `ChartsPie`) use it behind the scenes.

If you ever need to manually include ChartJS in a custom page without using Jettra chart components, you can add it to your DOM tree:

```java
ChartJSExtension chartJs = new ChartJSExtension();
center.add(chartJs);
```

