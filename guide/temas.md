# Temas en JettraWUI

JettraWUI soporta un sistema de temas dinámico basado en variables CSS y clases en el `<body>`.

## Temas Disponibles

- **3d**: Tema por defecto con efectos de profundidad y luces de neón.
- **Dark**: Tema oscuro clásico y elegante.
- **White**: Tema claro y minimalista.
- **Cyberpunk**: Estilo retro-futurista con colores vibrantes.
- **Modern**: Estilo limpio con acentos verdes.
- **Nx (: Inspirado en la interfaz de Netflix. Fondo negro profundo, acentos rojos y tipografía negrita.

## Tema Nx (Netflix)

El tema `Nx` se activa con la clase `theme-nx`. Sus características principales son:

- **Fondo**: `#141414` (Deep Black)
- **Acento**: `#E50914` (Netflix Red)
- **Componentes**: Bordes redondeados sutiles (4px), sin efectos 3D, enfoque en el contenido.
- **Dashboard**: El Top y el Left son completamente negros para maximizar la inmersión.

## Cómo cambiar de tema

Desde el Dashboard, utiliza el selector de temas en la esquina superior derecha. El framework guarda la preferencia en `localStorage`.

Para forzar un tema programáticamente:
```javascript
changeTheme('nx');
```
