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
}
