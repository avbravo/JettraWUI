# Card Component

El componente `Card` permite generar contenedores dinámicos en formato de "Tarjeta" ideales para agrupar contenido de forma estética como paneles, resúmenes, noticias o elementos de catálogos.

## Clases Automáticas
- `j-card`
- `j-component`

## Uso

```java
import io.jettra.wui.components.Card;
import io.jettra.wui.components.Header;
import io.jettra.wui.components.Paragraph;

Card card = new Card();

Header cardTitle = new Header(3, "Card Title");
Paragraph cardBody = new Paragraph("This is the main content of the card...");

card.add(cardTitle);
card.add(cardBody);
```
