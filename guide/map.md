---
title: Map
description: Componente para mostrar mapas provistos por Leaflet
package: io.jettra.wui.components.Map
---

El componente `Map` proporciona una manera interactiva de incrustar mapas con marcadores en JettraWUI utilizando Leaflet por debajo, de forma nativa e integrada.

### Modo de Uso
Para incrustar un mapa, instancie la clase `Map`, configure su identificador único para el DOM, defina las coordenadas de centrado (latitud, longitud y zoom) y un título para el marcador primario.

```java
import io.jettra.wui.components.Map;

Map locationMap = new Map("hq_map");
locationMap.setCenter(40.7128, -74.0060, 13); // Nueva York
locationMap.setMarker("JettraStack Headquarters");
```

### Personalización
Puede modificar libremente las propiedades nativas de estilo, tales como las proporciones del div del mapa:
```java
locationMap.setStyle("width", "100%");
locationMap.setStyle("height", "500px");
```
El componente embebe automáticamente todas las bibliotecas de js y css offline necesarias mediante la inyección interna generada por `LeafletExtension`. No es necesario incluir cabeceras externas en su Page.
