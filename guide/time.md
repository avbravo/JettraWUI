---
title: Time
description: Componente para seleccionar tiempo
package: io.jettra.wui.components.Time
---

El componente `Time` permite seleccionar horas y minutos utilizando el control de reloj nativo del dispositivo del usuario.

### Uso Básico

```java
import io.jettra.wui.components.Time;

Time tp = new Time("hora_inicio", "Hora de Inicio");
tp.setValue("14:30");
tp.setMin("09:00");
tp.setMax("18:00");
```
