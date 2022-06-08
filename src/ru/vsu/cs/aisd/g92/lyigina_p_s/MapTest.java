package ru.vsu.cs.aisd.g92.lyigina_p_s;

import ru.vsu.cs.aisd.g92.lyigina_p_s.bst.RndBSTreeMap;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class MapTest {

    public static final int TEST_MAP_SIZE = 1000;
    public static final int MAX_KEY_VALUE = 1000;

    private static <K, V> String toString(Map<K, V> map, boolean ordered) {
        if (map.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Map.Entry<K, V> firstEntry = map.entrySet().iterator().next();
        Map.Entry<K, V>[] entries = map.entrySet().stream().toArray(
                size -> (Map.Entry<K, V>[]) Array.newInstance(firstEntry.getClass(), size)
        );
        if (!ordered && firstEntry.getKey() instanceof Comparable) {
            Arrays.sort(entries, (a, b) -> ((Comparable) a.getKey()).compareTo(b.getKey()));
        }
        for (Map.Entry<K, V> kv : entries) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(String.format("(%s: %s)", kv.getKey(), kv.getValue()));
        }
        return sb.toString();
    }

    private static <K, V> void printMapsState(Map<K, V> rightMap, Map<K, V> testMap, boolean ordered) {
        System.out.printf("%d, %d, %s%n", testMap.size(), rightMap.size(), testMap.size() == rightMap.size());
        String s1 = toString(rightMap, true);
        String s2 = toString(testMap, ordered);
        System.out.println(s1);
        System.out.println(s2);
        System.out.printf("%s%n%n", s1.equals(s2));
    }

    public static void testCorrect(Map<Integer, Integer> testMap, boolean ordered) {
        Map<Integer, Integer> rightMap = new TreeMap<>();
        testMap.clear();
        Random rnd = new Random();

        // добавление элементов
        for (int i = 0; i < TEST_MAP_SIZE; i++) {
            int key = rnd.nextInt(MAX_KEY_VALUE);
            int value = rnd.nextInt(MAX_KEY_VALUE);
            rightMap.put(key, value);
            testMap.put(key, value);
            //System.out.printf("(%s, %s)%n", key, value);
        }
        printMapsState(rightMap, testMap, ordered);

        // удаление некторых элементов
        Integer[] keys = rightMap.keySet().toArray(new Integer[0]);
        for (Integer key : keys) {
            if (rnd.nextInt(2) == 0) {
                rightMap.remove(key);
                testMap.remove(key);
                //System.out.printf("(%s)%n", key);
            }
        }
        printMapsState(rightMap, testMap, ordered);

        // повторное добавление элементов
        for (int i = 0; i < TEST_MAP_SIZE / 2; i++) {
            int key = rnd.nextInt(MAX_KEY_VALUE);
            int value = rnd.nextInt(MAX_KEY_VALUE);
            rightMap.put(key, value);
            testMap.put(key, value);
        }
        printMapsState(rightMap, testMap, ordered);
    }

    public static void testCorrect(Map<Integer, Integer> testMap) {
        testCorrect(testMap, true);
    }

    public static String test() {
        String res = "put:\n";
        Map<Integer, Integer> std = new TreeMap<>();
        Map<Integer, Integer> rnd = new RndBSTreeMap<>();
        long start = System.nanoTime();
        for (int i = 0; i < 2000; i++) {
            std.put(i, i+1);
        }
        res += "std:" + (System.nanoTime()-start)/1000 + "mcrsec\n";
        start = System.nanoTime();
        for (int i = 0; i < 2000; i++) {
            rnd.put(i, i+1);
        }
        res += "rnd:" + (System.nanoTime()-start)/1000 + "mcrsec\n";

        res += "remove:\n";
        start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            std.remove(i);
        }
        res += "std:" + (System.nanoTime()-start)/1000 + "mcrsec\n";
        start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            rnd.remove(i);
        }
        res += "rnd:" + (System.nanoTime()-start)/1000 + "mcrsec\n";

        res += "get value:\n";
        start = System.nanoTime();
        int v = std.get(1001);
        res += "std:" + (System.nanoTime()-start) + "nsec\n";
        start = System.nanoTime();
        v = rnd.get(1001);
        res += "rnd:" + (System.nanoTime()-start) + "nsec\n";
        return res;
    }
}
