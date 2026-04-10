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
}
