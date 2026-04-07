---
title: Catcha
description: Componente de verificación humana (Captcha) visual e interactivo
package: io.jettra.wui.components.Catcha
---

El componente `Catcha` genera un grid dinámico para comprobaciones antibot, pidiendo al usuario marcar visualmente una cantidad específica de casillas correctas.

### Modo de Uso
Indica el número requerido de casillas para validar `setAmountOfImagesToValidate` y un callback interactivo cuando supera el bloque.

```java
import io.jettra.wui.components.Catcha;

Catcha catcha = new Catcha("form_verification");
catcha.setAmountOfImagesToValidate(3);
catcha.setOnValidate("window.show3DMessage('Autenticado', 'Has aprobado la validación exitosamente'); document.getElementById('loginBtn').disabled = false;");
```
