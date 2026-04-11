# Uso y Personalización de Temas en JettraWUI

JettraWUI incluye un potente motor de estilos empaquetado en Java. Sin dependencias externas o CDNs que hagan su aplicación lenta, JettraWUI provee varios estilos preconstruidos.

## Lista de Temas
Hemos añadido nuevos temas "Plain" (sin 3D), "Material" (clásico) y variantes emulando al popular framework TailwindCSS.
Puedes escoger los temas desde el Top Dashboard, con el siguiente listado:

* **3D**: Estilo por defecto, con fondos `glassmorphism`, sobreados neón y efectos 3D.
* **Modern**: Aspecto esmeralda premium para negocios formales, con efectos 3D fluidos.
* **Futuristic / Cyberpunk / Neon**: Modos altamente saturados con contornos y glows radiantes muy futuristas.
* **Plain**: Estilo puro en blanco, fondo claro, contrastes fuertes (negro y blanco), y SIN efecto 3D (`box-shadow: none`).
* **Material-Plain**: Estilo Material clásico de fondo puro. SIN efecto visual 3D exagerado en el Hover de botones. Ideal para paneles nativos.
* **TailwindCSS**: Inspirado en los predeterminados de colores de Tailwind (Blue-500, Slate-800) en versión light, bordes flat, esquinas redondeadas pero SIN 3D (Hover y saltos suprimidos).
* **TailwindCSS-3D**: Una combinación del "Look and Feel" y tokens de color de Tailwind, pero preservando las animaciones 3D.
* **Dark / White**: Esquemas base neutrales en un enfoque plano o dark.

## ¿Cómo cambiar el tema en toda mi app?

El selector de temas está construido directamente en su `JettraDashboardPage`. Al seleccionarlo se guardará la variable CSS global a través de `localStorage.setItem('jettra-theme', theme);` y los estilos de `io.jettra.wui.assets.JettraTheme` inyectarán dinámicamente las variables correspondientes a la clase insertada en el `<body class="theme-nombre">`.

Todo el CSS está auto-contenido en `JettraTheme.java` eliminando la necesidad de importar TailwindCSS con dependencias NPX si lo que buscas es solo su estilo para el Dashboard.
