# @PropertiesInRecord

La anotación `@PropertiesInRecord` se utiliza en conjunto con la anotación `@ModelToRecordConversor` para definir y personalizar el mapeo de atributos individuales entre un modelo visual (`Model`) y una entidad de datos estructurada (`Record`).

---

## Propiedades y Atributos

- **field**: Especifica el nombre exacto de la propiedad en la clase `Record` destino.
  - **Por defecto (`""`)**: Si no se especifica el campo `field` de manera explícita, el procesador asumirá que la propiedad en el `Record` tiene exactamente el mismo nombre que la variable del atributo en el `Model`.

---

## Comportamiento del Procesador

1. **De Record a Model (`toModel`)**:
   El procesador leerá el valor de la propiedad del Record usando el método de lectura directo `record.{field}()` y lo inyectará en el modelo llamando al correspondiente método _setter_ `model.set{Atributo}()` (con la primera letra en mayúscula).

2. **De Model a Record (`toRecord`)**:
   El procesador llamará a los métodos _getter_ correspondientes de la clase modelo para suministrarlos al constructor de la clase `Record`.
   - Si el atributo es de tipo primitivo `boolean`, buscará y utilizará el método `is{Atributo}()`.
   - Para cualquier otro tipo, utilizará el método estándar `get{Atributo}()`.
   - **Nota de diseño importante**: Las variables se pasan al constructor del `Record` en el **mismo orden secuencial** en el que se declaran los campos anotados dentro de la clase `Model`.

---

## Ejemplo Práctico de Mapeo

### Definición del Record de Datos
```java
public record Persona(String id, String name, Integer age, boolean active) {
}
```

### Clase Model con Anotaciones de Mapeo
```java
package com.jettra.example.model;

import io.jettra.wui.core.annotations.JettraViewModel;
import io.jettra.wui.core.annotations.ModelToRecordConversor;
import io.jettra.wui.core.annotations.PropertiesInRecord;
import com.jettra.example.entity.Persona;

@JettraViewModel
@ModelToRecordConversor(goal = Persona.class)
public class PersonaModel {
    
    // Mapea la variable "id" al campo "id" en el Record (mismo nombre)
    @PropertiesInRecord
    private String id;
    
    // Mapea la variable "nombreCompleto" al campo "name" en el Record
    @PropertiesInRecord(field = "name")
    private String nombreCompleto;

    // Mapea la variable "edad" al campo "age" en el Record
    @PropertiesInRecord(field = "age")
    private Integer edad;

    // Mapea el boolean "activo" al campo "active" en el Record
    @PropertiesInRecord(field = "active")
    private boolean activo;

    public PersonaModel() {}

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
```

### Código de Conversión Generado por JAP
El procesador generará el siguiente código basándose en la configuración anterior:
```java
package com.jettra.example.model;

import com.jettra.example.entity.Persona;
import jettra.scoped.ApplicationScoped;

@ApplicationScoped
public class PersonaRecordModelConverter {

    public PersonaModel toModel(Persona record) {
        if (record == null) {
            return null;
        }
        PersonaModel model = new PersonaModel();
        
        // Mapeo automático de propiedades
        model.setId(record.id());                 // Mismo nombre
        model.setNombreCompleto(record.name());    // Mapeo personalizado: name -> nombreCompleto
        model.setEdad(record.age());              // Mapeo personalizado: age -> edad
        model.setActivo(record.active());          // Mapeo personalizado: active -> activo
        
        return model;
    }

    public Persona toRecord(PersonaModel model) {
        if (model == null) {
            return null;
        }
        
        // El constructor del Record se llama usando los métodos getters del modelo
        // respetando el orden secuencial de declaración en la clase PersonaModel:
        // 1. id (getId)
        // 2. nombreCompleto (getNombreCompleto)
        // 3. edad (getEdad)
        // 4. activo (isActivo - uso especial de 'is' para booleans)
        return new Persona(
            model.getId(),
            model.getNombreCompleto(),
            model.getEdad(),
            model.isActivo()
        );
    }
}
```

Para una comprensión más amplia de cómo estas clases conversoras se acoplan con el resto de la arquitectura del framework, consulta la guía de [@ModelToRecordConversor](recordtomodel.md).
