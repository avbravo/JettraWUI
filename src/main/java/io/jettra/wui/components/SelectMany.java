package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;
import java.util.stream.Collectors;

/**
 * Representa un componente de selección múltiple tipo lista (no dropdown).
 * Reformulado para mostrar una lista de elementos seleccionables de forma elegante.
 */
public class SelectMany extends SelectOne {
    
    public SelectMany(String name) {
        super(name);
        // Cambiamos el tag base a div para que deje de comportarse como un select nativo
        this.setTag("div");
        this.removeClass("j-select").addClass("j-select-many-list");
        
        // Estilos base para el contenedor de la lista
        this.setStyle("border", "1px solid var(--jettra-border)")
            .setStyle("border-radius", "12px")
            .setStyle("background", "var(--jettra-glass)")
            .setStyle("backdrop-filter", "blur(10px)")
            .setStyle("max-height", "300px")
            .setStyle("overflow-y", "auto")
            .setStyle("padding", "8px")
            .setStyle("display", "flex")
            .setStyle("flex-direction", "column")
            .setStyle("gap", "6px")
            .setStyle("min-width", "250px")
            .setStyle("box-shadow", "inset 0 2px 4px rgba(0,0,0,0.2)");
    }

    @Override
    public SelectMany setAllowAddItem(boolean allowAddItem) {
        super.setAllowAddItem(allowAddItem);
        return this;
    }

    @Override
    public SelectMany setInline(boolean inline) {
        if (inline) {
            this.setStyle("display", "flex").setStyle("width", "100%");
        } else {
            this.setStyle("display", "flex").setStyle("width", "auto");
        }
        return this;
    }

    @Override
    public String render() {
        // Generar un ID único si no tiene uno para manejar el script
        if (getId() == null || getId().isEmpty()) {
            setId("sm_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000));
        }

        StringBuilder sb = new StringBuilder();
        
        // Renderizamos el contenedor manualment para controlar las opciones
        sb.append("<").append(getTag());
        renderAttributes(sb);
        sb.append(">");
        
        // Seleccionados para el input oculto inicial
        java.util.List<String> initialSelected = new java.util.ArrayList<>();

        // Renderizar opciones como items custom
        for (UIComponent child : getChildren()) {
            if ("option".equals(child.getTag())) {
                String value = child.getProperties().get("value");
                String label = child.getContent();
                boolean selected = "selected".equals(child.getProperties().get("selected")) || (defaultValue != null && defaultValue.equals(value));
                
                if (selected) initialSelected.add(value);

                String itemClass = "j-select-many-item" + (selected ? " selected" : "");
                sb.append("<div class='").append(itemClass).append("' ");
                sb.append("onclick=\"toggleSelectManyItem(this, '").append(value).append("')\" ");
                sb.append("data-value='").append(value).append("'>");
                
                // Icono de checkbox o indicador
                sb.append("<div class='j-checkbox-wrapper'>");
                sb.append("<div class='j-checkbox-inner'>");
                if (selected) sb.append("✓");
                sb.append("</div>");
                sb.append("</div>");
                
                sb.append("<span class='j-item-label'>").append(label).append("</span>");
                sb.append("</div>");
            } else {
                sb.append(child.render());
            }
        }
        
        // Input oculto para compatibilidad con formularios y MVC
        String name = getProperties().get("name");
        String selectedStr = initialSelected.stream().collect(Collectors.joining(","));
        sb.append("<input type='hidden' name='").append(name).append("' id='").append(getId()).append("_value' value='").append(selectedStr).append("'>");
        
        // Estilos específicos integrados (si no se quieren mover a JettraTheme)
        sb.append("<style>");
        sb.append(".j-select-many-item { display: flex; align-items: center; gap: 12px; padding: 12px 16px; border-radius: 8px; cursor: pointer; transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1); border: 1px solid transparent; background: rgba(255,255,255,0.02); }");
        sb.append(".j-select-many-item:hover { background: rgba(0,255,255,0.05); border-color: rgba(0,255,255,0.2); transform: translateX(4px); }");
        sb.append(".j-select-many-item.selected { background: rgba(0,255,255,0.1); border-color: var(--jettra-accent); color: var(--jettra-accent); box-shadow: 0 0 15px rgba(0,255,255,0.1); }");
        sb.append(".j-checkbox-wrapper { width: 22px; height: 22px; border: 2px solid var(--jettra-border); border-radius: 6px; display: flex; align-items: center; justify-content: center; transition: all 0.3s; background: rgba(0,0,0,0.4); flex-shrink: 0; }");
        sb.append(".j-select-many-item.selected .j-checkbox-wrapper { background: var(--jettra-accent); border-color: var(--jettra-accent); box-shadow: 0 0 10px var(--jettra-glow); }");
        sb.append(".j-checkbox-inner { color: #000; font-weight: bold; font-size: 14px; }");
        sb.append(".j-item-label { font-size: 14px; font-weight: 500; }");
        sb.append("</style>");

        // Script de gestión de selección única vez
        sb.append("<script>");
        sb.append("if(typeof toggleSelectManyItem === 'undefined') {");
        sb.append("  window.toggleSelectManyItem = function(el, val) {");
        sb.append("    const isSelected = el.classList.toggle('selected');");
        sb.append("    const inner = el.querySelector('.j-checkbox-inner');");
        sb.append("    inner.innerText = isSelected ? '✓' : '';");
        sb.append("    const container = el.parentElement;");
        sb.append("    const hidden = document.getElementById(container.id + '_value');");
        sb.append("    if(hidden) {");
        sb.append("      let vals = Array.from(container.querySelectorAll('.j-select-many-item.selected')).map(item => item.getAttribute('data-value'));");
        sb.append("      hidden.value = vals.join(',');");
        sb.append("    }");
        sb.append("  };");
        sb.append("}");
        sb.append("</script>");
        
        sb.append("</").append(getTag()).append(">");
        return sb.toString();
    }

    @Override
    public SelectMany setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public SelectMany setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public SelectMany setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public SelectMany addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public SelectMany removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public SelectMany setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public SelectMany setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public SelectMany addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public SelectMany add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
