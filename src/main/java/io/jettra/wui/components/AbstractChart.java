package io.jettra.wui.components;

import io.jettra.wui.core.UIComponent;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractChart extends UIComponent {
    protected String chartId;
    protected String[] labels = new String[0];
    protected List<Dataset> datasets = new ArrayList<>();
    protected String type;

    public static class Dataset {
        public String label;
        public Number[] data;
        public String[] backgroundColor;
        public String[] borderColor;
        
        public Dataset(String label, Number[] data) {
            this.label = label;
            this.data = data;
        }
    }

    public AbstractChart(String id, String type) {
        super("canvas");
        this.chartId = (id != null && !id.isEmpty()) ? id : "chart-" + UUID.randomUUID().toString().substring(0,8);
        this.type = type;
        setProperty("id", this.chartId);
        setStyle("width", "100%");
        setStyle("min-height", "250px");
        setStyle("max-width", "600px");
        setStyle("max-height", "400px");
    }

    public AbstractChart setLabels(String... labels) {
        this.labels = labels;
        return this;
    }

    public AbstractChart addDataset(String label, Number[] data) {
        this.datasets.add(new Dataset(label, data));
        return this;
    }

    public AbstractChart addDataset(String label, Number[] data, String[] bgColors, String[] borderColors) {
        Dataset ds = new Dataset(label, data);
        ds.backgroundColor = bgColors;
        ds.borderColor = borderColors;
        this.datasets.add(ds);
        return this;
    }

    @Override
    public String render() {
        StringBuilder js = new StringBuilder();
        String safeId = this.chartId.replaceAll("-", "_");
        
        // Chart.js Extension (Offline Load)
        js.append(ChartJSExtension.getInlineScript());
        
        js.append("<script>\n");
        js.append("setTimeout(function() { jettraInitChart_").append(safeId).append("(); }, 100);\n");
        
        js.append("function jettraInitChart_").append(safeId).append("() {\n");
        js.append("  var el = document.getElementById('").append(this.chartId).append("');\n");
        js.append("  if(!el) return;\n");
        js.append("  var ctx = el.getContext('2d');\n");
        js.append("  if(window.").append(safeId).append("Chart) { window.").append(safeId).append("Chart.destroy(); }\n");
        js.append("  window.").append(safeId).append("Chart = new Chart(ctx, {\n");
        js.append("    type: '").append(this.type).append("',\n");
        js.append("    data: {\n");
        
        js.append("      labels: [");
        for (int i = 0; i < labels.length; i++) {
            js.append("'").append(labels[i]).append(i < labels.length - 1 ? "', " : "'");
        }
        js.append("],\n");

        js.append("      datasets: [\n");
        for (int i = 0; i < datasets.size(); i++) {
            Dataset ds = datasets.get(i);
            js.append("        {\n");
            js.append("          label: '").append(ds.label).append("',\n");
            js.append("          data: [");
            for (int j = 0; j < ds.data.length; j++) {
                js.append(ds.data[j]).append(j < ds.data.length - 1 ? ", " : "");
            }
            js.append("],\n");
            
            if (ds.backgroundColor != null) {
                js.append("          backgroundColor: [");
                for (int j = 0; j < ds.backgroundColor.length; j++) {
                    js.append("'").append(ds.backgroundColor[j]).append(j < ds.backgroundColor.length - 1 ? "', " : "'");
                }
                js.append("],\n");
            }
            
            if (ds.borderColor != null) {
                js.append("          borderColor: [");
                for (int j = 0; j < ds.borderColor.length; j++) {
                    js.append("'").append(ds.borderColor[j]).append(j < ds.borderColor.length - 1 ? "', " : "'");
                }
                js.append("],\n");
                js.append("          borderWidth: 1\n");
            }

            js.append("        }");
            if (i < datasets.size() - 1) js.append(",");
            js.append("\n");
        }
        js.append("      ]\n");
        js.append("    },\n");
        js.append("    options: {\n");
        js.append("      responsive: true,\n");
        js.append("      maintainAspectRatio: false,\n");
        js.append("      plugins: { legend: { labels: { color: '#a9b7c6' } } },\n");
        if (type.equals("bar") || type.equals("line")) {
            js.append("      scales: { x: { ticks: { color: '#a9b7c6' }, grid: { color: 'rgba(255,255,255,0.1)' } }, y: { beginAtZero:true, ticks: { color: '#a9b7c6' }, grid: { color: 'rgba(255,255,255,0.1)' } } }\n");
        } else if (type.equals("radar")) {
            js.append("      scales: { r: { ticks: { color: '#a9b7c6', backdropColor: 'transparent' }, grid: { color: 'rgba(255,255,255,0.2)' }, angleLines: { color: 'rgba(255,255,255,0.2)' }, pointLabels: { color: '#a9b7c6' } } }\n");
        }
        js.append("    }\n");
        js.append("  });\n");
        js.append("}\n");
        js.append("</script>\n");

        return super.render() + js.toString();
    }

    @Override
    public AbstractChart setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public AbstractChart setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public AbstractChart setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public AbstractChart addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public AbstractChart removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public AbstractChart setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public AbstractChart setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public AbstractChart addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public AbstractChart add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
