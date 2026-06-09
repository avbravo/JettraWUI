# Console Component

El componente `Console` proporciona una vista de terminal o consola dentro de la interfaz web, ideal para mostrar registros (logs), salidas de comandos o mensajes de depuración en tiempo real.

## Uso Básico

```java
import io.jettra.wui.components.Console;

public class ConsolePage extends Page {
    @Override
    protected void onInit(Map<String, String> params) {
        Console console = new Console("miConsola");
        add(console);
    }
}
```

## Interactuar desde JavaScript

El componente expone funciones globales de JavaScript que permiten agregar mensajes a la consola dinámicamente desde el lado del cliente:

- `jtConsoleLog(id, msg, type)`: Agrega una nueva línea a la consola especificada. El parámetro `type` puede ser `'info'`, `'error'`, `'warn'` o indefinido (defecto).
- `jtConsoleClear(id)`: Limpia el contenido de la consola.

### Ejemplo de interacción

```html
<button onclick="jtConsoleLog('miConsola', 'Iniciando proceso...', 'info')">Start</button>
<button onclick="jtConsoleLog('miConsola', 'Ha ocurrido un error', 'error')">Error</button>
<button onclick="jtConsoleClear('miConsola')">Limpiar</button>
```

## Personalización

Al heredar de `UIComponent`, el componente `Console` soporta todos los métodos estándar de estilización:

```java
console.setStyle("height", "400px");
console.setStyle("background-color", "#000");
```

## Interactuar desde el Backend (Java)

Gracias al mecanismo de sincronización automática (polling integrado), el componente `Console` soporta el envío de mensajes desde cualquier parte del código Java del servidor, y la interfaz web se actualizará de manera automática para mostrarlos en tiempo real.

### Ejemplo de interacción

Puedes utilizar el método estático `Console.addMessage` pasando el ID de la consola, el mensaje y el tipo (por ejemplo, `'info'`, `'error'`, `'warn'`):

```java
import io.jettra.wui.components.Console;

// En cualquier parte de tu código backend (Controladores, Servicios, Repositorios, etc.)
public void procesarArchivo() {
    try {
        Console.addMessage("miConsola", "Iniciando procesamiento de archivo...", "info");
        // Lógica del proceso...
        Console.addMessage("miConsola", "El archivo se ha procesado correctamente.", "info");
    } catch (Exception e) {
        // Agregar mensaje de error y este se reflejará en la UI del usuario
        Console.addMessage("miConsola", "Error crítico al procesar: " + e.getMessage(), "error");
    }
}
```

La consola configurada con el id `miConsola` recuperará automáticamente estos mensajes y los mostrará en la pantalla del usuario, sin necesidad de que este recargue la página o haga clic en ningún botón.
