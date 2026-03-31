---
title: Downloader
description: A component to trigger file downloads in JettraWUI.
---

# Downloader

The `Downloader` component generates an HTML anchor tag configured with the `download` attribute to prompt file downloads on the client browser.

## Example Usage

```java
import io.jettra.wui.components.Downloader;

Downloader downloader = new Downloader("Download Report", "/downloads/report.pdf");
downloader.setFilename("Q1_Report.pdf");

center.add(downloader);
```

## Attributes

- **url**: The URL of the file to download.
- **text**: The text displayed on the button.
- **filename**: The default filename proposed to the user when downloading.
