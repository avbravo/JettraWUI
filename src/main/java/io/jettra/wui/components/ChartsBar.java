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
}
