package io.jettra.wui.components;

public class ChartsRadar extends AbstractChart {
    public ChartsRadar(String id) {
        super(id, "radar");
        this.initialClasses = "j-chart j-chart-radar";
    }

    @Override
    public ChartsRadar setLabels(String... labels) {
        super.setLabels(labels);
        return this;
    }

    @Override
    public ChartsRadar addDataset(String label, Number[] data) {
        super.addDataset(label, data);
        return this;
    }

    @Override
    public ChartsRadar addDataset(String label, Number[] data, String[] bgColors, String[] borderColors) {
        super.addDataset(label, data, bgColors, borderColors);
        return this;
    }

    @Override
    public ChartsRadar setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public ChartsRadar setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public ChartsRadar setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public ChartsRadar addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public ChartsRadar removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public ChartsRadar setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public ChartsRadar setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public ChartsRadar addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public ChartsRadar add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
