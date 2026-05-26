# @PropertiesInRecord

La anotación `@PropertiesInRecord` se utiliza en conjunto con `@ModelToRecordConversor`. Le indica al procesador de anotaciones qué atributos del `Record` corresponden a los campos del `Model`.

## Uso

Aplica la anotación sobre los campos de tu clase Modelo:

```java
import io.jettra.wui.core.annotations.ModelToRecordConversor;
import io.jettra.wui.core.annotations.PropertiesInRecord;

@JettraViewModel
@ModelToRecordConversor(goal = Persona.class)
public class PersonaModel {
    
    @PropertiesInRecord(field = "id")
    private String identificacion;
    
    @PropertiesInRecord(field = "name")
    private String nombre;

    @PropertiesInRecord
    private Integer edad;
}
```

Para este modelo, se asume un `Record` similar a:
```java
public record Persona(String id, String name, Integer edad) {}
```

### Atributos

- **field**: Especifica el nombre exacto del campo en el `Record`. Si no se especifica, se asume que el nombre del atributo en el `Model` es exactamente igual al nombre en el `Record` (como en el caso del campo `edad` en el ejemplo anterior).

## Cómo Funciona

El `ModelToRecordProcessor` leerá todos los campos anotados con `@PropertiesInRecord` dentro del modelo. 
Al generar el método `toModel()`, utilizará el nombre definido en `field` para extraer el valor del Record e inyectarlo en el modelo usando los métodos _setter_.
Al generar el método `toRecord()`, extraerá el valor del modelo usando sus _getters_ y lo pasará al constructor del Record en el mismo orden en que fueron definidos en la clase.
