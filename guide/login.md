# Componente `Login`

El componente `Login` en JettraWUI es un componente compuesto diseñado para facilitar formularios de autenticación. Hereda de `Form` e incluye internamente todos los campos necesarios para ingresar credenciales y realizar la autenticación del usuario.

## Estructura

El componente `Login` agrupa internamente los siguientes elementos:

- **`TextBox` (username)**: Campo para el nombre de usuario (propiedad `username`).
- **`TextBox` (password)**: Campo para la contraseña, tipo `password`.
- **`Button` (loginButton)**: Botón principal para enviar las credenciales.
- **`Button` (forgotPasswordButton)**: Botón secundario para "¿Olvidaste tu contraseña?".
- **`Alert` (alertMessage)**: Un componente para mostrar mensajes o advertencias (por defecto, tipo "warning").

## Creación y Uso Básico

Para crear una instancia de este componente, solo requieres especificar la ruta o el "action" hacia donde se enviarán los datos del formulario mediante una petición POST.

```java
import io.jettra.wui.components.Login;

// Crea un Login cuyo formulario apuntará a "/login"
Login loginForm = new Login("/login");
```

## Estilo 3D

El componente incluye por defecto un método llamado `style3D()` que aplica automáticamente una serie de variables CSS para proporcionarle un aspecto "futurista" o "3D" (colores cyan, fondo oscuro, efecto de cristal "blur", etc.). Este método se llama en el constructor por lo que ya viene con estilos predefinidos.

Si quisieras volver a aplicarlo o encadenarlo:

```java
loginForm.style3D();
```

## Accediendo a los Elementos

Si necesitas personalizar el comportamiento, el estilo o los atributos de cada control interno, el componente `Login` expone métodos getters para todos ellos:

```java
// Cambiar el estilo o clases del botón de Login
loginForm.getLoginButton().setProperty("class", "my-custom-login-btn");

// Cambiar el placeholder
loginForm.getUsername().setPlaceholder("Correo electrónico");

// Ocultar botón de olvidar contraseña
loginForm.getForgotPasswordButton().setProperty("style", "display: none;");

// Modificar o utilizar la alerta
loginForm.getAlertMessage().setType("info");
```

## Ejemplo de Estilo Personalizado

Puedes sobrescribir los estilos de cada uno de los elementos del `Login` aplicando tus propias reglas CSS (como el siguiente ejemplo que configura una estética futurista o "3D"):

```java
@Init
public void initUI() {
    loginForm = new Login("/login");

    // Styling the login form generically with 3D futuristic aesthetics
    loginForm.setProperty("style", "margin: 10% auto; padding: 30px; width: 350px; " +
        "background: rgba(20, 30, 50, 0.85); box-shadow: 0 0 20px cyan; " +
        "border-radius: 15px; backdrop-filter: blur(10px); " +
        "color: white; font-family: 'Courier New', monospace; text-align: center;");
   
    loginForm.getUsername().setProperty("style", "width: 100%; padding: 10px; margin-bottom: 15px; " +
        "background: #0f172a; border: 1px solid cyan; color: white; border-radius: 5px;");
       
    loginForm.getPassword().setProperty("style", "width: 100%; padding: 10px; margin-bottom: 15px; " +
        "background: #0f172a; border: 1px solid cyan; color: white; border-radius: 5px;");
       
    loginForm.getLoginButton().setProperty("style", "width: 100%; padding: 12px; margin-bottom: 10px; " +
        "background: cyan; color: #0f172a; border: none; border-radius: 5px; font-weight: bold; cursor: pointer; " +
        "box-shadow: 0 0 10px cyan;");
       
    loginForm.getForgotPasswordButton().setProperty("style", "background: transparent; border: none; " +
        "color: cyan; cursor: pointer; text-decoration: underline; font-size: 0.9em;");
    
    add(loginForm);
}
```

## Ejemplo Completo de Implementación

A continuación, un ejemplo de cómo integrar el componente `Login` dentro de una `Page` de JettraWUI y cómo capturar las credenciales.

### 1. Inicialización en la Página

```java
import io.jettra.wui.components.Login;
import io.jettra.wui.core.Page;
import io.jettra.wui.core.annotations.Init;

public class LoginPage extends Page {

    private Login loginForm;

    public LoginPage() {
        super("Iniciar Sesión");
    }

    @Init
    public void initUI() {
        // Enviar el formulario a la URL "/login"
        loginForm = new Login("/login");
        
        // Agregar el formulario a la página
        add(loginForm);
    }
}
```

### 2. Manejo de la Petición HTTP (`HttpHandler`)

Cuando el usuario hace clic en el botón de Login, se hace una petición POST y las credenciales viajan usando los nombres o propiedades `username` y `password`. Luego se pueden validar en el manejador (`HttpHandler`).

```java
@Override
public void handle(HttpExchange exchange) throws IOException {
    String path = exchange.getRequestURI().getPath();

    // Procesar envío del formulario (POST)
    if ("POST".equals(exchange.getRequestMethod()) && "/login".equals(path)) {
        
        // Leer el cuerpo de la petición (Query params)
        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        Map<String, String> params = parseQuery(body);

        // Obtener valores enviados por los campos del Login
        String user = params.get("username");
        String pass = params.get("password");

        // Lógica de Autenticación
        if ("admin".equals(user) && "admin".equals(pass)) {
            // Login correcto
            exchange.getResponseHeaders().set("Set-Cookie", "username=" + user + "; Path=/");
            exchange.getResponseHeaders().set("Location", "/dashboard");
            exchange.sendResponseHeaders(302, -1);
        } else {
            // Login incorrecto
            exchange.getResponseHeaders().set("Location", "/login?error=invalid_credentials");
            exchange.sendResponseHeaders(302, -1);
        }
    } 
    // Procesar visualización de la vista (GET)
    else if ("GET".equals(exchange.getRequestMethod())) {
        
        // Renderizar y enviar el HTML
        String html = this.render();
        byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);
        exchange.getResponseBody().write(bytes);
        
    } else {
        exchange.sendResponseHeaders(405, -1);
    }
    
    exchange.getResponseBody().close();
}

// Método utilitario recomendado para interpretar el body
private Map<String, String> parseQuery(String query) {
    Map<String, String> query_pairs = new HashMap<>();
    if (query == null || query.isBlank()) return query_pairs;
    String[] pairs = query.split("&");
    try {
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            if(idx > 0) {
                query_pairs.put(
                    java.net.URLDecoder.decode(pair.substring(0, idx), "UTF-8"), 
                    java.net.URLDecoder.decode(pair.substring(idx + 1), "UTF-8")
                );
            }
        }
    } catch(Exception e) {}
    return query_pairs;
}
```
