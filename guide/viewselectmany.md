# @ViewSelectMany

La anotación `@ViewSelectMany` permite representar un campo de lista como un componente de selección múltiple avanzado (`SelectMany`) en la interfaz de `CrudView`.

A diferencia de un selector múltiple estándar, `SelectMany` en JettraWUI ofrece una interfaz elegante con casillas de verificación y búsqueda integrada.

## Parámetros

| Parámetro | Tipo | Descripción |
| :--- | :--- | :--- |
| `label` | String | Atributos del objeto fuente a mostrar (pueden ser varios separados por comas). |
| `source` | String | Clase proveedora de los datos (Repository, Service, etc.). |
| `method` | String | Método estático que devuelve la `List<?>` de opciones. |
| `filter` | String | (Opcional) Filtro para el método de consulta. |

## Ejemplos de uso

### Selección Múltiple de Objetos

Ideal para relaciones "Muchos a Muchos". El usuario podrá seleccionar varios roles para un usuario.

```java
@ViewSelectMany(label = "descripcion", source = "RolRepository", method = "findAll")
private List<RolModel> roles;
```

### Lista de Strings

También se puede usar para colecciones simples de texto.

```java
@ViewSelectMany(label = "valor", source = "TagsService", method = "getPopulares")
private List<String> etiquetas;
```

## Características del Componente

*   **Interfaz Moderna**: Utiliza una lista con efectos de "Glassmorphism" y micro-animaciones.
*   **Persistencia Automática**: Los valores seleccionados se envían como una cadena separada por comas (CSV) al backend, compatible con los controladores de Jettra.
*   **Carga Dinámica**: Los datos se cargan en tiempo real mediante reflexión al renderizar el `CrudView`.

## Notas de Implementación

Para que la selección funcione correctamente:
1. La clase `source` debe ser accesible en el classpath.
2. El método debe ser público y preferiblemente estático o accesible sin estado complejo.
3. El campo anotado debe ser preferiblemente de tipo `List<?>` o `String` (si se guardan como CSV).
