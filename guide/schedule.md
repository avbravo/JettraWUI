---
title: Schedule
description: Componente para mostrar y gestionar agendas
package: io.jettra.wui.components.Schedule
---

El componente `Schedule` permite crear agendas y horarios interactivos, mostrando bloques de eventos dentro de los días de la semana y gestionando sus horarios.

### Uso Básico

```java
import io.jettra.wui.components.Schedule;

Schedule sched = new Schedule();
sched.addEvent("Stand-up", "Mon", "9:00");
sched.addEvent("Planning", "Tue", "10:30");
sched.addEvent("Review", "Fri", "15:00");
```
