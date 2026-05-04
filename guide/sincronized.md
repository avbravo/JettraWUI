# Jettra Page Synchronization (`@JettraPageSincronized`)

La anotación `@JettraPageSincronized` es una herramienta potente de **JettraWUI** que permite mantener la consistencia de los datos visualizados por diferentes usuarios en tiempo real. 

Cuando una página está anotada, el framework inyecta automáticamente lógica de **polling** (consulta periódica) que monitorea cambios globales. Si otro usuario realiza una acción que afecta los datos de la página actual, se muestra una notificación 3D no invasiva.

## Uso de la Anotación

Para habilitar la sincronización, simplemente coloque la anotación sobre la clase de su página:

```java
@JettraPageSincronized(SyncType.ALL)
public class MiPagina extends DashboardBasePage {
    // ...
}
```

### Parámetros de Configuración

| Parámetro | Tipo | Descripción |
| :--- | :--- | :--- |
| `value` | `SyncType` | Define qué tipos de eventos deben disparar la notificación. Por defecto es `ALL`. |
| `entity` | `String` | (Opcional) El nombre de la entidad/modelo. Si se omite, se infiere del nombre de la página. |

### Tipos de Sincronización (`SyncType`)

- `SyncType.ALL`: Notifica sobre cualquier cambio (Crear, Actualizar, Eliminar, Mover).
- `SyncType.CREATE`: Notifica solo cuando se añaden nuevos registros.
- `SyncType.UPDATE`: Notifica cuando se modifican registros existentes.
- `SyncType.DELETE`: Notifica cuando se eliminan registros.
- `SyncType.MOVE`: Notifica cuando un elemento cambia de estado o posición (ej. en el Tablero Kanban).

---

## Cómo Notificar Cambios

La sincronización no es mágica; requiere que las acciones del servidor notifiquen al gestor central cuando ocurre un cambio exitoso. Esto se hace mediante `JettraSyncManager.notifyChange`.

### Ejemplo en un CRUD de Personas

```java
@Override
protected void onPost(Map<String, String> params) {
    String action = params.get("action");
    
    if ("save".equals(action)) {
        // Lógica de guardado...
        PersonaRepository.save(newPersona);
        
        // Notificamos a otros usuarios que los datos de "PersonaModel" han cambiado
        JettraSyncManager.notifyChange("PersonaModel", SyncType.UPDATE, getLoggedUser(currentExchange));
    }
}
```

### Ejemplo en un Tablero Kanban (Movimiento)

Para el caso del Kanban, donde las tarjetas se mueven entre columnas:

```java
if ("move".equals(action)) {
    moveCard(params);
    // Notificamos específicamente un movimiento
    JettraSyncManager.notifyChange("KanbanCard", SyncType.MOVE, getLoggedUser(currentExchange));
}
```

---

## Experiencia de Usuario (UX)

Cuando se detecta un cambio, **JettraWUI** muestra un diálogo flotante futurista en la parte inferior derecha con las siguientes características:

1.  **Estética 3D Premium**: Utiliza efectos de desenfoque (`backdrop-filter`), bordes brillantes y animaciones de entrada suaves.
2.  **No Invasivo**: El diálogo no interrumpe el trabajo actual. El usuario puede seguir interactuando con la página.
3.  **Acciones Claras**:
    -   **Actualizar**: Recarga la página inmediatamente para ver los nuevos datos.
    -   **Cerrar**: Elimina el aviso si el usuario desea continuar con su vista actual.
4.  **Diseño Vertical**: Los botones de acción se presentan apilados verticalmente para facilitar la interacción táctil y asegurar que el contenido permanezca dentro del diálogo.

![Sync Demo](https://img.shields.io/badge/Jettra--Sync-Futuristic-00ffff?style=for-the-badge&logo=java)

> [!TIP]
> Use `SyncType.ALL` para dashboards críticos donde la integridad visual es fundamental, y tipos específicos para páginas donde solo le interese un tipo de evento (ej. solo nuevas alertas).
