# @NoEditable

La anotación `@NoEditable` se utiliza en el contexto del diseñador `@CrudView` de JettraWUI para indicar que un campo de un modelo debe ser renderizado como un elemento de solo lectura (no editable) en la interfaz de usuario.

Cuando se aplica esta anotación, `CrudView` genera una caja de texto (`TextBox`) con la propiedad `readonly` activada, junto con un estilo visual que indica claramente que el campo no puede ser modificado por el usuario (fondo gris y cursor de 'no permitido').

## Ejemplo de uso

En el siguiente ejemplo, el campo `apodo` se marca como `@NoEditable`:

```java
import io.jettra.wui.core.annotations.NoEditable;

public class EjemploModel {
    
    private String name;
    
    @NoEditable
    private String apodo;
    
    // Getters y Setters...
}
```

### Comportamiento en la Vista

Al usar una página que implementa `@CrudView` con este modelo:
1. El campo `name` será un `TextBox` estándar que permite al usuario ingresar texto.
2. El campo `apodo` se dibujará como un `TextBox` no editable, con el valor de fondo adecuado para mantener la legibilidad pero bloqueando la entrada de usuario.

Esto es útil para campos que son calculados, generados por el sistema, o que solo deben ser vistos y no alterados desde este formulario específico.
