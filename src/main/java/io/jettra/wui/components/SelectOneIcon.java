package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;

/**
 * Componente Select personalizado que soporta iconos (como banderas) para cada opción.
 */
public class SelectOneIcon extends UIComponent {
    
    private boolean showLabelInTrigger = true;
    private boolean allowAddItem = false;
    
    private String name;

    public SelectOneIcon(String name, String label) {
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
        
        Span selectedLabel = new Span(label);
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

    public SelectOneIcon setAllowAddItem(boolean allowAddItem) {
        this.allowAddItem = allowAddItem;
        return this;
    }

    public boolean isAllowAddItem() {
        return allowAddItem;
    }
    
    @Override
    public String render() {
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
                    getStyles().remove("width");
                } else {
                    label.setStyle("display", "none");
                    setStyle("width", "50px");
                }
            }
        }
        if (allowAddItem) {
            // Buscamos el contenedor de opciones (es el tercer hijo)
             UIComponent options = getChildren().stream()
                .filter(c -> c instanceof Div && (name + "_options").equals(c.getProperties().get("id")))
                .findFirst().orElse(null);
            
            if (options != null) {
                boolean hasAdd = options.getChildren().stream()
                        .anyMatch(c -> "j-select-icon-option-add".equals(c.getProperties().get("class")));
                
                if (!hasAdd) {
                    Div addOption = new Div();
                    addOption.addClass("j-select-icon-option").addClass("j-select-icon-option-add");
                    addOption.setProperty("onclick", "jettraSelectAddItem('" + name + "')");
                    
                    Div iconDiv = new Div();
                    iconDiv.addClass("j-option-icon");
                    iconDiv.setContent("<svg viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2'><line x1='12' y1='5' x2='12' y2='19'></line><line x1='5' y1='12' x2='19' y2='12'></line></svg>");
                    
                    Span labelSpan = new Span("Add item...");
                    
                    addOption.add(iconDiv).add(labelSpan);
                    options.add(addOption);
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

    public SelectOneIcon setSelectedValue(String value, String label, String iconHtml) {
        // Update hidden input
        // Find children[0] which is the hidden input
        if (getChildren().size() > 0) {
            getChildren().get(0).setProperty("value", value);
        }
        
        // Find trigger -> selectedIcon and selectedLabel
        UIComponent trigger = getChildren().stream()
            .filter(c -> c instanceof Div && "j-select-icon-trigger".equals(c.getProperties().get("class")))
            .findFirst().orElse(null);
            
        if (trigger != null) {
            UIComponent iconDiv = trigger.getChildren().stream()
                .filter(c -> (name + "_selected_icon").equals(c.getProperties().get("id")))
                .findFirst().orElse(null);
            if (iconDiv != null) {
                iconDiv.setContent(iconHtml);
            }
            
            UIComponent labelSpan = trigger.getChildren().stream()
                .filter(c -> (name + "_selected_label").equals(c.getProperties().get("id")))
                .findFirst().orElse(null);
            if (labelSpan != null) {
                labelSpan.setContent(label);
            }
        }
        return this;
    }
    
    private void addStyles() {
        Style style = new Style("""
            .j-select-icon-container {
                position: relative;
                width: auto;
                min-width: 45px;
                font-family: sans-serif;
                user-select: none;
                z-index: 1000;
            }
            @media (max-width: 480px) {
                .j-select-icon-container {
                    min-width: 40px;
                }
            }
            .j-select-icon-container .j-select-icon-trigger {
                width: 100%;
                box-sizing: border-box;
            }
            .j-select-icon-trigger {
                display: flex;
                align-items: center;
                gap: 6px;
                background: rgba(20, 25, 40, 0.8);
                border: 1px solid var(--jettra-accent, #00e5ff);
                color: var(--jettra-text, #fff);
                padding: 2px 8px;
                border-radius: 6px;
                cursor: pointer;
                box-shadow: 0 0 10px rgba(0, 229, 255, 0.2);
                transition: all 0.3s;
                font-size: 0.85rem;
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
                gap: 8px;
                padding: 6px 10px;
                cursor: pointer;
                transition: background 0.2s;
                color: #fff;
                font-size: 0.85rem;
            }
            .j-select-icon-option:hover {
                background: rgba(0, 229, 255, 0.2);
            }
            .j-option-icon, .j-selected-icon {
                width: 18px;
                height: 12px;
                display: flex;
                align-items: center;
                justify-content: center;
            }
            .j-option-icon svg, .j-selected-icon svg {
                width: 100%;
                height: 100%;
            }
            .j-select-icon-option-add {
                border-top: 1px dashed var(--jettra-accent, #00e5ff);
                color: var(--jettra-accent, #00e5ff) !important;
                font-weight: bold;
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
            function jettraSelectAddItem(name) {
                let val = prompt('Insert new item:');
                if (val && val.trim() !== '') {
                    const iconHtml = '<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M12 2L2 7l10 5 10-5-10-5zM2 17l10 5 10-5M2 12l10 5 10-5"></path></svg>';
                    
                    // Add it to the list visually before the add button
                    const optionsCont = document.getElementById(name + '_options');
                    const addBtn = optionsCont.querySelector('.j-select-icon-option-add');
                    
                    const newOpt = document.createElement('div');
                    newOpt.className = 'j-select-icon-option';
                    newOpt.onclick = function() { jettraSelectOption(name, val, val, iconHtml); };
                    newOpt.innerHTML = `<div class="j-option-icon">${iconHtml}</div><span>${val}</span>`;
                    
                    optionsCont.insertBefore(newOpt, addBtn);
                    
                    // Select it
                    jettraSelectOption(name, val, val, iconHtml);
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

    @Override
    public SelectOneIcon setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public SelectOneIcon setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public SelectOneIcon setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public SelectOneIcon addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public SelectOneIcon removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public SelectOneIcon setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public SelectOneIcon setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public SelectOneIcon addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public SelectOneIcon add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
