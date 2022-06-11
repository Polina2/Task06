package ru.vsu.cs.aisd.g92.lyigina_p_s.bst.charts;

import java.util.ArrayList;
import java.util.List;

public class Chart {
    public static class Point {
        private double x;
        private double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }
    }

    private List<Point> points = new ArrayList<>();

    public void add(double x, double y){
        points.add(new Point(x, y));
    }

    public void add(Point p){
        add(p.getX(), p.getY());
    }

    public void clear() {
        points.clear();
    }

    public int size() {
        return points.size();
    }

    public Point get(int index) {
        return points.get(index);
    }
}
