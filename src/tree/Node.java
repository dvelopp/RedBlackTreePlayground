package tree;

import java.util.Objects;

import static tree.Main.currentNode;
import static tree.NodeColor.BLACK;
import static tree.NodeColor.RED;

public class Node<T extends Comparable<T>> {

    int id = currentNode++;

    private Node<T> parent;
    private Node<T> left;
    private Node<T> right;
    private T value;
    private NodeColor color = RED;

    public Node(T value) {
        this.value = value;
    }

    public Node<T> getLeft() {
        return left;
    }

    public Node<T> getRight() {
        return right;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> makeBlack() {
        this.color = BLACK;
        return this;
    }

    public Node<T> makeRed() {
        this.color = RED;
        return this;
    }

    public Node<T> setRight(Node<T> right) {
        this.right = right;
        if (right != null) {
            right.setParent(this);
        }
        return this;
    }

    public Node<T> setLeft(Node<T> left) {
        this.left = left;
        if (left != null) {
            left.setParent(this);
        }
        return this;
    }

    public boolean isLessThen(T value) {
        return getValue().compareTo(value) < 0;
    }

    public boolean isGreaterThen(T value) {
        return getValue().compareTo(value) > 0;
    }

    public NodeColor getColor() {
        return color;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Node<T> getParent() {
        return parent;
    }

    public Node<T> getUncle() {
        if (parent == null) {
            return null;
        }
        Node<T> grandParent = getGrandParent();
        if (grandParent == null) {
            return null;
        }
        return getParent().equals(grandParent.getLeft()) ? grandParent.getRight() : grandParent.getLeft();
    }

    public Node<T> getGrandParent() {
        if (parent == null) {
            return null;
        }
        return this.parent.getParent();
    }

    public NodeRelations<T> getFamily(Node<T> node) {
        return new NodeRelations<>(getParent(), getGrandParent(), getUncle());
    }

    public boolean hasBothChildren() {
        return left != null && right != null;
    }

    public boolean isLeftChild() {
        if (parent == null) {
            throw new IllegalArgumentException("Current node is a root node");
        }
        return parent.left == this;
    }

    @Override
    public String toString() {
        return getValue().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(id, node.id) &&
                Objects.equals(left, node.left) &&
                Objects.equals(right, node.right) &&
                Objects.equals(value, node.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, left, right, value);
    }
}
