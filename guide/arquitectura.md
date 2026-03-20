# JettraWUI Architecture
JettraWUI is a Java-to-HTML Component framework focused on generating futuristic, 3D visually-rich User Interfaces using Neumorphism and Glassmorphism CSS directly embedded into Java object hierarchies.

## Core Hierarchy
The framework is based on the Abstract Syntax Tree pattern:
- `UIComponent` es la clase base. Almacena las `properties` (HTML attributes), `styles` (inline CSS), e hijos `children` (otros componentes anidados).
- Cada componente específico (`Button`, `Form`, `Modal`) anula el método `render()` par construir la etiqueta HTML correspondiente y le adjunta sus sub-nodos llamando internamente a `render()` en sus clases hijas.
- `Page` es la raíz del árbol. Se encarga de rodear todos los elementos con las etiquetas `<html>`, `<head>` y `<body>`. Además, inyecta la librería estática de JettraTheme para dar vida al mundo 3D.

## Advantages
- **Zero Front-end Tech Required**: The user never touches CSS, JS, or HTML formats.
- **Reusable Types**: Objects like Forms or Grids can be initialized and reused programmatically.
- **Consistent Futuristic Concept**: `JettraTheme` enforces a strict aesthetic pattern focused on spatial depth, glass blur effects, and animations to resemble immersive environments.

# Icon
La clase Icon contiene iconos para ser usados con otros componentes

# Dashboard
La clase Dashboard es la clase principal que dibuja el dashboard estilo 3D de todo el sistema esta compuesto por las clases: Top, Left,Center,Footer.

# Top
La clase Top tiene los componentes de la barrra superior tales como nombre de usuario, botones salir

# Left
La clase Left tiene los componentes de la barra lateral tales como menu, botones, etc

# Center
La clase Center tiene los componentes del cuerpo principal tales como formularios, tablas, etc

# Footer
La clase Footer tiene los componentes de la barra inferior tales como informacion de la aplicacion, etc

# Menu
La clase Menu es un componente que se puede usar en cualquier parte de la aplicacion, por ejemplo en el dashboard, en el menu de la izquierda, etc. Esta compuesto por clases hijas que dibujan los componentes en la aplicacion, por ejemplo:

```java
Menu menu = new Menu();


```

# MenuBar 
La clase MenuBar son barras de menu que se pueden usar en cualquier parte de la aplicacion

# MenuItem 
La clase MenuItem son elementos de menu que se añaden a sun MenuBar.
Debe tener una clase hija que dibuje el componente en la aplicacion, por ejemplo:
```java
MenuItem menuItem = new MenuItem("File");
```
Tambien controlar los eventos de los elementos de menu. Por ejemplo:
click, doble click, mouse over. Mediante metodos Java.


# Separador 
La clase Separator es un componente que se puede usar en cualquier parte de la aplicacion, por ejemplo en el dashboard, en el menu de la izquierda, etc. Esta compuesto por clases hijas que dibujan los componentes en la aplicacion, por ejemplo:

```java
Separator separator = new Separator();

Menu menu = new Menu();
MenuBar menuBar = new MenuBar("File");
MenuItem menuItemOpen = new MenuItem("Open");
MenuItem menuItemSave = new MenuItem("Save",Icon.Button)
menuBar.add(menuItemOpen);
menuBar.add(separator);
menuBar.add(menuItemSave);

menu.add(menuBar);
```

# Page

La clase page es la clase base para dibujar componentes en la aplicacion, las clases hijas de page son las que se encargan de dibujar los componentes en la aplicacion, por ejemplo:

```java
public class PersonaPage extends Page{

    public Persona(){
        
    }
    @Init
    public void initUI(){
// Aqui se dibuja los componentes
    }
    @PostConstructor
    public void postConstructor(){

    }
    @PreDestroy
    public void preDestroy(){

    }
    @OnEvent(name="submit")
    public void onSubmit(){

    }

}
```

