package tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static tree.Direction.LEFT;
import static tree.Direction.RIGHT;
import static tree.NodeColor.RED;

public class Tree<T extends Comparable<T>> {

    private Node<T> root = null;

    public Tree() {
    }

    public void insert(T value) {
        Node<T> node = insert(root, value);
        if (root == null) {
            updateRoot(node);
        }
    }

    private Node<T> insert(Node<T> node, T value) {
        if (node == null) {
            return new Node<>(value);
        } else if (node.isLessThen(value)) {
            processInsert(node, value, LEFT);
        } else if (node.isGreaterThen(value)) {
            processInsert(node, value, RIGHT);
        }
        return node;
    }

    private void processInsert(Node<T> node, T value, Direction direction) {
        Function<Node<T>, Node<T>> getChild = direction == LEFT ? Node::getLeft : Node::getRight;
        BiConsumer<Node<T>, Node<T>> setChild = direction == LEFT ? Node::setLeft : Node::setRight;
        if (getChild.apply(node) != null) {
            insert(getChild.apply(node), value);
        } else {
            Node<T> newNode = new Node<>(value);
            setChild.accept(node, newNode);
            balance(newNode);
        }
    }

    private void balance(Node<T> node) {
        Node<T> p = node.getParent();
        Node<T> g = node.getGrandParent();
        Node<T> u = node.getUncle();
        if (isBlack(p)) {
            return;
        }
        if (g == null) {
            p.makeBlack();
            return;
        }
        if (isRed(u)) {
            p.makeBlack();
            u.makeBlack();
            if (g.equals(root)) {
                g.makeBlack();
            } else {
                g.makeRed();
                balance(g);
            }
        } else {
            if (p.equals(g.getLeft()) && p.getLeft().equals(node)) {
                rightRotate(g);
            } else if (p.equals(g.getLeft()) && p.getRight().equals(node)) {
                rightRotate(p);
                rightRotate(g);
            } else if (p.equals(g.getRight()) && p.getRight().equals(node)) {
                leftRotate(g);
            } else if (p.equals(g.getRight()) && p.getLeft().equals(node)) {
                leftRotate(p);
                leftRotate(g);
            }
        }
    }

    private void leftRotate(Node<T> x) {
        Node<T> p = x.getParent();
        Node<T> y = x.getRight();
        x.setRight(y.getLeft())
                .makeRed();
        y.setLeft(x)
                .makeBlack();
        if (p != null) {
            p.setRight(y);
        }
        if (root.equals(x)) {
            updateRoot(y);
        }
    }

    private void rightRotate(Node<T> x) {
        Node<T> p = x.getParent();
        Node<T> y = x.getLeft();
        x.setLeft(y.getRight())
                .makeRed();
        y.setRight(x)
                .makeBlack();
        if (p != null) {
            p.setLeft(y);
        }
        if (root.equals(x)) {
            updateRoot(y);
        }
    }

    private void updateRoot(Node<T> newRootNode) {
        root = newRootNode;
        newRootNode.setParent(null);
    }


    public Node<T> find(T value) {
        return find(root, value);
    }

    private Node<T> find(Node<T> node, T value) {
        if (node == null) {
            return null;
        }
        if (node.isLessThen(value)) {
            return find(node.getLeft(), value);
        }
        if (node.isGreaterThen(value)) {
            return find(node.getRight(), value);
        }
        return node;
    }

    public void remove(T value) {
        remove(root, value);
    }

    private void remove(Node<T> node, T value) {
        boolean currentNodeShouldBeDeleted = node.getValue().equals(value);
        if(currentNodeShouldBeDeleted){
            if(node.hasBothChildren()) {
                Node<T> smallestNodeInTheRightSubTree = findMin(node.getRight());
                node.setValue(smallestNodeInTheRightSubTree.getValue());
                remove(node.getRight(), node.getValue());
            } else{
                Node<T> child = node.getLeft() == null ? node.getRight() : node.getLeft();
                if(node.isLeftChild()){
                    node.getParent().setLeft(child);
                } else {
                    node.getParent().setRight(child);
                }
            }
        }
        if(node.isLessThen(value)) {
            remove(node.getRight(), value);
        } else {
            remove(node.getLeft(), value);
        }
    }

    protected Node<T> findMin(Node<T> node) {
        if (node != null) {
            while (node.getLeft() != null) {
                node = node.getLeft();
            }
        }
        return node;
    }

    public boolean isRed(Node<T> node) {
        return node != null && node.getColor() == RED;
    }

    public boolean isBlack(Node<T> node) {
        return !isRed(node);
    }


    public Node<T> breadthFirstSearch(T value) {
        return breadthFirstSearch(root, value);
    }

    private Node<T> breadthFirstSearch(Node<T> node, T value) {
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            if (!queue.isEmpty()) {
                node = queue.poll();
            }
            if (node.getValue().equals(value)) {
                return node;
            }
            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }
        return null;
    }

    public Node<T> depthFirstSearch(T value) {
        return depthFirstSearch(root, value);
    }

    private Node<T> depthFirstSearch(Node<T> node, T value) {
        Node<T> resultNode = null;
        if (node.getValue() == value) {
            resultNode = node;
        }
        if (node.getLeft() != null && resultNode == null) {
            resultNode = depthFirstSearch(node.getLeft(), value);
        }
        if (node.getRight() != null && resultNode == null) {
            resultNode = depthFirstSearch(node.getRight(), value);
        }
        return resultNode;
    }

    @Override
    public String toString() {
        return root.toString();
    }

    public Node<T> getRoot() {
        return root;
    }


}
