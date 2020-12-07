/**
 * My DoublyLinkedList Implementation
 *
 * @author Sohum Gala
 * @version 1.0
 */
public class DoublyLinkedList<T> {

    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    /**
     * Adds the element to the specified index
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
        }

        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data);
            if (index < size / 2) {
                DoublyLinkedListNode<T> curr = head;
                for (int i = 0; i < index; i++) {
                    curr = curr.getNext();
                }
                newNode.setNext(curr);
                newNode.setPrevious(curr.getPrevious());
                curr.getPrevious().setNext(newNode);
                curr.setPrevious(newNode);
            } else {
                DoublyLinkedListNode<T> curr = tail;
                for (int i = size - 1; i > index; i--) {
                    curr = curr.getPrevious();
                }
                newNode.setNext(curr);
                newNode.setPrevious(curr.getPrevious());
                curr.getPrevious().setNext(newNode);
                curr.setPrevious(newNode);
            }
            size++;
        }

    }

    /**
     * Adds the element to the front of the list.
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot insert null data into data structure");
        }
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data);
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
     * Adds the element to the back of the list.
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot insert null data into data structure");
        }
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data);
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
     * Removes and returns the element at the specified index. 
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException("Index provided should be between 0 and " + (size - 1));
        }
        if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            if (index < size / 2) {
                DoublyLinkedListNode<T> curr = head;
                for (int i = 0; i < index; i++) {
                    curr = curr.getNext();
                }
                T stored = curr.getData();
                curr.getPrevious().setNext(curr.getNext());
                curr.getNext().setPrevious(curr.getPrevious());
                size--;
                return stored;
            } else {
                DoublyLinkedListNode<T> curr = tail;
                for (int i = size - 1; i > index; i--) {
                    curr = curr.getPrevious();
                }
                T stored = curr.getData();
                curr.getPrevious().setNext(curr.getNext());
                curr.getNext().setPrevious(curr.getPrevious());
                size--;
                return stored;
            }

        }
    }

    /**
     * Removes and returns the first element of the list.
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
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
     * Removes and returns the last element of the list.
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
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
     * Returns the element at the specified index. 
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException("Index provided should be between 0 and " + (size - 1));
        }
        if (index < size / 2) {
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            return curr.getData();
        } else {
            DoublyLinkedListNode<T> curr = tail;
            for (int i = size - 1; i > index; i--) {
                curr = curr.getPrevious();
            }
            return curr.getData();
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
     * Clears the list.
     */
    public void clear() {
        size = 0;
        head = null;
        tail = null;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot search for and remove null data in data structure");
        }
        DoublyLinkedListNode<T> curr = tail;
        for (int i = size - 1; i >= 0; i--) {
            if (curr.getData().equals(data)) {
                T stored = curr.getData();
                if (i == size - 1) {
                    removeFromBack();
                } else if (i == 0) {
                    removeFromFront();
                } else {
                    curr.getPrevious().setNext(curr.getNext());
                    curr.getNext().setPrevious(curr.getPrevious());
                    size--;
                }
                return stored;
            } else {
                curr = curr.getPrevious();
            }
        }
        throw new java.util.NoSuchElementException("The data that was entered was not found in the Linked List");
    }

    /**
     * Returns an array representation of the linked list.
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] array = new Object[size];
        DoublyLinkedListNode<T> curr = head;
        for (int i = 0; i < size; i++) {
            array[i] = curr.getData();
            curr = curr.getNext();
        }
        return array;
    }
}
