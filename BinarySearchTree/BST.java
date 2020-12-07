import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

/**
 * My BST Implementation
 *
 * @author Sohum Gala
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> {

    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     */
    public BST() {
        
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot add null collection or collection containing "
                    + "null data to data structure");
        }
        size = 0;
        for (T datum : data) {
            add(datum);
        }
    }

    /**
     * Adds the data to the tree.
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
     *
     * @param data the data to be added
     * @param curr the current node being traversed
     * @return either new node to be added or current node to be pointer reinforced
     */
    private BSTNode<T> addHelper(BSTNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new BSTNode<T>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addHelper(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addHelper(curr.getRight(), data));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot remove null data from data structure");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
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
    private BSTNode<T> removeHelper(BSTNode<T> curr, T data, BSTNode<T> dummy) {
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
                BSTNode<T> tempDummy = new BSTNode<>(null);
                curr.setRight(successor(curr.getRight(), tempDummy));
                curr.setData(tempDummy.getData());
            } else if (curr.getLeft() != null || curr.getRight() != null) {
                return (curr.getLeft() != null) ? curr.getLeft() : curr.getRight();
            } else {
                return null;
            }
        }
        return curr;
    }

    /**
     * Helper method for removeHelper to deal with successor logic
     * @param curr current node being traversed
     * @param temp node to store data from leaf node to be deleted
     * @return either node to traverse to find successor or current node to be pointer reinforced
     */
    private BSTNode<T> successor(BSTNode<T> curr, BSTNode<T> temp) {
        if (curr.getLeft() == null) {
            temp.setData(curr.getData());
            return curr.getRight();
        }
        curr.setLeft(successor(curr.getLeft(), temp));
        return curr;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     * @param data the data to search for
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
    private T getHelper(BSTNode<T> curr, T data) {
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
     * @param data the data to search for
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
    private boolean containsHelper(BSTNode<T> curr, T data) {
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
     * Generate a pre-order traversal of the tree.
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> traversal = new LinkedList<T>();
        preorderHelper(root, traversal);
        return traversal;
    }
    /**
     * private recursive helper method for preorder traversal
     * @param node node to be passed in for recursion
     * @param list list data will be added to as traversal progresses
     */
    private void preorderHelper(BSTNode<T> node, List<T> list) {
        if (node != null) {
            list.add(node.getData());
            preorderHelper(node.getLeft(), list);
            preorderHelper(node.getRight(), list);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> traversal = new LinkedList<T>();
        inorderHelper(root, traversal);
        return traversal;
    }

    /**
     * private recursive helper method for inorder traversal
     * @param node node to be passed in for recursion
     * @param list list data will be added to as traversal progresses
     */
    private void inorderHelper(BSTNode<T> node, List<T> list) {
        if (node != null) {
            inorderHelper(node.getLeft(), list);
            list.add(node.getData());
            inorderHelper(node.getRight(), list);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> traversal = new LinkedList<T>();
        postorderHelper(root, traversal);
        return traversal;
    }

    /**
     * private recursive helper method for postorder traversal
     * @param node node to be passed in for recursion
     * @param list list data will be added to as traversal progresses
     */
    private void postorderHelper(BSTNode<T> node, List<T> list) {
        if (node != null) {
            postorderHelper(node.getLeft(), list);
            postorderHelper(node.getRight(), list);
            list.add(node.getData());
        }
    }
    /**
     * Generate a level-order traversal of the tree.
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> traversal = new LinkedList<>();
        Queue<BSTNode<T>> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            BSTNode<T> curr = q.poll();
            traversal.add(curr.getData());
            if (curr.getLeft() != null) {
                q.add(curr.getLeft());
            }
            if (curr.getRight() != null) {
                q.add(curr.getRight());
            }
        }
        return traversal;
    }

    /**
     * Returns the height of the root of the tree.
     * @return the height of the root of the tree, -1 if the tree is empty
     */

    public int height() {
        return heightHelper(root);
    }

    /**
     * private helper method for above height method
     * @param curr current node being traversed
     * @return height of the root of the tree
     */
    private int heightHelper(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            return Math.max(heightHelper(curr.getLeft()), heightHelper(curr.getRight())) + 1;
        }
    }

    /**
     * Clears the tree.).
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k > n (or k < 0 <- not originally there), the number of data
     *                                            in the BST
     */

    public List<T> kLargest(int k) {
        if (k > size || k < 0) {
            throw new java.lang.IllegalArgumentException("input should be between 0 and " + size);
        }
        List<T> largest = new LinkedList<T>();
        kLargestHelper(root, largest, k);
        return largest;
    }

    /**
     * private helper method for above kLargest method
     * @param curr current node being traversed
     * @param list list in which values are being stored
     * @param k number of the largest elements in the list that are desired
     */
    private void kLargestHelper(BSTNode<T> curr, List<T> list, int k) {
        if (curr != null && list.size() < k) {
            kLargestHelper(curr.getRight(), list, k);
            if (list.size() < k) {
                list.add(0, curr.getData());
            }
            kLargestHelper(curr.getLeft(), list, k);
        }
    }
}
