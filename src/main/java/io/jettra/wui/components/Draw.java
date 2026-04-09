package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

/**
 * Componente interactivo para dibujo y diagramación utilizando Excalidraw React Component embedding.
 */
public class Draw extends UIComponent {
    
    private String containerId;
    
    public Draw(String id, int width, int height) {
        super("div");
        this.containerId = id;
        this.initialClasses = "j-draw-container j-component";
        
        this.setStyle("position", "relative")
            .setStyle("width", width + "px")
            .setStyle("height", height + "px")
            .setStyle("background-color", "var(--jettra-bg-primary)")
            .setStyle("border", "2px solid var(--jettra-accent)")
            .setStyle("border-radius", "8px")
            .setStyle("overflow", "hidden")
            .setStyle("box-shadow", "0 0 15px rgba(0,255,255,0.2)");
            
        UIComponent appContainer = new UIComponent("div") {};
        appContainer.setProperty("id", this.containerId);
        appContainer.setStyle("width", "100%").setStyle("height", "100%");
        super.add(appContainer);
        
        // Add required scripts for Excalidraw (React, ReactDOM, Excalidraw)
        super.add(buildDependency("https://unpkg.com/react@18/umd/react.production.min.js"));
        super.add(buildDependency("https://unpkg.com/react-dom@18/umd/react-dom.production.min.js"));
        super.add(buildDependency("https://unpkg.com/@excalidraw/excalidraw/dist/excalidraw.production.min.js"));
        
        // Add Init script
        super.add(buildEngineScript());
    }
    
    private UIComponent buildDependency(String src) {
        UIComponent script = new UIComponent("script") {};
        script.setProperty("src", src);
        script.setProperty("crossorigin", "anonymous");
        return script;
    }
    
    private UIComponent buildEngineScript() {
        UIComponent script = new UIComponent("script") {};
        script.setContent(
            "window.addEventListener('load', () => { " +
            "  setTimeout(() => { " +
            "    const container = document.getElementById('" + this.containerId + "');" +
            "    if(!container) return;" +
            "    if(typeof React === 'undefined' || typeof ReactDOM === 'undefined' || typeof ExcalidrawLib === 'undefined') {" +
            "       console.error('Cannot load Excalidraw dependencies'); return;" +
            "    }" +
            "    " +
            "    const App = () => {" +
            "      const [excalidrawAPI, setExcalidrawAPI] = React.useState(null);" +
            "      React.useEffect(() => {" +
            "        if(excalidrawAPI) window['excalidrawAPI_" + this.containerId + "'] = excalidrawAPI;" +
            "      }, [excalidrawAPI]);" +
            "      " +
            "      return React.createElement(" +
            "        React.Fragment," +
            "        null," +
            "        React.createElement(" +
            "          'div'," +
            "          { style: { height: '100%', width: '100%' } }," +
            "          React.createElement(ExcalidrawLib.Excalidraw, { " +
            "             key: '" + this.containerId + "_exc', " +
            "             excalidrawAPI: setExcalidrawAPI " +
            "          })" +
            "        )" +
            "      );" +
            "    };" +
            "    " +
            "    const root = ReactDOM.createRoot(container);" +
            "    root.render(React.createElement(App));" +
            "  }, 1000);" +
            "});"
        );
        return script;
    }
}
