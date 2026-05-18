# Componente: QRReader

El componente `QRReader` de JettraWUI permite integrar de manera nativa un lector de códigos QR utilizando la cámara del dispositivo móvil o de escritorio del usuario. Es altamente responsivo, dinámico, incluye animaciones de escaneo en tiempo real y ofrece retroalimentación auditiva mediante un beep de confirmación.

## Características Clave
- **Selector de cámara dinámico**: Detecta automáticamente las cámaras frontal, trasera o periféricos externos conectados y permite intercambiarlas en tiempo real.
- **Línea de Escaneo Láser**: Animación fluida de láser de escaneo en color magenta/neon.
- **Sound Beep Feedback**: Emite un tono beep utilizando la API Web Audio de manera nativa (sin archivos de audio externos) al decodificar un código exitosamente.
- **Librería Asíncrona**: Carga la librería ligera de alto rendimiento `jsQR` de forma asíncrona directamente desde CDN.
- **Fácil Integración**: Permite configurar un callback de javascript cliente (`setOnScan`) o interactuar mediante eventos DOM.

---

## Métodos Disponibles en Java

| Método | Retorno | Descripción |
|---|---|---|
| `new QRReader(String id)` | `QRReader` | Instancia el componente lector QR con un ID específico. |
| `setWidth(String width)` | `QRReader` | Define el ancho CSS del contenedor (por defecto `350px`). |
| `setHeight(String height)` | `QRReader` | Define el alto CSS de la pantalla de video (por defecto `350px`). |
| `setOnScan(String jsCallback)` | `QRReader` | Configura una función JS callback que se invoca automáticamente al leer un código. Recibe el texto escaneado. |

---

## Ejemplo Práctico de Uso en Java

Aquí tienes un ejemplo de cómo integrar el lector en tu controlador o página JettraWUI para capturar y rellenar un campo de texto con el código QR escaneado:

```java
package com.jettra.example.pages;

import com.jettra.example.dashboard.DashboardBasePage;
import io.jettra.wui.complex.Center;
import io.jettra.wui.components.*;

public class QRReaderPage extends DashboardBasePage {

    public QRReaderPage() {
        super("Lector de Códigos QR");
    }

    @Override
    protected void initCenter(Center center, String username) {
        Div container = new Div();
        container.setStyle("padding", "30px").setStyle("display", "flex").setStyle("flex-direction", "column").setStyle("align-items", "center").setStyle("gap", "20px");

        Header h = new Header(1, "Lector QR JettraWUI");
        h.setStyle("color", "var(--jettra-accent)");
        container.add(h);

        // Campo donde se colocará el código QR leído
        TextBox qrResult = new TextBox("text", "qrResult");
        qrResult.setId("qrResult");
        qrResult.setProperty("placeholder", "El código escaneado aparecerá aquí...");
        qrResult.setStyle("width", "350px").setStyle("padding", "10px").setStyle("border-radius", "8px");
        container.add(qrResult);

        // Instanciar y añadir el QRReader
        QRReader reader = new QRReader("myQrReader")
            .setWidth("350px")
            .setHeight("300px")
            .setOnScan("function(code) { " +
                       "  document.getElementById('qrResult').value = code; " +
                       "}");
                       
        container.add(reader);
        center.add(container);
    }
}
```

---

## Evento Personalizado en JavaScript

Además del método `setOnScan`, el componente dispara un evento global en el documento del navegador llamado `qr_scanned_[ID]`, lo que permite una integración limpia con scripts o frameworks externos:

```javascript
document.addEventListener('qr_scanned_myQrReader', function(e) {
    console.log("Código leído globalmente:", e.detail);
});
```
