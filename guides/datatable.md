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

// 1. Agregar Encabezado
Row header = new Row(new TD("ID"), new TD("Nombre"), new TD("Rol"));
dt.addHeaderRow(header);

// 2. Agregar Filas
for (int i = 0; i < usuarios.size(); i++) {
    Row row = new Row();
    row.add(new TD(usuarios.get(i).getId()));
    row.add(new TD(usuarios.get(i).getFileName()));
    row.add(new TD(usuarios.get(i).getRole()));
    
    dt.addRow(row);
}
```

### Funciones Interactivas Automáticas

Al agregar un `Datatable`, JettraWUI automáticamente incrusta la lógica JavaScript:
1. **Filtro de búsqueda**: El *input* desplegado arriba de la tabla examina en tiempo real todo el texto de los componentes `TD` añadidos. Solo revelará las filas exactas omitiendo el resto.
2. **Paginación limitable**: Se limita el número de filas visibles por defecto a la cantidad que se asigne en el script interno (usualmente 5), con botones "Siguiente" y "Anterior" generados en la parte inferior para saltar mediante *slots* de datos sin refrescar la página. También mostrará la cantidad de registros que están filtrados y totales.
