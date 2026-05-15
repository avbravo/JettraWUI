# @Hidden

La anotación `@Hidden` se utiliza en el contexto del diseñador `@CrudView` de JettraWUI para indicar que un campo de un modelo debe permanecer oculto en la interfaz de usuario. 

Cuando se aplica esta anotación a un atributo, `CrudView` no dibujará un elemento visible para ese campo. En su lugar, se inserta un elemento de tipo `hidden` dentro del formulario y la fila o grupo asociado no es visible, asegurando que el valor del campo se conserve durante las operaciones pero permanezca invisible para el usuario.

## Ejemplo de uso

En el siguiente ejemplo, el campo `apodo` se marca como `@Hidden`:

```java
import io.jettra.wui.core.annotations.Hidden;

public class EjemploModel {
    
    private String name;
    
    @Hidden
    private String apodo;
    
    // Getters y Setters...
}
```

### Comportamiento en la Vista

Al generar la página web con `@CrudView`:
1. El campo `name` será un `TextBox` visible y editable de forma regular.
2. El campo `apodo` se generará como un elemento oculto (`<input type="hidden">`) en lugar de un `TextBox` visible, y la etiqueta descriptiva del campo también se ocultará de la vista.

Esta anotación es ideal para campos técnicos, IDs internos, tokens o información que necesita viajar junto con el formulario pero no debe ser visualizada ni alterada directamente por los usuarios en la pantalla del CRUD.
