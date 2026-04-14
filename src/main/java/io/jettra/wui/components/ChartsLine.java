package io.jettra.wui.components;

public class ChartsLine extends AbstractChart {
    public ChartsLine(String id) {
        super(id, "line");
        this.initialClasses = "j-chart j-chart-line";
    }

    @Override
    public ChartsLine setLabels(String... labels) {
        super.setLabels(labels);
        return this;
    }

    @Override
    public ChartsLine addDataset(String label, Number[] data) {
        super.addDataset(label, data);
        return this;
    }

    @Override
    public ChartsLine addDataset(String label, Number[] data, String[] bgColors, String[] borderColors) {
        super.addDataset(label, data, bgColors, borderColors);
        return this;
    }

    @Override
    public ChartsLine setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public ChartsLine setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public ChartsLine setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public ChartsLine addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public ChartsLine removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public ChartsLine setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public ChartsLine setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public ChartsLine addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public ChartsLine add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
