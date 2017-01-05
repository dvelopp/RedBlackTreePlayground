package tree;

import java.io.FileNotFoundException;

public class Main {

    public static int currentNode = 0;

    public static void main(String[] args) throws FileNotFoundException {
        Tree<Integer> tree = new Tree<>();
        for(int i = 20; i > 1; i--){
            insert(tree, i);
        }
        tree.remove(10);
        TreePrinter.print(tree.getRoot());

    }

    private static void insert(Tree<Integer> tree, int i) {
        tree.insert(i);
        TreePrinter.print(tree.getRoot());
        System.out.println(" === ");
    }

}
