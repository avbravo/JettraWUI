---
title: Organigram
description: Componente para graficar organigramas jerárquicos
package: io.jettra.wui.components.Organigram
---

El componente `Organigram` presenta datos en una estructura de árbol, ideal para mostrar relaciones organizacionales o mapas jerárquicos de forma nativa.

### Uso Básico

```java
import io.jettra.wui.components.Organigram;

Organigram org = new Organigram();
org.setRoot("CEO", "John Doe");
Organigram.OrgNode cto = org.getRoot().addChild("CTO", "Jane Smith");
cto.addChild("Lead Dev", "Peter");
cto.addChild("UI/UX", "Sarah");
```
