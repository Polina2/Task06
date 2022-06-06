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

    int size = 0;//...

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
        return node == null || isBSTInner(node).result;
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
        int size = (node != null)? node.size : 0;
        Random rand = new Random();
        int r = rand.nextInt(size+1);
        if (r == size) {
            if (node == root)
                root = node = putAtRoot(node, value);
            else
                node = putAtRoot(node, value);
        }
        else {
            if (value.compareTo(node.value) < 0) {
                node.left = put(node.left, value);
            }
            else {
                node.right = put(node.right, value);
            }
            node.size++;
        }
        return node;
    }

    private SimpleTreeNode putAtRoot(SimpleTreeNode node, T value) {
        Pair<T> pair = split(node, value);
        return new SimpleTreeNode(value, pair.getLeft(), pair.getRight());
    }

    private Pair<T> split(SimpleTreeNode node, T value) {
        if (node == null)
            return new Pair<>(null, null);
        else {
            if (value.compareTo(node.value) < 0) {
                Pair<T> p = split(node.left, value);
                node.left = p.getRight();
                node.size = 1;
                if (node.left != null)
                    node.size += node.left.size;
                if (node.right != null)
                    node.size += node.right.size;
                return new Pair<>(p.getLeft(), node);
            } else {
                Pair<T> p = split(node.right, value);
                node.right = p.getLeft();
                node.size = 1;
                if (node.left != null)
                    node.size += node.left.size;
                if (node.right != null)
                    node.size += node.right.size;
                return new Pair<>(node, p.getRight());
            }
        }
    }

    /**
     * Рекурсивное удаления значения из поддерева node
     *
     * @param node Узел (поддерево), из которого удаляем значение
     * @param value Удаляемое значение
     * @return Старое значение, равное value, если есть
     */
    private SimpleTreeNode remove(SimpleTreeNode node, T value)
    {
        if (node == null)
            return null;
        if (value.compareTo(node.value) < 0)
            node.left = remove(node.left, value);
        else if (value.compareTo(node.value) > 0)
            node.right = remove(node.right, value);
        else {
            if (node == root)
                root = node = merge(node.left, node.right);
            else
                node = merge(node.left, node.right);
        }
        return node;
    }

    private SimpleTreeNode merge(SimpleTreeNode left, SimpleTreeNode right) {
        if (left == null && right == null)
            return null;
        int lSize = (left != null)? left.size : 0;
        int rSize = (right != null)? right.size : 0;
        int r = new Random().nextInt(lSize + rSize) + 1;
        if (r <= lSize) {
            left.right = merge(left.right, right);
            left.size = 1;
            if (left.left != null)
                left.size += left.left.size;
            if (left.right != null)
                left.size += left.right.size;
            return left;
        } else {
            assert right != null;
            right.left = merge(left, right.left);
            right.size = 1;
            if (right.left != null)
                right.size += right.left.size;
            if (right.right != null)
                right.size += right.right.size;
            return right;
        }
    }

    @Override
    public T put(T value) {
        if (root == null) {
            root = new SimpleTreeNode(value);
            size++;
            return null;
        }
        return put(root, value).value;
    }

    @Override
    public T remove(T value) {
        return remove(root, value).value;
    }

    @Override
    public int size() {
        return size;
    }
}
