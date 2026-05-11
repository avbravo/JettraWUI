# @CrudView

La anotación `@CrudView` simplifica drásticamente la creación de interfaces CRUD. En lugar de definir manualmente el Datatable, los modales para mostrar los elementos y controlar todos los eventos, solo es necesario definir la clase de la página con esta anotación.

## Uso

Aplica la anotación a tu clase de página (que debe extender de `DashboardBasePage` o `JettraDashboardPage`):

```java
@JettraPageSincronized(SyncType.ALL)
@CrudView(model = "PlanetaModel")
public class PlanetaPage extends DashboardBasePage {
    public PlanetaPage() {
        super("Mantenimiento de Planetas");
    }
}
```

## Atributos

- **model**: El nombre de la clase ViewModel (ej. `PlanetaModel`). El framework intentará resolverla en el paquete por defecto `com.jettra.example.model` o por nombre completo.
- **repository**: (Opcional) El nombre de la clase Repository. Si se omite, se asume que se llama igual que el modelo pero terminado en `Repository` (ej. `PlanetaRepository`).

## Funcionamiento

1.  **UI Automática**: El framework escanea el modelo y genera un `Datatable` con columnas para cada campo.
2.  **Etiquetas**: Utiliza `@PropertiesLabel` de cada atributo para asignar las etiquetas de las columnas y los campos del formulario.
3.  **Validaciones**: Aplica automáticamente las validaciones definidas en el modelo (ej. `@NotNull`, `@Size`).
4.  **Persistencia**: Delega las operaciones de guardado y eliminación al Repository especificado, asumiendo que tiene métodos estáticos `findAll()`, `save(Model)` y `delete(String id)`.
5.  **Sincronización**: Notifica automáticamente a `JettraSyncManager` después de cada operación.
