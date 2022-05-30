package ru.vsu.cs.aisd.g92.lyigina_p_s.bst;

import ru.vsu.cs.aisd.g92.lyigina_p_s.BinaryTree;
import ru.vsu.cs.aisd.g92.lyigina_p_s.SimpleBinaryTree;

import java.util.Random;
import java.util.function.Function;

public class RndBSTree<T extends Comparable<? super T>> extends SimpleBinaryTree<T> implements BSTree<T> {
    private static class CheckBSTResult<T> {
        public boolean result;
        public int size;
        public T min;
        public T max;

        public CheckBSTResult(boolean result, int size, T min, T max) {
            this.result = result;
            this.size = size;
            this.min = min;
            this.max = max;
        }
    }

    int size = 0;

    public RndBSTree(Function<String, T> fromStrFunc, Function<T, String> toStrFunc) {
        super(fromStrFunc, toStrFunc);
    }

    public RndBSTree(Function<String, T> fromStrFunc) {
        super(fromStrFunc);
    }

    public RndBSTree() {
        super();
    }

    private static <T extends Comparable<? super T>> RndBSTree.CheckBSTResult<T> isBSTInner(BinaryTree.TreeNode<T> node) {
        if (node == null) {
            return null;
        }

        RndBSTree.CheckBSTResult<T> leftResult = isBSTInner(node.getLeft());
        RndBSTree.CheckBSTResult<T> rightResult = isBSTInner(node.getRight());
        RndBSTree.CheckBSTResult<T> result = new RndBSTree.CheckBSTResult<>(true, 1, node.getValue(), node.getValue());
        if (leftResult != null) {
            result.result &= leftResult.result;
            result.result &= leftResult.max.compareTo(node.getValue()) < 0;
            result.size += leftResult.size;
            result.min = leftResult.min;
        }
        if (rightResult != null) {
            result.result &= rightResult.result;
            result.size += rightResult.size;
            result.result &= rightResult.min.compareTo(node.getValue()) > 0;
            result.max = rightResult.max;
        }
        return result;
    }

    /**
     * Проверка, является ли поддерево деревом поиска
     * @param <T>
     * @param node Поддерево
     * @return treu/false
     */
    public static <T extends Comparable<? super T>> boolean isBST(BinaryTree.TreeNode<T> node) {
        return node == null ? true : isBSTInner(node).result;
    }

    /**
     * Загрузка дерева из скобочного представления
     * @param bracketStr
     * @throws Exception Если переаддное скобочное представление не является деревом поиска
     */
    @Override
    public void fromBracketNotation(String bracketStr) throws Exception {
        SimpleBinaryTree tempTree = new SimpleBinaryTree(this.fromStrFunc);
        tempTree.fromBracketNotation(bracketStr);
        RndBSTree.CheckBSTResult<T> tempTreeResult = isBSTInner(tempTree.getRoot());
        if (!tempTreeResult.result) {
            throw new Exception("Заданное дерево не является деревом поиска!");
        }
        super.fromBracketNotation(bracketStr);
        this.size = tempTreeResult.size;
    }

    /**
     * Рекурсивное добавление значения в поддерево node
     *
     * @param node Узел, в который (в него или его поддеревья) добавляем
     * значение value
     * @param value Добавляемое значение
     * @return Старое значение, равное value, если есть
     */
    private SimpleTreeNode put(SimpleTreeNode node, T value) {
        Random rand = new Random();
        int r = rand.nextInt(size+1);
        if (r == size)
            node = putAtRoot(node, value);
        else {
            if (value.compareTo(node.value) < 0)
                node = put(node.left, value);
            else
                node = put(node.right, value);
        }
        size++;
        return node;
    }

    private SimpleTreeNode putAtRoot(SimpleTreeNode node, T value) {
        return null;
    }

    /**
     * Рекурсивное удаления значения из поддерева node
     *
     * @param node
     * @param nodeParent Родитель узла
     * @param value
     * @return Старое значение, равное value, если есть
     */
    private T remove(SimpleTreeNode node, SimpleTreeNode nodeParent, T value)
    {
        return null;
    }

    /**
     * Поиск родителя минимально TreeNode в поддереве node
     *
     * @param node Поддерево в котором надо искать родителя минимального элемент
     * @return Узел, содержащий минимальный элемент
     */
    private SimpleTreeNode getMinNodeParent(SimpleTreeNode node) {
        if (node == null) {
            return null;
        }
        SimpleTreeNode parent = null;
        for (; node.left != null; node = node.left) {
            parent = node;
        }
        return parent;
    }

    @Override
    public T put(T value) {
        return null;
    }

    @Override
    public T remove(T value) {
        return null;
    }

    @Override
    public int size() {
        return size;
    }
}