# BarCodeExtension

El paquete `io.jettra.wui.components.BarCodeExtension` es un envoltorio que empaca la librería `JsBarcode` de manera estática y accesible desde Java. Esto resuelve problemas con políticas de seguridad de contenido (CSP) limitantes en ciertos entornos al no inyectar un tag `<script src="...">` que dependa de CDNs externos, reemplazándolo por código local a través de `innerText`.

## Estructura
Provee la constante inyectable `JS_CODE`. 

## Uso

Esta clase sólo define el código JS como constante, para ser invocada internamente por `BarCode`.

```java
import io.jettra.wui.components.BarCodeExtension;

String jsCode = BarCodeExtension.JS_CODE;
```
