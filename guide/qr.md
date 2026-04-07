---
title: QR
description: Componente para generar Códigos QR
package: io.jettra.wui.components.QR
---

El componente `QR` permite generar códigos de respuesta rápida visuales para ser escaneados por dispositivos móviles.

### Modo de Uso
Instancie la clase `QR` definiendo un identificador, y establezca su texto, tamaño y esquema de color.

```java
import io.jettra.wui.components.QR;

QR miQr = new QR("website_qr");
miQr.setText("https://jettra.io");
miQr.setWidth(200);
miQr.setHeight(200);
miQr.setColorDark("#111111");
miQr.setColorLight("#ffffff");
```
