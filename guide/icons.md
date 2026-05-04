# Componente Icon

El componente `Icon` se encarga de renderizar de manera estandarizada vectores SVG u otros caracteres (como Emojis) a lo largo de las vistas de Jettra. Al ser un componente versátil, puedes proporcionarle cualquiera de los íconos incorporados, o inyectar tu propio código SVG.

## Uso Básico

Puedes utilizar los íconos estáticos proporcionados por la clase `Icon`, que cubren las necesidades básicas (Home, User, Settings, etc.):

```java
import io.jettra.wui.components.Icon;

Icon userIcon = new Icon(Icon.USER);
Icon homeIcon = new Icon(Icon.HOME);
```

## Iconos Disponibles (SVG)

La clase `Icon` incluye constantes predefinidas para los íconos más comunes:

- `Icon.HOME`, `Icon.USER`, `Icon.SETTINGS`
- `Icon.TRASH`, `Icon.CHECK`, `Icon.CLOSE`
- `Icon.PLUS`, `Icon.MINUS`, `Icon.EYE`, `Icon.EYE_OFF`
- `Icon.MENU`, `Icon.SEARCH`, `Icon.CALENDAR`
- `Icon.HEART`, `Icon.LOCK`, `Icon.UNLOCK`
- `Icon.FILE`, `Icon.EDIT`, `Icon.IMAGE`, `Icon.VIDEO`
- `Icon.MAP_PIN`, `Icon.MAIL`, `Icon.BELL`
- `Icon.INFO`, `Icon.ALERT_CIRCLE`, `Icon.HELP_CIRCLE`
- `Icon.LOG_OUT`, `Icon.LOG_IN`, `Icon.REFRESH`, `Icon.STAR`

## Símbolos Unicode y Emojis

También se incluyen constantes para símbolos Unicode comunes:

- `Icon.UNI_STAR_BLACK`, `Icon.UNI_HEART_BLACK`, `Icon.UNI_WARNING`
- `Icon.UNI_FIRE`, `Icon.UNI_ROCKET`, `Icon.UNI_TROPHY`
- `Icon.UNI_COFFEE`, `Icon.UNI_PIZZA`, `Icon.UNI_SOCCER`
- `Icon.UNI_LAPTOP`, `Icon.UNI_PHONE`, `Icon.UNI_CAMERA`


## Estilo y Configuración

El componente proporciona métodos auxiliares para definir el tamaño y color del ícono:

```java
Icon check = new Icon(Icon.CHECK)
                .setSize("32px")
                .setColor("#22c55e");
```

## Íconos Personalizados

También puedes instanciar el componente con cadenas (Strings) arbitrarias de tu código SVG o Emojis:

```java
Icon emojiIcon = new Icon("🔥").setSize("40px");

Icon customSvg = new Icon("<svg viewBox=\"0 0 24 24\" fill=\"red\" stroke=\"none\"><circle cx=\"12\" cy=\"12\" r=\"6\"></circle></svg>");
```

---
# Sitio Web con iconos para Copiar y Pegar
[https://emojiterra.com/es/mamiferos-es/](https://emojiterra.com/es/mamiferos-es/)


---
*Para ver un ejemplo funcional en vivo, dirígete a la página de demostración `IconsPage.java` en el proyecto JettraWebExample.*
