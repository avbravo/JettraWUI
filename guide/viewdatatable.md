# @ViewDataTable

La anotación `@ViewDataTable` dibuja un Datatable en un `Model` que permite mostrar y opcionalmente insertar o editar registros en forma de tabla. Es especialmente útil en formularios maestro-detalles (por ejemplo, Factura y Líneas de Factura).

## Atributos

- `row` (String): Lista separada por comas de los campos que se mostrarán en la tabla.
- `editablerow` (String): Lista separada por comas de los campos que permitirán ser editados dentro de la tabla.
- `source` (String): El nombre de la clase (Repository, Controller, Service) que provee la lista inicial o de opciones para la tabla.
- `method` (String): El nombre del método en `source` que retorna la información (`List<T>`).
- `filter` (String): (Opcional) Permite aplicar un filtro a la consulta.

## Uso Básico

```java
@JettraViewModel
public class FacturaModel {
    private Long idFactura;
    private LocalDate fechaEmision;

    @ViewDataTable(row="productoId, precio, cantidad, total", 
                   editablerow="productoId, cantidad", 
                   source="FacturaRepository", 
                   method="getLineas")
    private List<LineaFacturaModel> lineaFacturaModel;

    // getters y setters...
}
```

En este ejemplo, la tabla renderizará las columnas `productoId`, `precio`, `cantidad` y `total`. Los campos `productoId` y `cantidad` se generarán como campos editables (por ejemplo, `TextBox` o `SelectOne` si están anotados con `@ViewSelectOne` en el modelo anidado). Además, incluye automáticamente un botón de operaciones en el encabezado de las filas y permite agregar nuevas líneas de forma dinámica.

## Integración con CrudView

Cuando `@ViewDataTable` se detecta en un campo durante el renderizado de `@CrudView`, el motor `JettraWUI` crea de forma autónoma una sub-tabla dentro de la tarjeta de edición. 

Los componentes internos generados respetan las anotaciones del modelo detallado (por ejemplo `@ViewSelectOne` o `@Hidden` en `LineaFacturaModel`).


## Cuando se usa en Master-Details
Se puede especificarshowRowInMasterTable:
 Cuando se usa en un formulario Master-details en la tabla maestra donde
     * se anexa de manera predeterminada no se muestra. Si lo cambia a true
     * se muestra la tabla dentro de la otra tabla

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

    @ViewDataTable(showRowInMasterTable = false,row="productoId, precio, cantidad, total", editablerow="productoId, cantidad", source="FacturaRepository", method="getLineas")
    @PropertiesLabel(value = "factura.lineas", label = "Detalle de Líneas")
    private List<LineaFacturaModel> lineaFacturaModel;
```

No muestra el datatable correspondiente a LineaFacturaModel, en la tabla maestra de Factura, si no que esta se habilita mediante jun modal donde se puede interactuar con ella.

En El componente ViewDatatable.java debe añadirse la propiedad editableRowMaster que demanera prederterminada esta en true, si se coloca en false las columnas del datatable master no 