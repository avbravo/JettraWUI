# @ViewSelectOne

La anotación `@ViewSelectOne` se utiliza para representar un campo de un modelo como un componente de selección única (`SelectOne` / Dropdown) en la vista generada por `CrudView`.

Esta anotación es ideal para manejar relaciones "Muchos a Uno" o para campos que requieren una selección de una lista predefinida proveniente de una base de datos o servicio.

## Parámetros

| Parámetro | Tipo | Descripción |
| :--- | :--- | :--- |
| `label` | String | El nombre del atributo (o atributos separados por comas) del objeto fuente que se mostrarán como etiqueta en el selector. |
| `source` | String | El nombre de la clase que provee los datos (ej: `Repository`, `Service`). Puede ser el nombre simple o calificado. |
| `method` | String | El nombre del método (estático) dentro del `source` que devuelve la lista de objetos. |
| `filter` | String | (Opcional) Un valor que se pasará como parámetro al método especificado si este requiere un filtro. |

## Ejemplos de uso

### Con un Objeto de Modelo (Relación)

En este ejemplo, el campo `grupo` se dibujará como un selector que muestra el `id` y el `nombre` de los grupos obtenidos de `GrupoRepository.findAll()`.

```java
@ViewSelectOne(label = "id, nombre", source = "GrupoRepository", method = "findAll")
private GrupoModel grupo;
```

### Con un String simple

También se puede usar para seleccionar un valor de texto simple desde un servicio de opciones.

```java
@ViewSelectOne(label = "descripcion", source = "OpcionesService", method = "listarIdiomas")
private String idioma;
```

### Usando Filtros

Si el método en la fuente de datos requiere un parámetro para filtrar los resultados.

```java
@ViewSelectOne(label = "nombre", source = "PaisRepository", method = "findByContinente", filter = "Europa")
private PaisModel pais;
```

## Funcionamiento Interno

Cuando `CrudView` encuentra esta anotación:
1. Localiza la clase `source` mediante reflexión.
2. Invoca el método `method` para obtener una `List<?>`.
3. Para cada objeto en la lista, resuelve el valor interno (ID) y la etiqueta visual configurada en `label`.
4. Construye dinámicamente un componente `SelectOne`.
