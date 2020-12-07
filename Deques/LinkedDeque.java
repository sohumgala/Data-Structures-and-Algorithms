/**
 * My LinkedDeque Implementation
 *
 * @author Sohum Gala
 * @version 1.0
 */
public class LinkedDeque<T> {

    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    /**
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot add null data to data structure");
        }
        LinkedNode<T> newNode = new LinkedNode<T>(data);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        }
        size++;

    }

    /**
     * Adds the element to the back of the deque.
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot insert null data into data structure");
        }
        LinkedNode<T> newNode = new LinkedNode<T>(data);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the first element of the deque.
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Cannot remove element if the Linked List is empty");
        }
        T stored = head.getData();
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            head = head.getNext();
            head.setPrevious(null);
        }
        size--;
        return stored;
    }

    /**
     * Removes and returns the last element of the deque.
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Cannot remove element if the Linked List is empty");
        }
        T stored = tail.getData();
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            tail = tail.getPrevious();
            tail.setNext(null);
        }
        size--;
        return stored;
    }

    /**
     * Returns the first data of the deque without removing it.
     * @return the data located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Cannot retrieve data from empty data structure");
        } else {
            return head.getData();
        }
    }

    /**
     * Returns the last data of the deque without removing it.
     * @return the data located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Cannot retrieve data from empty data structure");
        } else {
            return tail.getData();
        }
    }
}
