---
title: OTPValidator
description: Componente de cajas de texto para validar OTP (One Time Password)
package: io.jettra.wui.components.OTPValidator
---

El componente `OTPValidator` genera automáticamente una matriz de inputs interactivos que facilitan el ingreso de tokens OTP (como los recibidos por SMS).

### Modo de Uso
Establezca la cantidad de dígitos necesarios y provea una función JavaScript nativa para ejecutarse en el autollenado exitoso.

```java
import io.jettra.wui.components.OTPValidator;

OTPValidator otp = new OTPValidator("login_otp");
otp.setAmountOfDigits(6);
otp.setOnComplete("window.show3DMessage('OTP', 'Token completado: ' + otpStr);"); 
```
El string interno resultante está disponible en la variable embebida de evento `otpStr`.
