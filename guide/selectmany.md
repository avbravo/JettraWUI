# Componente SelectMany

El componente `SelectMany` representa el elemento HTML estándar `<select multiple>`, ajustado y estilizado globalmente dentro del ecosistema JettraWUI. Ha sido reformulado para ser totalmente consistente con `SelectOne`.

Acepta selecciones múltiples a través del arrastre de puntero nativo o usando `Ctrl + Click` o `Shift + Click`.

## API: Clase Java

```java
package io.jettra.wui.components;

/**
 * Hereda de SelectOne y genera una etiqueta <select multiple>.
 */
public class SelectMany extends SelectOne;
```

## Constructor

1. `SelectMany(String name)`
   Crea y devuelve un elemento `SelectMany` enlazado con la propiedad de envío indicada. Automáticamente agrega el identificador de clase interna `j-select-many` y el atributo `multiple="multiple"`.

## Métodos Destacados

- `addOption(String value, String label)`: Permite añadir elementos anidados de etiqueta `<option>` al selector actual (heredado de `SelectOne`).
- `setDefault(String value)`: Establece el valor por defecto que estará seleccionado. Si no se especifica ninguno, se seleccionará automáticamente el primer elemento.
- `setInline(boolean inline)`: Define si el componente se expande para ocupar todo el ancho del contenedor.

## Ejemplo Básico de Uso

```java
SelectMany multipleSelection = new SelectMany("ciudadesVisitar");

multipleSelection.addOption("PTY", "Panamá");
multipleSelection.addOption("MAD", "Madrid");
multipleSelection.addOption("SJO", "San José");
multipleSelection.addOption("BOG", "Bogotá");

// Establecer valor por defecto
multipleSelection.setDefault("MAD");

// Configuración visual
multipleSelection.setStyle("width", "300px");
multipleSelection.setStyle("height", "150px");

container.add(multipleSelection);
```

> **Nota Adicional:** El componente contiene el atributo `multiple="multiple"`, lo que permite capturar múltiples valores en el envío de formularios. Si no se establece un valor por defecto mediante `setDefault()`, el navegador (o el renderizado de Jettra) seleccionará el primer elemento de la lista.
