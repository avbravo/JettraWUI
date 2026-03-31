---
title: PDFViewer
description: A component for displaying PDF documents within the JettraWUI layout.
---

# PDFViewer

The `PDFViewer` component creates an `iframe` designed to seamlessly embed and display PDF files within the application without needing third-party libraries.

## Example Usage

```java
import io.jettra.wui.components.PDFViewer;

PDFViewer viewer = new PDFViewer("/documents/manual.pdf");
viewer.setStyle("height", "800px");

center.add(viewer);
```

## Attributes

- **url**: The absolute or relative path to the PDF file.
