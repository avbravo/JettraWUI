# IndexUI Component

El componente `IndexUI` genera la etiqueta `<indexui>`, utilizada para construir páginas principales o "index" personalizadas en aplicaciones Jettra. Este componente proporciona una estructura base para mostrar un diseño atractivo como primera impresión a los usuarios en el navegador.

## Uso Básico

```java
import io.jettra.wui.components.IndexUI;
import io.jettra.wui.components.Header;

IndexUI indexPage = new IndexUI();
indexPage.setId("main-index");
indexPage.add(new Header(1, "Bienvenido a mi Aplicación"));
```

## Propiedades y Métodos Principales

Al extender `UIComponent`, `IndexUI` soporta todos los métodos básicos para añadir clases, estilos y atributos:

*   **`setId(String id)`**: Asigna un identificador único al componente.
*   **`addClass(String className)`**: Añade una clase CSS (por defecto incluye `j-indexui`).
*   **`setStyle(String key, String value)`**: Aplica estilos CSS en línea.
*   **`add(UIComponent child)`**: Añade subcomponentes dentro del IndexUI.

## Ejemplo de Integración

Este componente está optimizado para ser la raíz o el contenedor principal en la vista `index` de una aplicación:

```java
IndexUI indexUI = new IndexUI();
indexUI.setStyle("min-height", "100vh");
indexUI.setStyle("display", "flex");
indexUI.setStyle("flex-direction", "column");

// Añadiendo una barra de navegación y el contenido central
indexUI.add(myNavigationBar);
indexUI.add(myHeroSection);
```

## Diseño en Jettra Web Designer

En el diseñador web (JettraWebDesigner) y el plugin de NetBeans, el componente `IndexUI` está disponible en la paleta bajo la categoría de Layout. Al arrastrarlo al lienzo, se previsualiza como un contenedor principal con los estilos básicos correspondientes.
