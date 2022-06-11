package ru.vsu.cs.aisd.g92.lyigina_p_s.bst.charts;

import java.io.PrintStream;
import java.util.List;
import java.util.Locale;

public class SVGPlotter {
    public static class Bounds{
        private double left, right, top, bottom;

        public Bounds(double left, double right, double top, double bottom) {
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
        }

        public double getLeft() {
            return left;
        }

        public double getRight() {
            return right;
        }

        public double getTop() {
            return top;
        }

        public double getBottom() {
            return bottom;
        }

        public double getWidth() {
            return Math.abs(getLeft() - getRight());
        }

        public double getHeight() {
            return Math.abs(getTop() - getBottom());
        }
    }

    private static void drawLine(PrintStream out, double x1, double y1, double x2, double y2, String color, double width) {
        out.printf(Locale.US, "<line x1=\"%f\" y1=\"%f\" x2=\"%f\" y2=\"%f\" stroke=\"%s\" stroke-width=\"%f\"/>\n",
                x1, y1, x2, y2, color, width);
    }

    private static double convertX(double x, double width) {
        return x + width/2;
    }

    private static double convertY(double y, double height) {
        return height/2 - y;
    }

    public static void plot(PrintStream out, List<ChartDescription> charts, Bounds bounds) {
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
        out.printf(Locale.US, "<svg xmlns=\"http://www.w3.org/2000/svg\" " +
                        "    version=\"1.1\"" +
                        "    width=\"%f\" height=\"%f\"  viewBox=\"%f %f %f %f\">\n",
                bounds.getWidth()*100, bounds.getHeight()*100,
                convertX(bounds.getLeft(), bounds.getWidth()*100), convertY(bounds.getTop(), bounds.getHeight()*100),
                bounds.getWidth(), bounds.getHeight());

        double axisWidth = Math.min(bounds.getWidth(), bounds.getHeight())*0.001;
        double x0 = bounds.getWidth()*100/2;
        double y0 = bounds.getHeight()*100/2;
        drawLine(out, x0, bounds.getTop()*100, x0, bounds.getBottom()*100, "black", axisWidth);
        drawLine(out, bounds.getLeft()*100, y0, bounds.getRight()*100, y0, "black", axisWidth);

        for (int ch = 0; ch < charts.size(); ch++) {
            ChartDescription cd = charts.get(ch);
            Chart chart = cd.getChart();
            out.printf(Locale.US, "<polyline fill=\"none\" stroke=\"%s\" stroke-width=\"%f\" points=\"",
                    cd.getColor(), cd.getLineWidth());
            for (int i = 0; i < chart.size(); i++) {
                Chart.Point p = chart.get(i);
                out.printf(Locale.US, "%f,%f ", convertX(p.getX(), bounds.getWidth()*100),
                        convertY(p.getY(), bounds.getHeight()*100));
            }
            out.println("\"/>\n");
        }
        out.println("</svg>");
    }
}
