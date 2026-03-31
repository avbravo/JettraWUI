package io.jettra.wui.complex;

import io.jettra.wui.components.Div;
import io.jettra.wui.components.Header;
import io.jettra.wui.core.UIComponent;
import java.util.ArrayList;
import java.util.List;

/**
 * Board Component: A basic board layout with a header and organized columns.
 * Ideal for Dashboards, Kanban, and Scrum visualizations.
 */
public class Board extends Div {
    private final Header boardHeader;
    private final Div gridContainer;
    private final List<Div> columns = new ArrayList<>();

    public Board(String title, int columnCount) {
        super();
        this.addClass("j-board");
        this.setStyle("display", "flex");
        this.setStyle("flex-direction", "column");
        this.setStyle("gap", "20px");
        this.setStyle("width", "100%");
        this.setStyle("padding", "25px");
        this.setStyle("background", "rgba(15, 23, 42, 0.4)");
        this.setStyle("border-radius", "20px");
        this.setStyle("border", "1px solid rgba(0, 255, 255, 0.15)");
        this.setStyle("backdrop-filter", "blur(10px)");
        this.setStyle("box-shadow", "0 10px 30px rgba(0, 0, 0, 0.3)");

        // Header section
        this.boardHeader = new Header(2, title);
        this.boardHeader.setStyle("margin", "0 0 10px 0");
        this.boardHeader.setStyle("color", "var(--jettra-accent, #00ffff)");
        this.boardHeader.setStyle("font-weight", "800");
        this.boardHeader.setStyle("letter-spacing", "1px");
        this.boardHeader.setStyle("text-shadow", "0 0 15px rgba(0, 255, 255, 0.3)");
        super.add(this.boardHeader);

        // Columns container
        this.gridContainer = new Div();
        this.gridContainer.setStyle("display", "grid");
        this.gridContainer.setStyle("grid-template-columns", "repeat(" + columnCount + ", 1fr)");
        this.gridContainer.setStyle("gap", "20px");
        this.gridContainer.setStyle("min-height", "500px");
        super.add(this.gridContainer);

        for (int i = 0; i < columnCount; i++) {
            Div col = new Div();
            col.addClass("j-board-column");
            col.setProperty("data-board-column", String.valueOf(i));
            col.setStyle("background", "rgba(30, 41, 59, 0.5)");
            col.setStyle("border-radius", "16px");
            col.setStyle("padding", "20px");
            col.setStyle("border", "2px dashed rgba(0, 255, 255, 0.05)");
            col.setStyle("transition", "all 0.3s ease");
            col.setStyle("display", "flex");
            col.setStyle("flex-direction", "column");
            col.setStyle("gap", "15px");
            columns.add(col);
            this.gridContainer.add(col);
        }
    }

    public Board addComponent(int columnIndex, UIComponent component) {
        if (columnIndex >= 0 && columnIndex < columns.size()) {
            columns.get(columnIndex).add(component);
        }
        return this;
    }

    public Header getBoardHeader() {
        return boardHeader;
    }

    public List<Div> getColumns() {
        return columns;
    }

    public Div getColumn(int index) {
        if (index >= 0 && index < columns.size()) {
            return columns.get(index);
        }
        return null;
    }
}
