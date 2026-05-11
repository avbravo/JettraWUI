# @CrudView

La anotación `@CrudView` simplifica drásticamente la creación de interfaces CRUD. A partir de la versión actual, el framework utiliza **Java Annotation Processing (JAP)** para generar código en tiempo de compilación, eliminando la necesidad de usar Java Reflection API en las operaciones críticas de persistencia y visualización.

## Uso

Aplica la anotación a tu clase de página:

```java
@CrudView(model = Pais.class, repository = PaisRepository.class)
public class PaisPage extends Page {
    public PaisPage() {
        super("Gestión de Países");
    }
}
```

## Beneficios del Procesamiento de Anotaciones

1.  **Rendimiento**: Se genera una clase `{PageName}CrudHandler` que implementa la interfaz `CrudHandler`. Las llamadas a los métodos del repositorio y el acceso a los campos del modelo se realizan de forma directa o altamente optimizada.
2.  **Validación en Compilación**: Los errores en la configuración de `@CrudView` pueden detectarse durante la compilación en lugar de en tiempo de ejecución.
3.  **Código Limpio**: `JettraMVC` utiliza el handler generado automáticamente si existe, delegando en él la lógica de negocio.

## Atributos

- **model**: La clase del modelo (ej. `Pais.class`).
- **repository**: La clase del repositorio (ej. `PaisRepository.class`). El repositorio debe contar con métodos estáticos `findAll()`, `save(model)` y `delete(id)`.

## Funcionamiento Interno

1.  **Generación de Handler**: El `CrudViewProcessor` escanea las clases anotadas y genera un archivo fuente Java que implementa `CrudHandler<M>`.
2.  **Inyección**: Al procesar la página, `JettraMVC` busca la clase generada por nombre (`PageClassName + "CrudHandler"`).
3.  **Operaciones**:
    - **Listado**: Usa `handler.findAll()` para obtener los datos.
    - **Guardado**: Usa `handler.save(model)` para persistir cambios.
    - **Eliminación**: Usa `handler.delete(id)` para remover registros.
4.  **Sincronización**: Mantiene la integración con `JettraSyncManager` para notificaciones en tiempo real.

## Configuración del Proyecto

Asegúrese de tener las dependencias de Google AutoService y JavaPoet en su `pom.xml`:

```xml
<dependency>
    <groupId>com.google.auto.service</groupId>
    <artifactId>auto-service</artifactId>
    <version>1.1.1</version>
    <optional>true</optional>
</dependency>
<dependency>
    <groupId>com.squareup</groupId>
    <artifactId>javapoet</artifactId>
    <version>1.13.0</version>
</dependency>
```

