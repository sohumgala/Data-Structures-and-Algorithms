/**
 * My ArrayList Implementation
 *
 * @author Sohum Gala
 * @version 1.0
*/
public class ArrayList<T> {

    public static final int INITIAL_CAPACITY = 9;
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     */
    public ArrayList() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds the element to the specified index.
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new java.lang.IndexOutOfBoundsException("The index provided should be between 0 and " + size);
        } else if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot insert null data into data structure");
        } else {
            if (backingArray.length < size + 1) {
                T[] temp = (T[]) new Object[backingArray.length * 2];
                for (int i = 0; i < backingArray.length; i++) {
                    temp[i] = backingArray[i];
                }
                backingArray = temp;
            }
            for (int i = size; i > index; i--) {
                backingArray[i] = backingArray[i - 1];
            }
            backingArray[index] = data;
            size++;
        }
    }

    /**
     * Adds the element to the front of the list
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    /**
     * Adds the element to the back of the list.
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the element at the specified index.
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException("The index provided should be between 0 and " + size);
        } else {
            T stored = backingArray[index];
            for (int i = index; i < size - 1; i++) {
                backingArray[i] = backingArray[i + 1];
            }

            size--;
            backingArray[size] = null;
            return stored;
        }
    }

    /**
     * Removes and returns the first element of the list.
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("The list is empty, therefore you cannot remove an element");
        } else {
            return removeAtIndex(0);
        }
    }

    /**
     * Removes and returns the last element of the list.*
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("The list is empty, therefore you cannot remove an element");
        } else {
            return removeAtIndex(size - 1);
        }
    }

    /**
     * Returns the element at the specified index.
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException("The index provided should be between 0 and " + (size - 1));
        } else {
            return backingArray[index];
        }
    }

    /**
     * Returns whether or not the list is empty.
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list
    */
    public void clear() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }
}
