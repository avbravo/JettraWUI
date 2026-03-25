# Soporte MVC (Model View Controller) en JettraWUI

JettraWUI ahora incluye soporte nativo para el patrón MVC y Binding automático de datos. Esto permite que los componentes visuales se sincronicen con un modelo de datos (ViewModel) de forma bidireccional sin intervención manual constante.

## Anotaciones Principales

### @JettraViewModel
Se utiliza para marcar una clase POJO como un modelo de vista.

```java
@JettraViewModel
public class Pais {
    private String name;
    private String code;
    // Getters y Setters
}
```

### @InjectViewModel
Se utiliza dentro de una `Page` para inyectar una instancia del ViewModel. JettraWUI se encargará de inicializar esta instancia y realizar el binding.

```java
public class PaisPage extends DashboardBasePage {
    @InjectViewModel
    Pais pais;
    // ...
}
```

## Funcionamiento del Binding

1. **De Modelo a Vista (GET)**: Durante el renderizado de la página, JettraWUI busca componentes cuyo atributo `name` coincida con el nombre de un campo en el `@InjectViewModel`. Si hay coincidencia, el valor del campo se asigna automáticamente a la propiedad `value` del componente.
2. **De Vista a Modelo (POST)**: Al recibir una petición POST, se puede llamar a `JettraMVC.updateModelFromRequest(this, formParams)` para poblar automáticamente los campos del ViewModel con los valores enviados desde el formulario.

## Ejemplo Completo de CRUD

### 1. Definir el Modelo
```java
package com.jettra.example.model;
import io.jettra.wui.core.annotations.JettraViewModel;

@JettraViewModel
public class Pais {
    private String code;
    private String name;
    
    public Pais() {}
    public Pais(String code, String name) {
        this.code = code;
        this.name = name;
    }
    // Getters y Setters...
}
```

### 2. Implementar la Página con Binding
```java
public class PaisPage extends DashboardBasePage {
    @InjectViewModel
    Pais pais;

    @Override
    protected void initCenter(Center center, String username) {
        Form form = new Form("paisForm", "/pais");
        
        // El nombre "code" vincula este TextBox con pais.code
        TextBox txtCode = new TextBox("text", "code"); 
        form.add(txtCode);
        
        // El nombre "name" vincula este TextBox con pais.name
        TextBox txtName = new TextBox("text", "name");
        form.add(txtName);

        form.add(new Button("Guardar").setProperty("type", "submit"));
        center.add(form);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            Map<String, String> params = parseRequestBody(exchange);
            
            // Sincronización automática de Form -> ViewModel
            JettraMVC.updateModelFromRequest(this, params);
            
            // 'pais' ya tiene los datos, proceder a guardar
            PaisRepository.save(pais);
        }
        // ... renderizado normal
    }
}
```

## Ventajas
- **Menos código repetitivo**: No es necesario hacer `formParams.get("nombre")` campo por campo.
- **Sincronización automática**: Al editar, los campos se llenan solos si el ViewModel tiene datos.
- **Mantenibilidad**: El modelo de datos es la única fuente de verdad.
