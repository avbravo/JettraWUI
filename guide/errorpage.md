---
title: ErrorPage
description: Componente para mostrar errores de forma global y estructurada mediante diseño 3D
---

# ErrorPage

El componente `ErrorPage` reemplaza las pantallas vacías o blancas cuando ocurre un error en los componentes de la aplicación o en el servidor (ej. un Error `404 Not Found` o un `500 Internal Server Error`).

Forma parte del paquete `io.jettra.wui.complex` y extiende `Page` implementando una interfaz 3D moderna para mostrar la información del error al usuario final.

## Características

- Diseño estilo **Glassmorphism / 3D**.
- Visualización de tres parámetros clave:
  - **Title** (`título del error`).
  - **Detail** (`detalle o descripción extendida del error`).
  - **Origin** (`lugar / clase / componente donde se originó`).
- Botón de **"Volver al Inicio"** configurado para regresar a la raíz `/`.

## Configuración y Uso

Para utilizar el `ErrorPage` como manejador global de errores en tu servidor `JettraServer`, debes:

1. **Registrar la clase ErrorPage** dentro de las rutas del servidor (generalmente apuntando a `/error`).
2. **Asignar la ruta de error** utilizando el método `setErrorPage` del servidor.

### Ejemplo de Configuración en `WebExampleMain.java`

```java
import com.jettra.server.JettraServer;
import io.jettra.wui.complex.ErrorPage;

public class WebExampleMain {

    public static void main(String[] args) {
        JettraServer server = new JettraServer();
        
        // 1. Configuramos la página de error por defecto
        server.setErrorPage("/error");

        // 2. Asociamos una ruta a la clase de JettraWUI
        server.addHandler("/error", ErrorPage.class);
        
        // 3. (Resto de los handlers a registrar)
        // server.addHandler("/", ...);

        server.start();
    }
}
```

JettraServer automáticamente redigirá al usuario a `/error?title=...&detail=...&origin=...` en caso de que ocurra una excepción no capturada (500) o que la URL solicitada no posea un handler (404), manteniendo la estética definida por JettraWUI.
