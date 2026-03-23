package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

/**
 * Componente Select personalizado que soporta iconos (como banderas) para cada opción.
 */
public class SelectOneIcon extends UIComponent {
    
    private boolean showLabelInTrigger = true;
    
    private String name;

    public SelectOneIcon(String name) {
        super("div");
        this.name = name;
        addClass("j-select-icon-container");
        setProperty("id", "container_" + name);
        
        // Input oculto para el valor seleccionado
        UIComponent hidden = new UIComponent("input") {};
        hidden.setProperty("type", "hidden");
        hidden.setProperty("name", name);
        hidden.setProperty("id", name);
        add(hidden);

        // Área del trigger (lo que se ve inicialmente)
        Div trigger = new Div();
        trigger.addClass("j-select-icon-trigger");
        trigger.setProperty("onclick", "jettraSelectToggle('" + name + "')");
        
        Div selectedIcon = new Div();
        selectedIcon.setProperty("id", name + "_selected_icon");
        selectedIcon.addClass("j-selected-icon");
        
        Span selectedLabel = new Span("Select...");
        selectedLabel.setProperty("id", name + "_selected_label");
        
        trigger.add(selectedIcon);
        if (showLabelInTrigger) {
            trigger.add(selectedLabel);
        } else {
            selectedLabel.setStyle("display", "none");
            trigger.add(selectedLabel);
        }
        add(trigger);

        // Contenedor de opciones
        Div options = new Div();
        options.addClass("j-select-icon-options");
        options.setProperty("id", name + "_options");
        add(options);
        
        // Estilos y Scripts necesarios para el componente
        addStyles();
        addScripts();
    }

    public SelectOneIcon setShowLabelInTrigger(boolean show) {
        this.showLabelInTrigger = show;
        return this;
    }
    
    @Override
    public String render() {
        // We need to re-check the children before rendering to hide/show the label
        // This is a bit hacky but works for this simple framework.
        UIComponent trigger = getChildren().stream()
            .filter(c -> c instanceof Div && "j-select-icon-trigger".equals(c.getProperties().get("class")))
            .findFirst().orElse(null);
        if (trigger != null) {
            UIComponent label = trigger.getChildren().stream()
                .filter(c -> c instanceof Span)
                .findFirst().orElse(null);
            if (label != null) {
                if (showLabelInTrigger) {
                    label.getStyles().remove("display");
                } else {
                    label.setStyle("display", "none");
                }
            }
        }
        return super.render();
    }

    public SelectOneIcon addOption(String value, String label, String iconHtml) {
        Div option = new Div();
        option.addClass("j-select-icon-option");
        // Escapamos comillas simples para el JS
        String safeIcon = iconHtml.replace("'", "\\'");
        option.setProperty("onclick", "jettraSelectOption('" + name + "', '" + value + "', '" + label + "', '" + safeIcon + "')");
        
        Div iconDiv = new Div();
        iconDiv.addClass("j-option-icon");
        iconDiv.setContent(iconHtml);
        
        Span labelSpan = new Span("");
        labelSpan.setContent(label);
        
        option.add(iconDiv).add(labelSpan);
        
        // Buscamos el contenedor de opciones (es el tercer hijo)
        ((Div)children.get(2)).add(option);
        return this;
    }

    private void addStyles() {
        Style style = new Style("""
            .j-select-icon-container {
                position: relative;
                width: 150px;
                font-family: sans-serif;
                user-select: none;
                z-index: 100;
            }
            .j-select-icon-trigger {
                display: flex;
                align-items: center;
                gap: 10px;
                background: rgba(20, 25, 40, 0.8);
                border: 1px solid var(--jettra-accent, #00e5ff);
                color: var(--jettra-text, #fff);
                padding: 5px 12px;
                border-radius: 8px;
                cursor: pointer;
                box-shadow: 0 0 10px rgba(0, 229, 255, 0.2);
                transition: all 0.3s;
            }
            .j-select-icon-trigger:hover {
                box-shadow: 0 0 15px rgba(0, 229, 255, 0.4);
                background: rgba(30, 40, 60, 0.9);
            }
            .j-select-icon-options {
                position: absolute;
                top: 100%;
                left: 0;
                right: 0;
                background: rgba(10, 15, 30, 0.95);
                border: 1px solid var(--jettra-accent, #00e5ff);
                border-top: none;
                border-radius: 0 0 8px 8px;
                display: none;
                flex-direction: column;
                overflow: hidden;
                box-shadow: 0 10px 20px rgba(0,0,0,0.5);
                backdrop-filter: blur(10px);
                z-index: 9999;
            }
            .j-select-icon-option {
                display: flex;
                align-items: center;
                gap: 10px;
                padding: 10px 12px;
                cursor: pointer;
                transition: background 0.2s;
                color: #fff;
            }
            .j-select-icon-option:hover {
                background: rgba(0, 229, 255, 0.2);
            }
            .j-option-icon, .j-selected-icon {
                width: 20px;
                height: 15px;
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .j-option-icon svg, .j-selected-icon svg {
                width: 100%;
                height: 100%;
            }
        """);
        add(style);
    }

    private void addScripts() {
        Script script = new Script("""
            function jettraSelectToggle(name) {
                const options = document.getElementById(name + '_options');
                const isVisible = options.style.display === 'flex';
                options.style.display = isVisible ? 'none' : 'flex';
            }
            function jettraSelectOption(name, value, label, iconHtml) {
                document.getElementById(name).value = value;
                document.getElementById(name + '_selected_label').innerText = label;
                document.getElementById(name + '_selected_icon').innerHTML = iconHtml;
                document.getElementById(name + '_options').style.display = 'none';
                
                // Disparar evento de cambio si es necesario o redireccionar
                if (name === 'lang') {
                    const currentUrl = new URL(window.location.href);
                    currentUrl.searchParams.set('lang', value);
                    window.location.href = currentUrl.toString();
                }
                
                if (name === 'theme' && typeof changeTheme === 'function') {
                    changeTheme(value);
                }
            }
            // Cerrar al hacer clic fuera
            document.addEventListener('click', (e) => {
                const containers = document.querySelectorAll('.j-select-icon-container');
                containers.forEach(container => {
                    if (!container.contains(e.target)) {
                        const options = container.querySelector('.j-select-icon-options');
                        if (options) options.style.display = 'none';
                    }
                });
            });
        """);
        add(script);
    }
}
