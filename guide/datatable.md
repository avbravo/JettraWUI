# Datatable Component

El componente `Datatable` de JettraWUI es una tabla avanzada que permite la integración de funcionalidad interactiva en la interfaz del cliente. Este componente incluye, de manera automática:
- Campo de Búsqueda (Filtro) para todo el contenido de la tabla.
- Paginación del lado del cliente (saltos de página "Anterior" y "Siguiente").
- Soporte moderno a través de componentes simplificados como `Row` y `TD`.

## Uso y Componentes Asociados

Para utilizar `Datatable`, apóyate en las clases estructurales `Row` (fila - `<tr>`) y `TD` (celda - `<td>`).

### Instanciación y Estructura (Fluent API)

```java
import io.jettra.wui.components.Row;
import io.jettra.wui.components.TD;
import io.jettra.wui.complex.Datatable;

Datatable dt = new Datatable()
    .addHeaderRow("ID", "Nombre", "Rol", "Acciones")
    .setId("userTable");

for (Usuario u : usuarios) {
    dt.addRow(new Row(
        new TD(u.getId()),
        new TD(u.getName()),
        new TD(u.getRole()),
        new TD().add(new Button("Edit").addClass("j-btn"))
    ));
}
```

### Editor Web (WebDesignerPage)
En el Jettra Web Designer, el componente `Datatable` permite diseño "Drag & Drop" intuitivo:
1. Arrastra `Datatable` al Canvas.
2. Usando el panel inspector, añade, nombra y elimina tantas columnas como desees (p. ej. "Col 1", "Col 2").
3. Directamente dentro del Canvas, se ha habilitado que las áreas de las celdas (dentro de las filas generadas automáticamente) admitan soltar componentes visuales con facilidad. ¡Puedes arrastrar `Button`, `SelectOne`, `TextBox`, `CheckBox` y cualquier otro componente para introducirlos fácilmente dentro de las filas! El generador automáticamente creará el código `java` anidándolos dentro de `TD`.


### Funciones Interactivas Automáticas

Al agregar un `Datatable`, JettraWUI automáticamente incrusta la lógica JavaScript:
1. **Filtro de búsqueda**: El *input* desplegado arriba de la tabla examina en tiempo real todo el texto de los componentes `TD` añadidos. Solo revelará las filas exactas omitiendo el resto.
2. **Paginación limitable**: Se limita el número de filas visibles por defecto a la cantidad que se asigne en el script interno (usualmente 5), con botones "Siguiente" y "Anterior" generados en la parte inferior para saltar mediante *slots* de datos sin refrescar la página. También mostrará la cantidad de registros que están filtrados y totales.

### Datatable Editable

JettraWUI soporta la creación de tablas editables nativamente. Dado que las celdas `TD` aceptan cualquier `UIComponent`, puedes integrar componentes como `TextBox`, `SelectOne` y `DatePicker` dentro de una tabla. 

A través de las propiedades de eventos como `onchange` o `oninput`, es posible capturar los cambios y actualizar elementos en tiempo real.

```java
import io.jettra.wui.components.Row;
import io.jettra.wui.components.TD;
import io.jettra.wui.components.TextBox;
import io.jettra.wui.components.SelectOne;
import io.jettra.wui.complex.Datatable;

Datatable table = new Datatable();
table.addHeaderRow("Artículo", "Precio", "Cantidad", "Total");

for(int i = 0; i < 3; i++) {
    Row row = new Row();
    row.setProperty("id", "row_" + i);
    
    // Celda de solo lectura
    row.add(new TD("Producto " + i));
    
    // Columna editable: Precio
    TextBox priceBox = new TextBox("number", "price_" + i);
    priceBox.setId("price_" + i);
    priceBox.setProperty("value", "10.00");
    priceBox.setProperty("oninput", "updateRow(" + i + ")");
    row.add(new TD().add(priceBox));
    
    // Columna editable: Cantidad
    TextBox qtyBox = new TextBox("number", "qty_" + i);
    qtyBox.setId("qty_" + i);
    qtyBox.setProperty("value", "1");
    qtyBox.setProperty("oninput", "updateRow(" + i + ")");
    row.add(new TD().add(qtyBox));
    
    // Total calculado (Actualizado vía JavaScript)
    TD totalTd = new TD("10.00");
    totalTd.setId("total_" + i);
    row.add(totalTd);
    
    table.addRow(row);
}
```

Para dar vida a esta tabla, basta con inyectar el código JavaScript utilizando `io.jettra.wui.components.Script` que defina la función `updateRow(index)`, calculando los valores y asignándolos de regreso a los elementos mediante sus IDs. Un ejemplo completo se puede encontrar en `DatatableEditablePage.java`.
