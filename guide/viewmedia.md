---
title: ViewMedia
description: A component to play audio and video natively.
---

# ViewMedia

The `ViewMedia` component uses native HTML5 `<video>` or `<audio>` elements to play multimedia files right in the UI. 

## Example Usage

```java
import io.jettra.wui.components.ViewMedia;

// Video player
ViewMedia video = new ViewMedia("/media/demo.mp4", true);
video.setAutoplay(true);

// Audio player
ViewMedia audio = new ViewMedia("/media/song.mp3", false);

center.add(video).add(audio);
```

## Attributes

- **url**: The path to the media file.
- **isVideo**: Boolean to switch between video mode or audio mode.
- **autoplay**: Boolean indicating if the media should start playing automatically.
