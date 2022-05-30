package ru.vsu.cs.aisd.g92.lyigina_p_s.bst;

import ru.vsu.cs.aisd.g92.lyigina_p_s.SimpleBinaryTree;

public class Pair<T extends Comparable<? super T>> extends SimpleBinaryTree<T> {
    private final SimpleTreeNode left;
    private final SimpleTreeNode right;

    public Pair(SimpleTreeNode l, SimpleTreeNode r) {
        left = l;
        right = r;
    }

    public SimpleTreeNode getRight() {
        return right;
    }

    public SimpleTreeNode getLeft() {
        return left;
    }
}
