package io.jettra.wui.components;

public class ChartsBar extends AbstractChart {
    public ChartsBar(String id) {
        super(id, "bar");
        this.initialClasses = "j-chart j-chart-bar";
    }

    @Override
    public ChartsBar setLabels(String... labels) {
        super.setLabels(labels);
        return this;
    }

    @Override
    public ChartsBar addDataset(String label, Number[] data) {
        super.addDataset(label, data);
        return this;
    }

    @Override
    public ChartsBar addDataset(String label, Number[] data, String[] bgColors, String[] borderColors) {
        super.addDataset(label, data, bgColors, borderColors);
        return this;
    }

    @Override
    public ChartsBar setId(String id) {
        super.setId(id);
        return this;
    }

    @Override
    public ChartsBar setProperty(String key, String value) {
        super.setProperty(key, value);
        return this;
    }

    @Override
    public ChartsBar setStyle(String key, String value) {
        super.setStyle(key, value);
        return this;
    }

    @Override
    public ChartsBar addClass(String className) {
        super.addClass(className);
        return this;
    }

    @Override
    public ChartsBar removeClass(String className) {
        super.removeClass(className);
        return this;
    }

    @Override
    public ChartsBar setContent(String content) {
        super.setContent(content);
        return this;
    }

    @Override
    public ChartsBar setUpdate(String ids) {
        super.setUpdate(ids);
        return this;
    }

    @Override
    public ChartsBar addClickListener(io.jettra.wui.events.ClickListener listener) {
        super.addClickListener(listener);
        return this;
    }

    @Override
    public ChartsBar add(io.jettra.wui.core.UIComponent child) {
        super.add(child);
        return this;
    }
}
