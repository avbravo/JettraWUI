package io.jettra.wui.components;

public class ChartsPie extends AbstractChart {
    public ChartsPie(String id) {
        super(id, "pie");
        this.initialClasses = "j-chart j-chart-pie";
    }

    @Override
    public ChartsPie setLabels(String... labels) {
        super.setLabels(labels);
        return this;
    }

    @Override
    public ChartsPie addDataset(String label, Number[] data) {
        super.addDataset(label, data);
        return this;
    }

    @Override
    public ChartsPie addDataset(String label, Number[] data, String[] bgColors, String[] borderColors) {
        super.addDataset(label, data, bgColors, borderColors);
        return this;
    }

    @Override
    public ChartsPie setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public ChartsPie setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public ChartsPie setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public ChartsPie addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public ChartsPie removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public ChartsPie setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public ChartsPie setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public ChartsPie addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public ChartsPie add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
