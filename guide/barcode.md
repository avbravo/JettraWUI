---
title: BarCode
description: Componente para generar Códigos de Barras
package: io.jettra.wui.components.BarCode
---

El componente `BarCode` permite generar e incrustar códigos de barra usando diversos formatos estándares en JettraWUI.

### Modo de Uso
Se utiliza un generador vectorial (SVG) de fondo garantizando que al cambiar sus propiedades de escalado siga siendo nítido.

```java
import io.jettra.wui.components.BarCode;

BarCode bc = new BarCode("product_barcode");
bc.setText("9876543210");
bc.setFormat("CODE128"); // También puede usar EAN13, UPC, etc.
bc.setLineColor("#2c3e50");
bc.setBackground("transparent");
bc.setDisplayValue(true);
```
