package ru.vsu.cs.aisd.g92.lyigina_p_s.bst.charts;

import ru.vsu.cs.aisd.g92.lyigina_p_s.bst.RndBSTreeMap;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.TreeMap;

public class MainForCharts {
    public static void main(String[] args) {
        final int start = 0;
        final int end = 1000;
        final int count = 200;
        SVGPlotter.Bounds bounds = new SVGPlotter.Bounds(-0.5, 11, 6, -0.5);
/*
        ChartDescription stdPut = new ChartDescription(
                Calculator.getStdPut(start, end, count), "red", 0.02
        );
        ChartDescription rndPut = new ChartDescription(
                Calculator.getRndPut(start, end, count), "blue", 0.02
        );

        ChartDescription stdRemove = new ChartDescription(
                Calculator.getStdRemove(start, end, count), "red", 0.01
        );
        ChartDescription rndRemove = new ChartDescription(
                Calculator.getRndRemove(start, end, count), "blue", 0.01
        );

        ChartDescription stdGetValue = new ChartDescription(
                Calculator.getStdGetValue(start, end, count), "red", 0.01
        );
        ChartDescription rndGetValue = new ChartDescription(
                Calculator.getRndGetValue(start, end, count), "blue", 0.01
        );
*/
        ChartDescription stdPut2 = new ChartDescription(
                Calculator.getPut(start, end, count, new TreeMap<>()), "red", 0.01
        );
        ChartDescription rndPut2 = new ChartDescription(
                Calculator.getPut(start, end, count, new RndBSTreeMap<>()), "blue", 0.01
        );

        //ChartDescription test = new ChartDescription(Calculator.getGraphic(start,end, count, Calculator::test), "red", 0.008);

        try {
            //SVGPlotter.plot(new PrintStream("./test.svg"), Arrays.asList(stdPut, rndPut), bounds);
            //SVGPlotter.plot(new PrintStream("./test2.svg"), Arrays.asList(stdRemove, rndRemove), bounds);
            //SVGPlotter.plot(new PrintStream("./test3.svg"), Arrays.asList(stdGetValue, rndGetValue), bounds);
            SVGPlotter.plot(new PrintStream("./test4.svg"), Arrays.asList(stdPut2, rndPut2), bounds);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
