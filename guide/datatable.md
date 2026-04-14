# Datatable Component

El componente `Datatable` de JettraWUI es una tabla avanzada que permite la integración de funcionalidad interactiva en la interfaz del cliente. Este componente incluye, de manera automática:
- Campo de Búsqueda (Filtro) para todo el contenido de la tabla.
- Paginación del lado del cliente (saltos de página "Anterior" y "Siguiente").
- Soporte moderno a través de componentes simplificados como `Row` y `TD`.

## Uso y Componentes Asociados

Para utilizar `Datatable`, apóyate en las clases estructurales `Row` (fila - `<tr>`) y `TD` (celda - `<td>`).

### Instanciación y Estructura

```java
import io.jettra.wui.components.Row;
import io.jettra.wui.components.TD;
import io.jettra.wui.complex.Datatable;

Datatable dt = new Datatable();

// 1. Agregar Encabezado (Sintaxis simplificada y fluida)
dt.addHeaderRow("ID", "Nombre", "Rol");

// Opcional: Sintaxis con Row y TD si deseas aplicar estilos especiales al encabezado
// dt.addHeaderRow(new Row(new TD("ID"), new TD("Nombre"), new TD("Rol")));

// 2. Agregar Filas

for (int i = 0; i < usuarios.size(); i++) {
    Row row = new Row();
    row.add(new TD(usuarios.get(i).getId()));
    row.add(new TD(usuarios.get(i).getFileName()));
    row.add(new TD(usuarios.get(i).getRole()));
    
    dt.addRow(row);
}

// Opcionalmente, se puede utilizar el estilo Fluent API:
// new Datatable()
//     .addHeaderRow("ID", "Nombre")
//     .addRow(new Row(new TD("1"), new TD("John Doe")));
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
