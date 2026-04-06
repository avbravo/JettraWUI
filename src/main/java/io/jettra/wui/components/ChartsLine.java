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
}
