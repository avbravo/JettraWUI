# `@ViewDataTable` Annotation

La anotación `@ViewDataTable` permite renderizar un Datatable dinámico y editable dentro de un modelo (`Model`). Es ideal para representar relaciones maestro-detalle (por ejemplo, una `Factura` y sus `Lineas de Factura`), permitiendo insertar, editar y eliminar registros en formato de tabla de manera interactiva.

---

## Atributos de la Anotación

| Atributo | Tipo | Valor por Defecto | Descripción |
| :--- | :--- | :--- | :--- |
| `row` | `String` | `""` | Lista separada por comas de las columnas (propiedades del modelo de detalle) a mostrar en la tabla. |
| `editablerow` | `String` | `""` | Lista separada por comas de las columnas que serán editables dentro del formulario/modal de detalle. |
| `showRowInMasterTable` | `boolean` | `false` | Si es `false` (valor predeterminado), el detalle no se muestra en la tabla maestra principal, sino dentro del modal de creación/edición. Si es `true`, la tabla de detalles se muestra incrustada dentro de la celda de la tabla maestra. |
| `editableRowMaster` | `boolean` | `true` | Determina si las columnas de la tabla maestra son editables en línea. Si es `false`, la edición en línea del datatable maestro se desactiva por completo. |
| `source` | `String` | `""` | La clase de origen de datos (Repository, Controller o Service) que suministra los datos iniciales. |
| `method` | `String` | `""` | El método de la clase de origen que retorna la lista de objetos (`List<T>`). |
| `filter` | `String` | `""` | (Opcional) Un filtro a aplicar en la consulta de obtención de datos. |

---

## Ejemplos de Uso

### Ejemplo 1: Formulario Maestro-Detalle Tradicional (Detalles ocultos en Tabla Maestra)

En este escenario típico, los detalles de la factura no se muestran en la tabla maestra inicial para mantener la interfaz limpia. El detalle se edita y visualiza únicamente dentro del modal de edición del registro maestro.

```java
@JettraViewModel
public class FacturaModel {
    @NotNull
    @PropertiesLabel(value = "factura.id", label = "ID Factura")
    private Long idFactura;

    @NotNull
    @ViewSelectOne(label = "nombre", source = "ClienteRepository", method = "findAll")
    @PropertiesLabel(value = "factura.cliente", label = "Cliente")
    private ClienteModel clienteModel;

    @NotNull
    @PropertiesLabel(value = "factura.fechaEmision", label = "Fecha de Emisión")
    private LocalDate fechaEmision;

    // showRowInMasterTable = false: Oculta la sub-tabla en la vista general
    @ViewDataTable(
        showRowInMasterTable = false,
        row = "productoId, precio, cantidad, total", 
        editablerow = "productoId, cantidad", 
        source = "FacturaRepository", 
        method = "getLineas"
    )
    @PropertiesLabel(value = "factura.lineas", label = "Detalle de Líneas")
    private List<LineaFacturaModel> lineaFacturaModel;

    // Getters y setters...
}
```

### Ejemplo 2: Incrustar Detalles dentro de la Tabla Maestra

Al cambiar `showRowInMasterTable` a `true`, la tabla de sub-elementos se renderiza automáticamente dentro de la celda correspondiente en cada fila de la tabla maestra.

```java
@JettraViewModel
public class FacturaModel {
    @NotNull
    private Long idFactura;

    // showRowInMasterTable = true: Renderiza la tabla detallada dentro de la celda de la factura maestra
    @ViewDataTable(
        showRowInMasterTable = true,
        row = "productoId, precio, cantidad, total", 
        editablerow = "productoId, cantidad",
        source = "FacturaRepository", 
        method = "getLineas"
    )
    private List<LineaFacturaModel> lineaFacturaModel;

    // Getters y setters...
}
```

### Ejemplo 3: Desactivar la Edición en Línea de la Tabla Maestra

Por defecto, la tabla maestra permite la edición en línea (`inline editing`) de sus celdas cuando la página está configurada como editable. Si deseas que los registros de la tabla maestra solo se editen a través del modal y no directamente en la tabla principal, puedes establecer `editableRowMaster` en `false`.

```java
@JettraViewModel
public class FacturaModel {
    @NotNull
    private Long idFactura;

    // editableRowMaster = false: Deshabilita la edición en línea de la factura en la tabla maestra
    @ViewDataTable(
        editableRowMaster = false,
        row = "productoId, precio, cantidad, total",
        editablerow = "productoId, cantidad"
    )
    private List<LineaFacturaModel> lineaFacturaModel;

    // Getters y setters...
}
```

## Atributo `fieldOnlyMasterTable` en Selectores Relacionados

Cuando se usan relaciones maestro-detalle mediante `@ViewDataTable`, a veces la tabla maestra del componente contiene columnas que representan relaciones de objeto (anotadas con `@ViewSelectOne` o `@ViewSelectMany`). 

Por defecto, la tabla maestra intentará invocar `toString()` sobre el objeto relacionado. Sin embargo, para un control más preciso y estético de qué datos mostrar en la tabla maestra, puedes utilizar el atributo `fieldOnlyMasterTable` en las anotaciones `@ViewSelectOne` y `@ViewSelectMany`.

Este atributo permite especificar el nombre del campo (o campos, separados por comas) de la entidad relacionada que se deben mostrar en la celda correspondiente de la tabla maestra.

### Ejemplo de Uso con `@ViewSelectOne`

```java
@JettraViewModel
public class FacturaModel {
    @NotNull
    private Long idFactura;

    // Se especifica que en la columna "Cliente" de la tabla maestra, se muestre el "nombre" del cliente
    @NotNull
    @ViewSelectOne(label = "nombre", fieldOnlyMasterTable = "nombre", source = "ClienteRepository", method = "findAll")
    @PropertiesLabel(value = "factura.cliente", label = "Cliente")
    private ClienteModel clienteModel;

    @ViewDataTable(
        showRowInMasterTable = true,
        row = "productoId, precio, cantidad, total", 
        editablerow = "productoId, cantidad",
        source = "FacturaRepository", 
        method = "getLineas"
    )
    private List<LineaFacturaModel> lineaFacturaModel;
}
```

En este caso, la tabla maestra de `FacturaModel` mostrará únicamente el valor del campo `nombre` del objeto `ClienteModel` en su respectiva celda.
