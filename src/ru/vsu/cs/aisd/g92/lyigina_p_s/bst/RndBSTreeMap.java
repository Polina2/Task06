package ru.vsu.cs.aisd.g92.lyigina_p_s.bst;

public class RndBSTreeMap<K extends Comparable<K>, V> implements BSTreeMap<K, V> {
    private final BSTree<MapTreeEntry<K, V>> tree = new RndBSTree<>();

    @Override
    public BSTree<MapTreeEntry<K, V>> getTree() {
        return tree;
    }
}
