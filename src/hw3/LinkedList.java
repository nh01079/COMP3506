package hw3;

import javax.print.attribute.standard.NumberUp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Singly linked list intended for traversal operation over a tree
 * @param <E>
 */
public class LinkedList<E> implements List<E> {
    /**
     *  Nested class Node for singly linked list implementation
     * @param <E>
     */
    protected static class Node<E>{
        E element;
        Node next;

        public Node(E val, Node<E> next) {
            element = val;
            this.next = next;
        }
    }// end of Node

    // internal parameters
    private Node<E> head;
    private Node<E> tail;
    private int size = 0;

    /**
     * create a size 0 linked list
     */
    public LinkedList(){
        size = 0;
    }

    /**
     * current size of the linked list
     * @return size
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * check if linked list is empty
     * @return true if empty
     */
    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    /**
     * create a new node linked to the tail of the list
     * @param e element to be stored
     * @return true if add operation is successfully performed
     */
    @Override
    public boolean add(E e) {
        if(head==null) {
            head = new Node(e,null);
            tail = head;
        }
        else {
            tail.next = new Node(e, null);
            tail = tail.next;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        if(index<0||index>=size)throw new IndexOutOfBoundsException("invalid index");
        Node<E> runner = head;
        for(int i = index; i>0; i--){
            runner = runner.next;
        }
        return runner.element;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    /**
     * return the item at index i
     * @param index the index of the item that is being removed from the list
     * @return the it
     */
    @Override
    public E remove(int index) {
        if(index<0||index>=size) throw new IndexOutOfBoundsException("invalid index");
        Node<E> runner = head;
        Node<E> prev = null;
        E element;
        for(int i= index; i>0;i--){
            prev = runner;
            runner = runner.next;
        }
        if(prev!=null){
            element = runner.element;
            prev.next = runner.next;

        }else{
            element = head.element;
            head = head.next;
        }
        size--;
        return element;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

}
