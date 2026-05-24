# JettraWUI

## Descripción General
`JettraWUI` es el motor de interfaces de usuario (Web UI) para JettraStack. Se encarga de proveer componentes web ricos, vistas CRUD, tablas de datos (Datatables) y enrutadores de eventos, facilitando la creación de aplicaciones web dinámicas y modernas.

## Detalles Específicos
- **Arquitectura general**: Capa de presentación (MVC) adaptada para manejar eventos de la UI, inyección de componentes y generación dinámica de vistas a partir de modelos.
- **Dependencias clave**: Interfaces Core, Servidor, librerías de componentes UI nativos y motores de plantillas.
- **Roles dentro del sistema**: El front-end del framework; procesa las interacciones del usuario y orquesta la actualización de la interfaz en respuesta a cambios de estado o validaciones de negocio.

## Características Detalladas
- **EventRouter**: Un enrutador de eventos (EventRouter) para manejar eventos del navegador de forma centralizada.
- **CrudView y Datatable**: Componentes avanzados de gestión de datos, incluyendo DatatableEditableCrudView, permitiendo construir rápidamente interfaces CRUD complejas con validaciones de interfaz (ej. solo lectura, ocultos).
- **Plantillas Dinámicas y Anotaciones**: Generación y manipulación de UI desde anotaciones como `@CrudView`. Funciones automáticas como cálculos de totales en filas hijas.

## Guía de Entrenamiento (AI / Nuevas Características)
- Las modificaciones en componentes como Datatables y formularios maestros-detalle (Master-Detail) requieren conocer el ciclo de vida del `EventRouter` y del backend correspondiente que manejará los posts.
- Para cambiar la apariencia de solo lectura o aplicar nuevas clases CSS a la interfaz, editar las hojas de estilos generales y aplicar en la definición de las etiquetas generadas.
- Todo nuevo feature de UI, como soporte a QR o calculadoras en la vista, debe integrarse sin romper la estructura de `CrudView`.
