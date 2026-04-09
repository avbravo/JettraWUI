# Componente SelectMany

El componente `SelectMany` representa el elemento HTML estándar `<select multiple>`, ajustado y estilizado globalmente dentro del ecosistema JettraWUI con el tema visual "Cyberpunk 3D / Glassmorphism".

Al igual que un `SelectOne`, pero acepta selecciones múltiples a través del arrastre de puntero nativo o usando `Ctrl + Click` o `Shift + Click`.

## API: Clase Java

```java
package io.jettra.wui.components;

/**
 * Hereda de UIComponent y genera una etiquta <select multiple>.
 */
public class SelectMany extends UIComponent;
```

## Constructor

1. `SelectMany(String name)`
   Crea y devuelve un elemento `SelectMany` enlazado con la propiedad de envío indicada. Automáticamente agrega el identificador de clase interna `j-select-many` y hereda los estilos unificados de componentes.

## Métodos Destacados

- `addOption(String value, String label)`: Permite añadir elementos anidados de etiqueta `<option>` al selector actual.

## Ejemplo Básico de Uso (Back-end)

```java
SelectMany multipleSelection = new SelectMany("ciudadesVisitar");

multipleSelection.addOption("PTY", "Panamá");
multipleSelection.addOption("MAD", "Madrid");
multipleSelection.addOption("SJO", "San José");
multipleSelection.addOption("BOG", "Bogotá");

// Establecer dimensiones opcionales
multipleSelection.setStyle("width", "300px");
multipleSelection.setStyle("height", "150px");

container.add(multipleSelection);
```

> **Nota Adicional:** El componente ya contiene un atributo `multiple="true"`, permitiendo su recolección de formulario como un arreglo de valores mediante procesamiento POST o binding bidireccional dependiendo del Model y controlador.
