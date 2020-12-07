import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * My AVL Implementation
 * @author Sohum Gala
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {

    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL
     */
    public AVL() {
        
    }

    /**
     * Constructs a new AVL.
     * This constructor should initialize the AVL with the data in the
     * Collection. 
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot add null collection to data structure");
        }
        size = 0;
        for (T datum : data) {
            add(datum);
        }
    }

    /**
     * Adds the element to the tree.
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot add null data to data structure");
        }
        root = addHelper(root, data);
    }
    /**
     * Helper method for add(T data)
     * @param data the data to be added
     * @param curr the current node being traversed
     * @return either new node to be added or current node to be pointer reinforced
     */
    private AVLNode<T> addHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new AVLNode<T>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addHelper(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addHelper(curr.getRight(), data));
        }
        update(curr);
        return comprehensiveRebalance(curr);
    }


    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot remove null data from data structure");
        }
        AVLNode<T> dummy = new AVLNode<>(null);
        root = removeHelper(root, data, dummy);
        return dummy.getData();
    }
    /**
     * Helper method for remove(T data)
     * @param curr the current node being traversed
     * @param data the data being removed
     * @param dummy node that contains copy of data from node to be removed
     * @return either the outcome of the removal in the case of no children or current node to be pointer reinforced
     */
    private AVLNode<T> removeHelper(AVLNode<T> curr, T data, AVLNode<T> dummy) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("Could not find specified data in data structure");
        }
        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(removeHelper(curr.getLeft(), data, dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(removeHelper(curr.getRight(), data, dummy));
        } else if (data.equals(curr.getData())) {
            dummy.setData(curr.getData());
            size--;
            if (curr.getLeft() != null && curr.getRight() != null) {
                AVLNode<T> tempDummy = new AVLNode<>(null);
                curr.setLeft(predecessor(curr.getLeft(), tempDummy));
                curr.setData(tempDummy.getData());
            } else if (curr.getLeft() != null || curr.getRight() != null) {
                return (curr.getLeft() != null) ? curr.getLeft() : curr.getRight();
            } else {
                return null;
            }
        }
        update(curr);
        return comprehensiveRebalance(curr);
    }

    /**
     * Helper method for removeHelper to deal with predecessor logic
     * @param curr current node being traversed
     * @param temp node to store data from leaf node to be deleted
     * @return either node to traverse to find successor or current node to be pointer reinforced
     */
    private AVLNode<T> predecessor(AVLNode<T> curr, AVLNode<T> temp) {
        if (curr.getRight() == null) {
            temp.setData(curr.getData());
            return curr.getLeft();
        }
        curr.setRight(predecessor(curr.getRight(), temp));
        update(curr);
        return comprehensiveRebalance(curr);
    }


    /**
     * Returns the element from the tree matching the given parameter.
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("cannot retrieve null data from data structure");
        }
        return getHelper(root, data);
    }
    /**
     * private helper method for above get method
     * @param curr current node being traversed
     * @param data data to be retrieved from BST
     * @return data stored in BST if it matches data passed in
     */
    private T getHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("could not find specified data in data structure");
        } else if (curr.getData().equals(data)) {
            return curr.getData();
        } else if (data.compareTo(curr.getData()) < 0) {
            return getHelper(curr.getLeft(), data);
        } else {
            return getHelper(curr.getRight(), data);
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * @param data the data to search for in the tree
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("cannot search for null data in data structure");
        }
        return containsHelper(root, data);
    }
    /**
     * private helper method for above contains method
     * @param curr current node being traversed
     * @param data data to be searched for in the bST
     * @return true or false depending on whether or not the data was found in the BST
     */
    private boolean containsHelper(AVLNode<T> curr, T data) {
        if (curr == null) {
            return false;
        } else if (curr.getData().equals(data)) {
            return true;
        } else if (data.compareTo(curr.getData()) < 0) {
            return containsHelper(curr.getLeft(), data);
        } else {
            return containsHelper(curr.getRight(), data);
        }
    }

    /**
     * Returns the height of the root of the tree. 
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        int hLeft = (root.getLeft() == null) ? -1 : root.getLeft().getHeight();
        int hRight = (root.getRight() == null) ? -1 : root.getRight().getHeight();
        return Math.max(hLeft, hRight) + 1;
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Find a path of letters in the tree that spell out a particular word,
     * if the path exists.
     * @param word array of T, where each element represents a letter in the
     * word (in order).
     * @return list containing the path of letters in the tree that spell out
     * the word, if such a path exists. Order matters! The ordering of the
     * letters in the returned list should match that of the word array.
     * @throws java.lang.IllegalArgumentException if the word array is null
     * @throws java.util.NoSuchElementException if the path is not in the tree
     */

    public List<T> wordSearch(T[] word) {
        if (word == null) {
            throw new java.lang.IllegalArgumentException("cannot perform a word search on a null array");
        }
        List<T> list = new LinkedList<>();
        if (word.length == 0) {
            return list;
        } else if (word.length == 1) {
            list.add(get(word[0]));
            return list;
        }
        AVLNode<T> dca = getDCA(root, word[0], word[word.length - 1]);
        list.add(dca.getData());
        traverseToFront(dca, word[0], list, dca);
        traverseToBack(dca, word[word.length - 1], list, dca);
        if (word.length != list.size()) {
            throw new java.util.NoSuchElementException("path of word does not exist in the tree");
        }
        for (int i = 0; i < word.length; i++) {
            if (!word[i].equals(list.get(i))) {
                throw new java.util.NoSuchElementException("path of word does not exist in the tree");
            }
        }
        return list;
    }

    /**
     * private helper method to return the DCA of an AVL tree
     * finds the deepest common ancestor of the values of the beginning and end of a word in the tree
     * @param curr current node being traversed
     * @param begin first letter of word
     * @param end last letter of word
     * @return the Deepest Common Ancestor of
     */
    private AVLNode<T> getDCA(AVLNode<T> curr, T begin, T end) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("deepest ancestor could not be found in the AVL tree");
        } else if (begin.compareTo(curr.getData()) > 0 && end.compareTo(curr.getData()) > 0) {
            return getDCA(curr.getRight(), begin, end);
        } else if (begin.compareTo(curr.getData()) < 0 && end.compareTo(curr.getData()) < 0) {
            return getDCA(curr.getLeft(), begin, end);
        }
        return curr;
    }

    /**
     * private helper method to traverse to the front of the word from the DCA
     * @param curr current node being traversed
     * @param front first letter of word
     * @param list list of letters being added to
     * @param dca deepest common ancestor node so that the same letter isn't added to list multiple times
     */
    private void traverseToFront(AVLNode<T> curr, T front, List<T> list, AVLNode<T> dca) {
        if (curr != null && !curr.getData().equals(dca.getData())) {
            list.add(0, curr.getData());
        }
        if (curr == null) {
            return;
        }
        if (front.compareTo(curr.getData()) > 0) {
            traverseToFront(curr.getRight(), front, list, dca);
        } else if (front.compareTo(curr.getData()) < 0) {
            traverseToFront(curr.getLeft(), front, list, dca);
        }
    }

    /**
     * private helper method to traverse to the back of the word from the DCA
     * @param curr current node being traversed
     * @param back last letter of the word
     * @param list list of letters being added to
     * @param dca deepest common ancestor node so that the same letter isn't added to the list multiple times
     */
    private void traverseToBack(AVLNode<T> curr, T back, List<T> list, AVLNode<T> dca) {
        if (curr != null && !curr.getData().equals(dca.getData())) {
            list.add(list.size(), curr.getData());
        }
        if (curr == null) {
            return;
        }
        if (back.compareTo(curr.getData()) > 0) {
            traverseToBack(curr.getRight(), back, list, dca);
        } else if (back.compareTo(curr.getData()) < 0) {
            traverseToBack(curr.getLeft(), back, list, dca);
        }
    }


    /**
     * private helper method that updates the height and balance factor of a node
     * @param curr current node to be updated
     */
    private void update(AVLNode<T> curr) {
        int hLeft = (curr.getLeft() == null) ? -1 : curr.getLeft().getHeight();
        int hRight = (curr.getRight() == null) ? -1 : curr.getRight().getHeight();
        curr.setHeight(Math.max(hLeft, hRight) + 1);
        curr.setBalanceFactor(hLeft - hRight);
    }

    /**
     * all in one rebalance method that calls leftRotation and rightRotation as necessary to perform rotations
     * @param curr current node being balanced
     * @return final AVLNode that has been balanced to be pointer reinforced
     */
    private AVLNode<T> comprehensiveRebalance(AVLNode<T> curr) {
        if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft().getBalanceFactor() < 0) {
                curr.setLeft(leftRotation(curr.getLeft()));
            }
            curr = rightRotation(curr);
        } else if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() > 0) {
                curr.setRight(rightRotation(curr.getRight()));
            }
            curr = leftRotation(curr);
        }
        return curr;
    }

    /**
     * helper method to perform a left rotation about an AVLNode
     * @param curr current node being rotated
     * @return final AVLNode that has been rotated to be pointer reinforced
     */
    private AVLNode<T> leftRotation(AVLNode<T> curr) {
        AVLNode<T> right = curr.getRight();
        curr.setRight(right.getLeft());
        right.setLeft(curr);
        update(curr);
        update(right);
        return right;
    }

    /**
     * helper method to perform a right rotation about an AVLNode
     * @param curr current node being rotated
     * @return final AVLNode that has been rotated to be pointer reinforced
     */
    private AVLNode<T> rightRotation(AVLNode<T> curr) {
        AVLNode<T> left = curr.getLeft();
        curr.setLeft(left.getRight());
        left.setRight(curr);
        update(curr);
        update(left);
        return left;
    }
}