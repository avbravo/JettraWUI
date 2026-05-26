# @ModelToRecordConversor

La anotación `@ModelToRecordConversor` es una herramienta basada en Java Annotation Processing diseñada para generar automáticamente clases conversoras entre tus modelos visuales (`Model`) y tus entidades de datos (`Record`). Esto elimina el código repetitivo de transformación manual (boilerplate).

## Uso

Aplica la anotación a tu clase Modelo:

```java
import io.jettra.wui.core.annotations.ModelToRecordConversor;

@JettraViewModel
@ModelToRecordConversor(goal = Persona.class)
public class PersonaModel {
    // ... campos anotados con @PropertiesInRecord ...
}
```

### Atributos

- **goal**: Especifica la clase `Record` de destino. Si no se especifica el `goal` explícitamente (ej. `@ModelToRecordConversor`), el procesador asumirá que el nombre del Record destino es igual al nombre del modelo eliminando el sufijo "Model". Por ejemplo, para `UsuarioModel`, buscará generar código para `Usuario`.

## Clase Generada

Durante la compilación, el procesador generará una clase llamada `{RecordName}RecordModelConverter` (por ejemplo, `PersonaRecordModelConverter`), la cual incluirá métodos para convertir en ambas direcciones:

```java
@ApplicationScoped
public class PersonaRecordModelConverter {
    public PersonaModel toModel(Persona record) { ... }
    public Persona toRecord(PersonaModel model) { ... }
}
```

## Beneficios

1. **Eficiencia en Compilación**: No se utiliza Reflection en tiempo de ejecución.
2. **Cero Boilerplate**: Las actualizaciones en los campos del modelo o el record solo requieren una compilación para sincronizar los mapeos automáticamente.

Consulta la guía de [@PropertiesInRecord](modelproperties.md) para aprender a mapear atributos específicos.
