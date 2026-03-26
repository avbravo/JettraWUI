# Jettra Page Synchronization

La anotación `@JettraPageSincronized` permite mantener sincronizados los modelos de las páginas Web entre diferentes sesiones de usuario. Cuando se activa, la página detecta cambios realizados por otros usuarios y muestra una advertencia no invasiva en 3D permitiendo al usuario actualizar su vista.

## Funcionamiento

El mecanismo utiliza un sistema de **polling** (consulta periódica) al servidor para verificar si ha ocurrido un evento de cambio (Crear, Editar o Eliminar) en la entidad asociada a la página.

### Propiedades de la Anotación

- **value**: Define qué eventos disparan la notificación.
  - `SyncType.ALL`: (Predeterminado) Notifica sobre cualquier cambio.
  - `SyncType.CREATE`: Solo notifica cuando se crea un registro nuevo.
  - `SyncType.UPDATE`: Solo notifica cuando se edita un registro.
  - `SyncType.DELETE`: Solo notifica cuando se elimina un registro.
- **entity**: (Opcional) El nombre de la entidad a monitorear. Si se deja vacío, se deduce del nombre de la clase de la página (ej. `PaisPage` -> `PaisModel`).

## Ejemplo de Uso

### 1. Aplicación de la anotación

En el archivo de la página Java:

```java
import io.jettra.wui.sync.JettraPageSincronized;
import io.jettra.wui.sync.SyncType;

@JettraPageSincronized(SyncType.ALL)
public class PaisPage extends DashboardBasePage {
    // ...
}
```

### 2. Notificación de cambios

Cuando se realiza una operación CRUD, se debe notificar al `JettraSyncManager`:

```java
import io.jettra.wui.sync.JettraSyncManager;
import io.jettra.wui.sync.SyncType;

// En el método onPost de PaisPage
if (operacionExitosa) {
    JettraSyncManager.notifyChange("PaisModel", SyncType.UPDATE, username);
}
```

## Interfaz de Usuario (3D Popup)

Cuando se detecta un cambio, aparecerá una ventana emergente en 3D en la esquina inferior derecha. Esta ventana es **no invasiva**:
- Utiliza **glassmorphism** y efectos de profundidad.
- No bloquea el flujo de trabajo del usuario.
- Permite elegir entre **Actualizar** la página inmediatamente o **Ignorar** el aviso.

![Sincronización Popup](https://img.shields.io/badge/Jettra-Sync-00ffff)

> [!IMPORTANT]
> La sincronización siempre tomará el **último cambio** generado por cualquier usuario si se decide actualizar.
