package hw3;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * An iterator for the tree ADT that performs a preorder traversal
 */
public class TreeIterator<E> implements Iterator<E> {

    /**
     * Constructs a new tree iterator from the root of the tree to iterate over
     *
     * You are welcome to modify this constructor but cannot change its signature
     * This method should have O(1) time complexity
     */

    // varaiable
    private List<E> out;

    /**
     * create a list and add items in a preorder traversal fashion
     * O(n)
     *
     * @param root the root node of the tree
     */
    public TreeIterator(Tree<E> root) {
        Stack<Tree<E>> stack = new Stack<>();
        out = new LinkedList<E>();
        stack.push(root);
        while(!stack.isEmpty()) {
            Tree<E> node = stack.pop();
            out.add(node.getRoot());
            if(node.isLeaf())continue;
            List<Tree<E>> current = node.getChildren();
            for (int i = current.size() - 1; i >= 0; i--) {
                stack.push(current.get(i));
            }
        }
    }

    /**
     * check if iterator has next
     * O(1) out.isEmpty uses an integer to keep track of the list size
     *
     * @return true if out is not empty
     */
    @Override
    public boolean hasNext() {
        return !out.isEmpty();
    }

    /**
     * get the next element in the list
     * O(1) since only the head node is removed from the singly linked list
     *
     * @return the next element in the list
     */
    @Override
    public E next() {
        if(!hasNext()) throw new NoSuchElementException("Iterator has no next element");
        return out.remove(0);
    }


}
