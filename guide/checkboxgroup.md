# CheckBoxGroup

El componente `CheckBoxGroup` facilita el manejo de múltiples componentes `CheckBox` bajo un mismo grupo (atributo `name`). Aunque en HTML los checkboxes no requieren estrictamente compartir un `name` para funcionar (a diferencia de los radio buttons), agruparlos bajo el mismo `name` es muy útil para enviar arreglos de datos en formularios (e.g. `roles[]`).

## Características
- Actúa como un contenedor con `role="group"`.
- Asigna recursivamente el mismo atributo `name` a todos los componentes `CheckBox` que se añadan dentro de él, ya sea directa o indirectamente (por ejemplo, si están envueltos en un div).

## Uso Básico

```java
import io.jettra.wui.components.CheckBoxGroup;
import io.jettra.wui.components.CheckBox;
import io.jettra.wui.components.Label;
import io.jettra.wui.core.UIComponent;

// 1. Crear el grupo definiendo su "name"
CheckBoxGroup privilegesGroup = new CheckBoxGroup("privileges");

// 2. Crear los checkboxes (envueltos en contenedores flexibles)
UIComponent check1 = new UIComponent("div").setStyle("display", "flex").setStyle("align-items", "center").setStyle("gap", "8px");
check1.add(new CheckBox().setProperty("value", "read"));
check1.add(new Label("Read"));

UIComponent check2 = new UIComponent("div").setStyle("display", "flex").setStyle("align-items", "center").setStyle("gap", "8px");
check2.add(new CheckBox().setProperty("value", "write"));
check2.add(new Label("Write"));

// 3. Añadir los wrappers al grupo. Automáticamente se les asignará el name="privileges"
privilegesGroup.addCheckBox(check1);
privilegesGroup.addCheckBox(check2);
```

## CSS y Estilos
Por defecto, el componente se inicializa con la clase `j-checkboxgroup` y con una disposición flexible en columna (`flex-direction: column`, `gap: 10px`).
Puedes alterar estos estilos usando los métodos estándar `.setStyle(...)` o `.addClass(...)`.
