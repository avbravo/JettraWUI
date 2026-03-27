package io.jettra.wui.complex;

import io.jettra.wui.components.Div;
import io.jettra.wui.core.UIComponent;
import java.util.ArrayList;
import java.util.List;

public class Panel extends Div {
    private List<Div> columns = new ArrayList<>();

    public Panel(int columnCount) {
        super();
        this.setStyle("display", "grid");
        this.setStyle("grid-template-columns", "repeat(" + columnCount + ", 1fr)");
        this.setStyle("gap", "20px");
        this.setStyle("width", "100%");
        this.addClass("j-panel");

        for (int i = 0; i < columnCount; i++) {
            Div col = new Div();
            col.addClass("j-panel-column");
            columns.add(col);
            super.add(col);
        }
    }

    public Panel add(int columnIndex, UIComponent component) {
        if (columnIndex >= 0 && columnIndex < columns.size()) {
            columns.get(columnIndex).add(component);
        }
        return this;
    }
    
    @Override
    public UIComponent add(UIComponent component) {
        if (!columns.isEmpty()) {
            columns.get(0).add(component);
        } else {
            super.add(component);
        }
        return this;
    }
}
