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
}
