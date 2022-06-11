package ru.vsu.cs.aisd.g92.lyigina_p_s.bst.charts;

public class ChartDescription {
    private Chart chart;
    private String color;
    private double lineWidth;

    public ChartDescription(Chart chart, String color, double lineWidth) {
        this.chart = chart;
        this.color = color;
        this.lineWidth = lineWidth;
    }

    public Chart getChart() {
        return chart;
    }

    public String getColor() {
        return color;
    }

    public double getLineWidth() {
        return lineWidth;
    }
}
