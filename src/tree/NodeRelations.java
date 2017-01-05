package tree;

public class NodeRelations<T extends Comparable<T>> {

   Node<T> parent;
   Node<T> grandParent;
   Node<T> uncle;

    public NodeRelations(Node<T> parent, Node<T> grandParent, Node<T> uncle) {
        this.parent = parent;
        this.grandParent = grandParent;
        this.uncle = uncle;
    }

    public Node<T> getParent() {
        return parent;
    }

    public Node<T> getGrandParent() {
        return grandParent;
    }

    public Node<T> getUncle() {
        return uncle;
    }
}
