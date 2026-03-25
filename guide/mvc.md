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

## Prácticas Recomendadas: "Reactive MVC"

Para minimizar el uso de JavaScript manual y aprovechar al máximo el motor de binding de JettraWUI, se recomienda el siguiente flujo:

### 1. Manejo del Estado en el Servidor
En lugar de usar JS para limpiar o llenar inputs, usa parámetros de URL (`?action=edit&code=XX`) y sincroniza el ViewModel en el método `handle()`:

```java
if ("edit".equals(action)) {
    Pais p = repository.find(code);
    this.pais.setCode(p.getCode()); // El ViewModel ya tiene los datos
    this.pais.setName(p.getName());
}
```

### 2. Auto-Población de Componentes
Dado que `JettraMVC.updateViewFromModel` escanea todo el árbol de componentes, los `TextBox` con el atributo `name` correcto se llenarán automáticamente al renderizar:

```java
TextBox input = new TextBox("text", "code"); // Bindeado a pais.code
```

### 3. Script Mínimo para Visibilidad
Solo necesitas una línea de JS inyectada dinámicamente para abrir el modal si hay una acción activa:

```java
if (action != null) {
    this.add(new Script("document.getElementById('modal').style.display='flex';"));
}
```

## Beneficios
- **Código Java Limpio**: No necesitas concatenar largas cadenas de JavaScript.
- **Seguridad**: Los datos se procesan y validan en el servidor antes de renderizarse.
- **Sincronización Total**: El ViewModel es siempre la única fuente de verdad (Single Source of Truth).
