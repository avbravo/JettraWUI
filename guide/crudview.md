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
- **editable**: Convierte el listado de datos en una tabla (datatable) con campos editables en línea para el modelo proporcionado. Por defecto es `false`.
- **report**: Activa el botón de impresión en la vista. Por defecto es `false`.
- **reportShowViewer**: Muestra un visor de reporte antes de generar el archivo. Por defecto es `true`.
- **reportAllowPrint**: Habilita el botón imprimir en el visor. Por defecto es `true`.
- **reportAllowPdf**: Habilita el botón de exportación PDF en el visor. Por defecto es `true`.
- **reportAllowExcel**: Habilita el botón de exportación Excel en el visor. Por defecto es `true`.
- **reportAllowCsv**: Habilita el botón de exportación CSV en el visor. Por defecto es `true`.

## Funcionamiento Interno

1.  **Generación de Handler**: El `CrudViewProcessor` escanea las clases anotadas y genera un archivo fuente Java que implementa `CrudHandler<M>`.
2.  **Inyección**: Al procesar la página, `JettraMVC` busca la clase generada por nombre (`PageClassName + "CrudHandler"`).
3.  **Operaciones**:
    - **Listado**: Usa `handler.findAll()` para obtener los datos.
    - **Guardado**: Usa `handler.save(model)` para persistir cambios.
    - **Eliminación**: Usa `handler.delete(id)` para remover registros.
4.  **Sincronización**: Mantiene la integración con `JettraSyncManager` para notificaciones en tiempo real.

## Integración con JettraReport

La interfaz generada por `@CrudView` está completamente integrada de forma nativa con **JettraReport**. Cuando la anotación incluye opciones de reporte (ej. `report = true`), JettraWUI se encarga automáticamente de generar un botón **🖨️ Imprimir** que gestiona:

1.  **Instanciación Dinámica**: A través de Reflection, `CrudView` detecta e instancia dinámicamente el visor HTML interactivo nativo de JettraReport (`ReportViewer`). Esto evita dependencias circulares y mantiene los módulos desacoplados.
2.  **Generación de la Interfaz del Visor**: El visor nativo muestra una vista previa del reporte en el navegador. Transforma las secciones configuradas (Header, Detail/Columnas derivadas del Modelo) en componentes HTML (Tablas, Párrafos) de JettraWUI.
3.  **Gestión de Exportación**: Dependiendo de las variables `reportAllowPdf`, `reportAllowExcel`, `reportAllowCsv` y `reportAllowPrint`, el toolbar superior del visor habilitará los respectivos botones de exportación o impresión.
4.  **Redimensionamiento**: El visor incrustado posee la opción de escalar su tamaño visual dinámicamente en pantalla (p. ej. a 50%, 75%, Original o Maximizar al 100%) a través de un control `<select>` integrado en la barra de herramientas del diálogo de previsualización.

## DataTables Editables

Puedes activar la edición en línea de los registros usando el atributo `editable = true`. Cuando este atributo está activo, las celdas del datatable no renderizarán simple texto estático, sino controles interactivos (`TextBox`, `SelectOne`, etc.) de JettraWUI, que permiten manipular los datos directamente desde la vista del listado. 

- El framework detecta las anotaciones `@NoEditable`, `@Hidden` y `@Compute`, ajustando la visibilidad y editabilidad de los campos automáticamente.
- El color del texto en campos de solo lectura se ajusta automáticamente para garantizar contraste y legibilidad (usando `var(--jettra-text)`).

## Soporte para Reglas de Negocio (@Rules)

`CrudView` integra el motor de **JettraRules** para proporcionar validaciones en tiempo real tanto en los formularios modales como en los DataTables editables.

- Las anotaciones `@Rules` definidas en el modelo se traducen automáticamente a lógica JavaScript inyectada en la vista.
- Soporta operadores como `greater`, `lessorequals`, `less`, `greaterorequals` y `equals`.
- Proporciona retroalimentación visual inmediata (bordes rojos) y bloquea el envío de formularios si hay violaciones de reglas.
- Se integra con `@Compute` para revalidar reglas automáticamente cuando un campo calculado cambia.

### Ejemplo de Reglas en Modelo
```java
public class ReglasModel {
    @Rules(apply="greater", than="0", message="Saldo debe ser positivo")
    private Double saldo;

    @Rules(apply="lessorequals", than="saldo", message="Descuento excedido")
    private Double descuento;
}
```

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

