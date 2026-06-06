# @ModelToRecordConversor

La anotación `@ModelToRecordConversor` es una potente herramienta basada en **Java Annotation Processing (JAP)** diseñada para generar automáticamente clases conversoras en tiempo de compilación entre tus modelos visuales (`Model`) y tus entidades de datos (`Record`). Esto elimina por completo el código repetitivo de conversión manual (boilerplate).

## ¿Cómo funciona?

Cuando el compilador encuentra una clase modelo anotada con `@ModelToRecordConversor`, el procesador `ModelToRecordProcessor` analiza sus atributos y genera una clase utilitaria de conversión bajo el patrón de nombre `{RecordClassName}RecordModelConverter` en el mismo paquete que el modelo. 

Esta clase generada incluye la anotación `@ApplicationScoped` para su inyección/uso modular y proporciona métodos eficientes `toModel` y `toRecord`.

---

## Parámetros de la Anotación

- **goal**: Especifica la clase `Record` destino (por ejemplo, `goal = Persona.class`). 
  - Si **no se especifica** el parámetro `goal`, el framework asume de forma predeterminada que el nombre del Record destino se corresponde al nombre del modelo eliminando la palabra "Model" al final de la definición. Por ejemplo, para un modelo llamado `UsuarioModel`, el Record destino deducido será `Usuario`.

---

## Ejemplo Completo de Integración

### 1. La Entidad Record (Destino)
```java
package com.jettra.example.entity;

public record Persona(String id, String name) {
}
```

### 2. El Modelo Visual con Mapeo
```java
package com.jettra.example.model;

import io.jettra.wui.core.annotations.JettraViewModel;
import io.jettra.wui.core.annotations.ModelToRecordConversor;
import io.jettra.wui.core.annotations.PropertiesInRecord;
import com.jettra.example.entity.Persona;

@JettraViewModel
@ModelToRecordConversor(goal = Persona.class)
public class PersonaModel {
    
    @PropertiesInRecord(field = "id")
    private String id;
    
    @PropertiesInRecord(field = "name")
    private String nombre;

    public PersonaModel() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
```

### 3. Clase Conversora Generada (`PersonaRecordModelConverter.java`)
El procesador genera este archivo automáticamente en `target/generated-sources/annotations`:
```java
package com.jettra.example.model;

import com.jettra.example.entity.Persona;
import jettra.scoped.ApplicationScoped;

@ApplicationScoped
public class PersonaRecordModelConverter {

    /**
     * Convierte de Persona (Record) a PersonaModel
     */
    public PersonaModel toModel(Persona record) {
        if (record == null) {
            return null;
        }
        PersonaModel model = new PersonaModel();
        model.setId(record.id());
        model.setNombre(record.name());
        return model;
    }

    /**
     * Convierte de PersonaModel a Persona (Record)
     */
    public Persona toRecord(PersonaModel model) {
        if (model == null) {
            return null;
        }
        return new Persona(
            model.getId(),
            model.getNombre()
        );
    }
}
```

---

## Integración con @CrudView y Capa de Persistencia

Cuando creas una vista CRUD utilizando la anotación `@CrudView` apuntando directamente a un repositorio de persistencia (que opera sobre Records), el procesador `@CrudViewProcessor` detecta la presencia de `@ModelToRecordConversor` en el modelo y genera automáticamente toda la lógica de conversión dentro de la clase manejadora (`CrudHandler`).

### Definición de la Página
```java
@CrudView(
    model = PersonaModel.class, 
    repository = PersonaRepository.class, 
    editable = true
)
public class PersonaPage extends Page {
    public PersonaPage() {
        super("Gestión de Personas");
    }
}
```

### Código Manejador Generado Automáticamente
```java
public class PersonaPageCrudHandler implements CrudHandler<PersonaModel> {
    // Instancia el conversor autogenerado
    private final PersonaRecordModelConverter converter = new PersonaRecordModelConverter();

    @Override
    public List<PersonaModel> findAll() {
        // Convierte el listado de Records devueltos por el repositorio en Modelos
        return PersonaRepository.findAll().stream()
            .map(converter::toModel)
            .collect(Collectors.toList());
    }

    @Override
    public void save(PersonaModel model) {
        // Convierte el Modelo UI a Record antes de persistirlo en el repositorio
        PersonaRepository.save(converter.toRecord(model));
    }

    @Override
    public void delete(String id) {
        PersonaRepository.delete(id);
    }
    
    // ... otros métodos del ciclo de vida CRUD
}
```

Para obtener más información sobre cómo personalizar el mapeo de atributos individuales, lee la guía de [@PropertiesInRecord](modelproperties.md).
