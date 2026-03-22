# Componente Notification

El componente `Notification` proporciona una forma de mostrar notificaciones flotantes (toast-like) en la interfaz web de JettraWUI. Estas notificaciones aparecen en una posición fija de la pantalla y pueden cerrarse automáticamente después de un tiempo especificado.

## Creación y Uso

Para usar el componente de notificación, simplemente instáncialo y añádelo a tu `Page` u otro contenedor:

```java
import io.jettra.wui.components.Notification;

// ... dentro de tu inicialización de UI ...

Notification notif = new Notification();
notif.setType("warning"); // Tipos soportados: success, error, warning, info
notif.setDurationMs(4000); // Duración en milisegundos (default: 3000ms)

// Mostrar un mensaje en la notificación. Automáticamente la hará visible.
notif.showMessage("Usuario o contraseña incorrectos");

add(notif); // Añadirla al Page
```

## Opciones Disponibles

- `setType(String type)`: Cambia el estilo visual de la notificación. Los valores predefinidos son `success`, `error`, `warning` e `info`.
- `setDurationMs(int ms)`: Configura el tiempo (en milisegundos) que la notificación permanecerá en pantalla antes de desvanecerse automáticamente.
- `setAutoClose(boolean autoClose)`: Configura si la notificación se cerrará automáticamente o si permanecerá de forma indefinida en pantalla.
- `showMessage(String message)`: Define el contenido HTML o texto a mostrar, y activa la visibilidad mediante inserción de scripts para la transición.
