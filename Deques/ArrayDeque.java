/**
 * My ArrayDeque Implementation
 * @author Sohum Gala
 * @version 1.0
 */
public class ArrayDeque<T> {

    public static final int INITIAL_CAPACITY = 11;
    private T[] backingArray;
    private int front;
    private int size;

    /**
     * Constructs a new ArrayDeque.
     */
    public ArrayDeque() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    /**
     * Adds the element to the front of the deque.
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot add null data to data structure");
        }
        if (size == backingArray.length) {
            T[] resizedBackingArray = (T[]) new Object[backingArray.length * 2];
            resizedBackingArray[0] = data;
            for (int i = 0; i < size; i++) {
                resizedBackingArray[i + 1] = backingArray[mod(front + i, backingArray.length)];
            }
            backingArray = resizedBackingArray;
            front = 0;
            size++;
        } else {
            backingArray[mod(front - 1, backingArray.length)] = data;
            front = mod(front - 1, backingArray.length);
            size++;
        }
    }

    /**
     * Adds the element to the back of the deque.
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot add null data to data structure");
        }
        if (size == backingArray.length) {
            T[] resizedBackingArray = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < size; i++) {
                resizedBackingArray[i] = backingArray[mod(front + i, backingArray.length)];
            }
            resizedBackingArray[size] = data;
            backingArray = resizedBackingArray;
            front = 0;
            size++;
        } else {
            backingArray[mod(front + size, backingArray.length)] = data;
            size++;
        }
    }

    /**
     * Removes and returns the first element of the deque.
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */

    public T removeFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Cannot remove element from empty data structure");
        } else {
            T stored = backingArray[front];
            backingArray[front] = null;
            front = mod(front + 1, backingArray.length);
            size--;
            return stored;
        }
    }

    /**
     * Removes and returns the last element of the deque.
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Cannot remove element from empty data structure");
        } else {
            T stored = backingArray[mod(front + size - 1, backingArray.length)];
            backingArray[mod(front + size - 1, backingArray.length)] = null;
            size--;
            return stored;
        }
    }

    /**
     * Returns the first data of the deque without removing it.
     * @return the first data
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Cannot retrieve data from empty data structure");
        }
        return backingArray[front];
    }

    /**
     * Returns the last data of the deque without removing it.
     * @return the last data
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Cannot retrieve data from empty data structure");
        }
        return backingArray[mod(front + size - 1, backingArray.length)];
    }
    /**
     * Returns the smallest non-negative remainder when dividing index by
     * modulo. So, for example, if modulo is 5, then this method will return
     * either 0, 1, 2, 3, or 4, depending on what the remainder is.
     * @param index  the number to take the remainder of
     * @param modulo the divisor to divide by
     * @return the remainder in its smallest non-negative form
     * @throws java.lang.IllegalArgumentException if the modulo is non-positive
     */
    private static int mod(int index, int modulo) {
        
        if (modulo <= 0) {
            throw new IllegalArgumentException("The modulo must be positive");
        }
        int newIndex = index % modulo;
        return newIndex >= 0 ? newIndex : newIndex + modulo;
    }
}
