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
}
