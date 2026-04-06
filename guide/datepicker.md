---
title: DatePicker
description: Componente para seleccionar fechas
package: io.jettra.wui.components.DatePicker
---

El componente `DatePicker` permite seleccionar una fecha utilizando el calendario nativo del navegador, proveyendo accesibilidad y soporte en dispositivos móviles sin necesidad de librerías extra.

### Uso Básico

```java
import io.jettra.wui.components.DatePicker;

DatePicker dp = new DatePicker("fecha_inicio", "Fecha de Inicio");
dp.setValue("2026-05-01");
dp.setMin("2026-01-01");
dp.setMax("2026-12-31");
dp.setOnChange("console.log('Fecha cambiada a: ' + this.value)");
```
