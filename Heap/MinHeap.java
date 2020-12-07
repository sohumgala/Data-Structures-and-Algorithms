import java.util.ArrayList;

/**
 * Your implementation of a MinHeap.
 *
 * @author Sohum Gala
 * @version 1.0
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     */
    public static final int INITIAL_CAPACITY = 13;


    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null || data.contains(null)) {
            throw new java.lang.IllegalArgumentException("cannot build heap from null ArrayList or ArrayList "
                    + "containing null");
        }
        backingArray = (T[]) new Comparable[2 * data.size() + 1];
        size = data.size();
        for (int i = 1; i <= size; i++) {
            backingArray[i] = data.get(i - 1);
        }
        for (int i = size / 2; i >= 1; i--) {
            // let n represent the index of the parent node that will be compared to its children
            int n = i;
            while (2 * n < backingArray.length && backingArray[2 * n] != null) {
                if (2 * n + 1 < backingArray.length && backingArray[2 * n + 1] != null) {
                    if (backingArray[2 * n].compareTo(backingArray[2 * n + 1]) < 0) {
                        if (backingArray[n].compareTo(backingArray[2 * n]) > 0) {
                            T temp = backingArray[n];
                            backingArray[n] = backingArray[2 * n];
                            backingArray[2 * n] = temp;
                            n *= 2;
                        } else {
                            break;
                        }
                    } else {
                        if (backingArray[n].compareTo(backingArray[2 * n + 1]) > 0) {
                            T temp = backingArray[n];
                            backingArray[n] = backingArray[2 * n + 1];
                            backingArray[2 * n + 1] = temp;
                            n =  2 * n + 1;
                        } else {
                            break;
                        }
                    }
                } else if (backingArray[n].compareTo(backingArray[2 * n]) > 0) {
                    T temp = backingArray[n];
                    backingArray[n] = backingArray[2 * n];
                    backingArray[2 * n] = temp;
                    n *= 2;
                } else {
                    break;
                }
            }
        }
    }

    /**
     * Adds an item to the heap. 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("cannot add null data to data structure");
        }
        if (size + 1 == backingArray.length) {
            T[] temp = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i < backingArray.length; i++) {
                temp[i] = backingArray[i];
            }
            backingArray = temp;
        }
        size++;
        backingArray[size] = data;
        // let n represent the index of a child node that will be compared to its parent for up-heap
        int n = size;
        // while loop is up-heap
        while (n > 1) {
            if (backingArray[n].compareTo(backingArray[n / 2]) < 0) {
                T temp = backingArray[n];
                backingArray[n] = backingArray[n / 2];
                backingArray[n / 2] = temp;
                n /= 2;
            } else {
                break;
            }
        }
    }

    /**
     * Removes and returns the min item of the heap. 
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("cannot remove data from empty data structure");
        }
        T stored = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        // let n represent the index of the parent node that will be compared to its children
        int n = 1;
        // while loop is down-heap
        while (2 * n < backingArray.length && backingArray[2 * n] != null) {
            if (2 * n + 1 < backingArray.length && backingArray[2 * n + 1] != null) {
                if (backingArray[2 * n].compareTo(backingArray[2 * n + 1]) < 0) {
                    if (backingArray[n].compareTo(backingArray[2 * n]) > 0) {
                        T temp = backingArray[n];
                        backingArray[n] = backingArray[2 * n];
                        backingArray[2 * n] = temp;
                        n *= 2;
                    } else {
                        break;
                    }
                } else {
                    if (backingArray[n].compareTo(backingArray[2 * n + 1]) > 0) {
                        T temp = backingArray[n];
                        backingArray[n] = backingArray[2 * n + 1];
                        backingArray[2 * n + 1] = temp;
                        n = 2 * n + 1;
                    } else {
                        break;
                    }
                }
            } else if (backingArray[n].compareTo(backingArray[2 * n]) > 0) {
                T temp = backingArray[n];
                backingArray[n] = backingArray[2 * n];
                backingArray[2 * n] = temp;
                n *= 2;
            } else {
                break;
            }
        }
        return stored;
    }

    /**
     * Returns the minimum element in the heap.
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("cannot retrieve data from empty data structure");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        size = 0;
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }
}
