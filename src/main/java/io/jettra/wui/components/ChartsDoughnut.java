package io.jettra.wui.components;

public class ChartsDoughnut extends AbstractChart {
    public ChartsDoughnut(String id) {
        super(id, "doughnut");
        this.initialClasses = "j-chart j-chart-doughnut";
    }

    @Override
    public ChartsDoughnut setLabels(String... labels) {
        super.setLabels(labels);
        return this;
    }

    @Override
    public ChartsDoughnut addDataset(String label, Number[] data) {
        super.addDataset(label, data);
        return this;
    }

    @Override
    public ChartsDoughnut addDataset(String label, Number[] data, String[] bgColors, String[] borderColors) {
        super.addDataset(label, data, bgColors, borderColors);
        return this;
    }

    @Override
    public ChartsDoughnut setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public ChartsDoughnut setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public ChartsDoughnut setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public ChartsDoughnut addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public ChartsDoughnut removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public ChartsDoughnut setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public ChartsDoughnut setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public ChartsDoughnut addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public ChartsDoughnut add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
