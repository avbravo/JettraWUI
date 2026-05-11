# @PropertiesLabel

La anotación `@PropertiesLabel` permite definir etiquetas para los atributos del ViewModel de manera que se puede añadir la descripción de un atributo de forma declarativa.

## Uso

Aplica la anotación a los campos de tu clase ViewModel:

```java
public class PaisModel {
   @NotNull
   @Size(min = 2, max = 5)
   @PropertiesLabel(value = "pais.code", label = "Código")
   private String code;
  
   @NotNull
   @Size(min = 3, max = 100)
   @PropertiesLabel(value = "pais.name", label = "Nombre")
   private String name;
}
```

## Atributos

- **value**: La clave en el archivo de propiedades (ej. `messages_es.properties`).
- **label**: El valor por defecto que se usará si la clave no se encuentra en el archivo de propiedades.

Esta anotación es utilizada automáticamente por el componente `CrudView` para generar los encabezados de las tablas y las etiquetas de los formularios.
