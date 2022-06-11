package ru.vsu.cs.aisd.g92.lyigina_p_s.bst.charts;

import ru.vsu.cs.aisd.g92.lyigina_p_s.bst.RndBSTreeMap;

import java.util.Map;
import java.util.TreeMap;

public class Calculator {
    @FunctionalInterface
    public interface Counter{
        double count(double x, Map<Integer, Integer> map);
    }

    private static Chart getGraphic(double from, double to, int count, Counter counter, Map<Integer, Integer> map) {
        Chart chart = new Chart();
        double step = (to - from)/(count - 1);
        for (int i = 0; i < count; i++) {
            double x = from + i*step;
            double y = getAverage(x, 20, counter, map);
            chart.add(x/100, y);
        }
        return chart;
    }

    private static double getAverage(double x, int count, Counter c, Map<Integer, Integer> map) {
        double s = 0;
        for (int i = 0; i < count; i++) {
            s += c.count(x, map);
        }
        return s/count;
    }

    private static void putNValues(Map<Integer, Integer> map, int n) {
        for (int i = 0; i < n; i++) {
            map.put(i, i);
        }
    }

    public static Chart getPut(double from, double to, int count, Map<Integer, Integer> map) {
        Chart chart = new Chart();
        double step = (to - from)/(count - 1);
        for (int i = 0; i < count; i++) {
            double x = from + i*step;
            if (i == 0)
                putNValues(map, (int)from);
            else
                putNValues(map, (int)(step-1));
            long start = System.nanoTime();
            map.put(2*i, i);
            chart.add(x/100, (System.nanoTime()-start)/1000.0);
        }
        return chart;
    }

/*
    public static double test(double x){
        return Math.sin(x);
    }
*/
    public static Chart getStdPut(int from, int to, int count) {
        return getGraphic(from, to, count, Calculator::putTime, new TreeMap<>());
    }

    public static Chart getRndPut(int from, int to, int count) {
        return getGraphic(from, to, count, Calculator::putTime, new RndBSTreeMap<>());
    }

    public static Chart getStdRemove(int from, int to, int count) {
        return getGraphic(from, to, count, Calculator::removeTime, new TreeMap<>());
    }

    public static Chart getRndRemove(int from, int to, int count) {
        return getGraphic(from, to, count, Calculator::removeTime, new RndBSTreeMap<>());
    }

    public static Chart getStdGetValue(int from, int to, int count) {
        return getGraphic(from, to, count, Calculator::getValueTime, new TreeMap<>());
    }

    public static Chart getRndGetValue(int from, int to, int count) {
        return getGraphic(from, to, count, Calculator::getValueTime, new RndBSTreeMap<>());
    }

    private static double putTime(double x, Map<Integer, Integer> map) {

        long start = System.nanoTime();
        for (int i = 0; i < x; i++) {
            map.put(i, i+1);
        }
        return (System.nanoTime()-start)/1000000.0;
    }

    private static double removeTime(double x, Map<Integer, Integer> map) {
        for (int i = 0; i < x; i++) {
            map.put(i, i+1);
        }
        long start = System.nanoTime();
        for (int i = 0; i < x; i++){
            map.remove(i);
        }
        return (System.nanoTime()-start)/100000.0;
    }

    private static double getValueTime(double x, Map<Integer, Integer> map) {
        for (int i = 0; i < x; i++) {
            map.put(i, i+1);
        }
        long start = System.nanoTime();
        for (int i = 0; i < x; i++) {
            boolean b = map.containsKey(i);
        }
        return (System.nanoTime()-start)/100000.0;
    }
}
