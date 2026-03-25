package io.jettra.wui.complex;

import io.jettra.wui.core.UIComponent;
import java.util.UUID;

public class Datatable extends UIComponent {
    private String tableId;
    
    private static class Element extends UIComponent {
        public Element(String tag) { super(tag); }
    }

    public Datatable() {
        super("div");
        this.tableId = "dt-" + UUID.randomUUID().toString().substring(0, 8);
        setProperty("id", this.tableId + "-container");
        this.addClass("j-datatable-container j-component");
        this.setStyle("display", "flex");
        this.setStyle("flex-direction", "column");
        this.setStyle("gap", "15px");
        this.setStyle("width", "100%");

        // Search Bar container
        UIComponent topBar = new Element("div");
        topBar.setStyle("display", "flex");
        topBar.setStyle("justify-content", "flex-end");
        topBar.setStyle("align-items", "center");

        io.jettra.wui.components.TextBox searchInput = new io.jettra.wui.components.TextBox("text", "search");
        searchInput.setProperty("id", this.tableId + "-search");
        searchInput.setProperty("placeholder", "Buscar...");
        searchInput.addClass("j-input");
        searchInput.setStyle("margin", "0");
        searchInput.setStyle("width", "100%");
        searchInput.setStyle("max-width", "300px");
        topBar.add(searchInput);
        super.add(topBar);

        // Responsive Table Wrapper
        UIComponent tableWrapper = new Element("div");
        tableWrapper.setStyle("width", "100%");
        tableWrapper.setStyle("overflow-x", "auto");

        UIComponent table = new Element("table");
        table.setProperty("id", this.tableId);
        table.addClass("j-datatable");
        table.setStyle("width", "100%");
        table.setStyle("border-collapse", "collapse");

        UIComponent headerRow = new Element("thead");
        UIComponent body = new Element("tbody");
        body.setProperty("id", this.tableId + "-body");

        table.add(headerRow);
        table.add(body);
        tableWrapper.add(table);

        super.add(tableWrapper);

        // Pagination Controls
        UIComponent bottomBar = new Element("div");
        bottomBar.setStyle("display", "flex");
        bottomBar.setStyle("justify-content", "space-between");
        bottomBar.setStyle("align-items", "center");
        bottomBar.setStyle("margin-top", "10px");

        UIComponent info = new Element("span");
        info.setProperty("id", this.tableId + "-info");
        info.setStyle("font-size", "0.9rem");
        info.setStyle("color", "var(--jettra-text)");

        UIComponent paginationBtnGroup = new Element("div");
        paginationBtnGroup.setStyle("display", "flex");
        paginationBtnGroup.setStyle("gap", "5px");

        io.jettra.wui.components.Button btnPrev = new io.jettra.wui.components.Button("Anterior");
        btnPrev.setProperty("id", this.tableId + "-prev");
        btnPrev.addClass("j-btn");
        btnPrev.setStyle("padding", "5px 15px");

        io.jettra.wui.components.Button btnNext = new io.jettra.wui.components.Button("Siguiente");
        btnNext.setProperty("id", this.tableId + "-next");
        btnNext.addClass("j-btn");
        btnNext.setStyle("padding", "5px 15px");

        paginationBtnGroup.add(btnPrev).add(btnNext);
        bottomBar.add(info).add(paginationBtnGroup);
        super.add(bottomBar);
    }

    public void addHeaderRow(io.jettra.wui.components.Row row) {
        row.setStyle("border-bottom", "2px solid var(--jettra-accent)");
        row.setStyle("background", "rgba(0,255,255,0.1)");
        for (UIComponent child : row.getChildren()) {
            if ("td".equals(child.getTag()) || "th".equals(child.getTag())) {
                child.setStyle("padding", "12px");
                child.setStyle("text-align", "left");
                child.setStyle("color", "var(--jettra-accent)");
                child.setStyle("font-weight", "bold");
            }
        }
        
        try {
            getChildren().get(1).getChildren().get(0).getChildren().get(0).add(row);
        } catch (Exception e) {}
    }

    public void addRow(io.jettra.wui.components.Row row) {
        row.setStyle("border-bottom", "1px solid var(--jettra-border)");
        for (UIComponent child : row.getChildren()) {
            child.setStyle("padding", "12px");
        }
        
        try {
            getChildren().get(1).getChildren().get(0).getChildren().get(1).add(row);
        } catch (Exception e) {}
    }

    @Override
    public String render() {
        String baseHtml = super.render();
        String script = "<script>\n" +
            "document.addEventListener('DOMContentLoaded', function() {\n" +
            "   const tableId = '" + this.tableId + "';\n" +
            "   const searchInput = document.getElementById(tableId + '-search');\n" +
            "   const tbody = document.getElementById(tableId + '-body');\n" +
            "   const info = document.getElementById(tableId + '-info');\n" +
            "   const btnPrev = document.getElementById(tableId + '-prev');\n" +
            "   const btnNext = document.getElementById(tableId + '-next');\n" +
            "   if(!tbody) return;\n" +
            "   \n" +
            "   let rows = Array.from(tbody.getElementsByTagName('tr'));\n" +
            "   let filteredRows = [...rows];\n" +
            "   let currentPage = 1;\n" +
            "   const pageSize = 5;\n" +
            "   \n" +
            "   function updateTable() {\n" +
            "       let totalPages = Math.ceil(filteredRows.length / pageSize) || 1;\n" +
            "       if(currentPage > totalPages) currentPage = totalPages;\n" +
            "       if(currentPage < 1) currentPage = 1;\n" +
            "       \n" +
            "       rows.forEach(r => r.style.display = 'none');\n" +
            "       \n" +
            "       let start = (currentPage - 1) * pageSize;\n" +
            "       let end = start + pageSize;\n" +
            "       filteredRows.slice(start, end).forEach(r => r.style.display = '');\n" +
            "       \n" +
            "       let showingStart = filteredRows.length === 0 ? 0 : start + 1;\n" +
            "       let showingEnd = Math.min(end, filteredRows.length);\n" +
            "       if(info) info.innerText = 'Mostrando ' + showingStart + ' a ' + showingEnd + ' de ' + filteredRows.length + ' registros';\n" +
            "       \n" +
            "       if(btnPrev) {\n" +
            "           btnPrev.disabled = currentPage === 1;\n" +
            "           btnPrev.style.opacity = currentPage === 1 ? '0.5' : '1';\n" +
            "       }\n" +
            "       if(btnNext) {\n" +
            "           btnNext.disabled = currentPage === totalPages;\n" +
            "           btnNext.style.opacity = currentPage === totalPages ? '0.5' : '1';\n" +
            "       }\n" +
            "   }\n" +
            "   \n" +
            "   if(searchInput) {\n" +
            "       searchInput.addEventListener('input', function() {\n" +
            "           const query = this.value.toLowerCase();\n" +
            "           filteredRows = rows.filter(r => r.innerText.toLowerCase().includes(query));\n" +
            "           currentPage = 1;\n" +
            "           updateTable();\n" +
            "       });\n" +
            "   }\n" +
            "   \n" +
            "   if(btnPrev) {\n" +
            "       btnPrev.addEventListener('click', function(e) {\n" +
            "           e.preventDefault();\n" +
            "           if(currentPage > 1) { currentPage--; updateTable(); }\n" +
            "       });\n" +
            "   }\n" +
            "   \n" +
            "   if(btnNext) {\n" +
            "       btnNext.addEventListener('click', function(e) {\n" +
            "           e.preventDefault();\n" +
            "           const totalPages = Math.ceil(filteredRows.length / pageSize);\n" +
            "           if(currentPage < totalPages) { currentPage++; updateTable(); }\n" +
            "       });\n" +
            "   }\n" +
            "   \n" +
            "   updateTable();\n" +
            "});\n" +
            "</script>\n";
        
        return baseHtml + script;
    }
}
