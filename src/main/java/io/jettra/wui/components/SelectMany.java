package io.jettra.wui.components;

/**
 * Representa un combo box de seleccion multiple (HTML select multiple).
 * Reformulado para ser consistente con SelectOne.
 */
public class SelectMany extends SelectOne {
    
    public SelectMany(String name) {
        super(name);
        setProperty("multiple", "multiple");
        this.removeClass("j-select").addClass("j-select-many");
        // Default style for multiple selects
        this.setStyle("height", "150px");
    }

    @Override
    public SelectMany setAllowAddItem(boolean allowAddItem) {
        super.setAllowAddItem(allowAddItem);
        return this;
    }

    /**
     * Define si el componente se muestra en bloque o en línea.
     * @param inline
     * @return 
     */
    public SelectMany setInline(boolean inline) {
        if (inline) {
            this.setStyle("display", "block").setStyle("width", "100%");
        } else {
            this.setStyle("display", "inline-block").setStyle("width", "auto");
        }
        return this;
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
