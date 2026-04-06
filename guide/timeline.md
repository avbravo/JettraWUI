---
title: Timeline
description: Componente para graficar eventos en lineas de tiempo
package: io.jettra.wui.components.Timeline
---

El componente `Timeline` es un visualizador secuencial para demostrar una serie de eventos o descripciones históricas.

### Uso Básico

```java
import io.jettra.wui.components.Timeline;

Timeline tl = new Timeline();
tl.addNode("Release 1.0", "Lanzamiento oficial", "Marzo 10, 2026");
tl.addNode("Update 1.1", "Mejoras de rendimiento", "Abril 1, 2026");
```
