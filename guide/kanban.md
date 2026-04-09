# Componente Board (Kanban)

El ecosistema JettraWUI provee facilidades para la creación de interfaces de tipo Kanban o Tableros interactivos (Boards) mediante el uso combinado de varios componentes estructurales y visuales de diseño Cyberpunk 3D / Glassmorphism.

## Componentes Clave Involucrados

1. **Board (Tablero Base)**: Consiste en la superficie principal o el contenedor padre. Suele ser un componente `Div` o `Panel` con estilo display `flex` o CSS Grid.
2. **Columnas (Draggable Areas)**: Representan los estados de un flujo (Ej. Todo, In Progress, Done). Se construyen típicamente utilizando `Div` configurados con bordes glassmorphism y control de scroll vertical.
3. **Card (Tarjetas)**: El componente principal de contenido. Las tarjetas representan las tareas o ítems que se mueven entre las columnas. En JettraWUI, el componente `Card` permite la personalización con títulos, subtítulos e imágenes.
4. **Header / Typography**: Utilizado para nombrar las columnas o mostrar contadores gráficos.
5. **Button**: Acciones (Ej: "Añadir tarea") o acciones internas de las tarjetas Kanban.

## Ejemplo Básico de Estructuración

```java
import io.jettra.wui.components.Card;
import io.jettra.wui.components.Div;
import io.jettra.wui.components.Header;

// Contenedor principal del Kanban
Div kanbanBoard = new Div();
kanbanBoard.setStyle("display", "flex")
           .setStyle("gap", "20px")
           .setStyle("overflow-x", "auto")
           .setStyle("padding", "20px");

// Columna "Por Hacer"
Div todoColumn = new Div();
todoColumn.setStyle("min-width", "300px")
          .setStyle("background", "var(--jettra-glass)")
          .setStyle("padding", "15px")
          .setStyle("border-radius", "8px")
          .setStyle("border", "1px solid var(--jettra-border)");
          
Header todoHeader = new Header(4, "To Do");
todoColumn.add(todoHeader);

// Tarjeta Ejemplo en To Do
Card task1 = new Card()
    .setTitle("Implementar SelectMany")
    .setSubtitle("Prioridad Alta")
    .setContentText("Añadir HTML <select multiple> con la estética visual 3D.")
    .setWidth("100%");
task1.setStyle("margin-bottom", "15px").setProperty("draggable", "true");

todoColumn.add(task1);

// ... crear más columnas (In progress, Done) ...
kanbanBoard.add(todoColumn);
// kanbanBoard.add(inProgressColumn);
// kanbanBoard.add(doneColumn);
```

## Ajustes de Uso (Drag and Drop HTML5)

Al trabajar con tableros interactivos, las tarjetas deben ser "draggables" (arrastrables) mediante propiedades estándar del navegador (eventos `dragstart`, `dragover`, `drop`). Puedes enlazar estas lógicas inyectando el código de los eventos directamente utilizando `.setProperty("ondragstart", "...")` y validando los eventos a nivel de JavaScript, interactuando con el servidor mediante peticiones asíncronas de guardado.

### Buenas Prácticas
* **Feedback Visual**: Para mejorar el efecto "Cyberpunk", al arrastrar la tarjeta cambia su propiedad CSS a `box-shadow: 0 0 15px #0ff`.
* **Dimensionamiento**: Usar `overflow-x: auto` en el `kanbanBoard` previene que se apile desordenadamente si hay muchas columnas.
* **Componentes 3D**: Todas las Cards deben responder adecuadamente al evento `:hover` para levantarse (eje Z), mantén la clase `j-card-3d` si tienes estilos personalizados inyectados.
