import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * My HashMap Implementation
 *
 * @author Sohum Gala
 * @version 1.0
 */
public class LinearProbingHashMap<K, V> {

    /**
     * The initial capacity of the LinearProbingHashMap when created with the
     * default constructor.
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * The max load factor of the LinearProbingHashMap
     */
    public static final double MAX_LOAD_FACTOR = 0.67;
    private LinearProbingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new LinearProbingHashMap.
     */
    public LinearProbingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new LinearProbingHashMap..
     * @param initialCapacity the initial capacity of the backing array
     */
    public LinearProbingHashMap(int initialCapacity) {
        table = (LinearProbingMapEntry<K, V>[]) new LinearProbingMapEntry[initialCapacity];
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new java.lang.IllegalArgumentException("cannot add <K, V> containing null data to data structure");
        }
        if ((double) (size + 1) / table.length > MAX_LOAD_FACTOR) {
            resizeBackingTable(table.length * 2 + 1);
        }
        int saved = -1;
        int thingsCounted = 0;
        for (int probe = 0; probe < table.length; probe++) {
            int index = (Math.abs(key.hashCode() % table.length) + probe) % table.length;
            if (table[index] != null && !table[index].isRemoved()) {
                thingsCounted++;
            }
            if (table[index] != null && !table[index].isRemoved() && table[index].getKey().equals(key)) {
                V stored = table[Math.abs((key.hashCode() + probe) % table.length)].getValue();
                table[index].setValue(value);
                return stored;
            }
            if (table[index] != null && table[index].isRemoved() && table[index].getKey().equals(key)) {
                if (saved == -1) {
                    table[index].setValue(value);
                    table[index].setRemoved(false);
                } else {
                    table[saved] = new LinearProbingMapEntry<>(key, value);
                }
                size++;
                return null;
            } else if (table[index] != null && table[index].isRemoved() && saved == -1)  {
                saved = index;
            } else if (table[index] == null) {
                if (saved == -1) {
                    table[index] = new LinearProbingMapEntry<>(key, value);
                } else {
                    table[saved] = new LinearProbingMapEntry<>(key, value);
                }
                size++;
                return null;
            }
            if (thingsCounted == size) {
                if (saved == -1) {
                    table[(index + 1) % table.length] = new LinearProbingMapEntry<>(key, value);
                } else {
                    table[saved] = new LinearProbingMapEntry<>(key, value);
                }
                size++;
                return null;
            }
        }
        return null;
    }

    /**
     * Removes the entry with a matching key from map by marking the entry as
     * removed.
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new java.lang.IllegalArgumentException("cannot remove null key from data structure");
        }
        int thingsCounted = 0;
        for (int probe = 0; probe < table.length; probe++) {
            int index = (Math.abs(key.hashCode() % table.length) + probe) % table.length;
            if (table[index] != null && !table[index].isRemoved()) {
                thingsCounted++;
            }
            if (table[index] == null) {
                throw new java.util.NoSuchElementException("element was not found in the data structure");
            } else if (!table[index].isRemoved() && table[index].getKey().equals(key)) {
                table[index].setRemoved(true);
                size--;
                return table[index].getValue();
            }
            if (thingsCounted == size) {
                break;
            }
        }
        throw new java.util.NoSuchElementException("element was not found in the data structure");
    }

    /**
     * Gets the value associated with the given key.
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new java.lang.IllegalArgumentException("cannot search for null data in data structure");
        }
        int thingsCounted = 0;
        for (int probe = 0; probe < table.length; probe++) {
            int index = (Math.abs(key.hashCode() % table.length) + probe) % table.length;
            if (table[index] != null && !table[index].isRemoved()) {
                thingsCounted++;
            }
            if (table[index] == null) {
                throw new java.util.NoSuchElementException("element was not found in the data structure");
            } else if (!table[index].isRemoved() && table[index].getKey().equals(key)) {
                return table[index].getValue();
            }
            if (thingsCounted == size) {
                throw new java.util.NoSuchElementException("element was not found in the data structure");
            }
        }
        throw new java.util.NoSuchElementException("element was not found in the data structure");
    }

    /**
     * Returns whether or not the key is in the map.
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new java.lang.IllegalArgumentException("cannot search for null data in data structure");
        }
        int thingsCounted = 0;
        for (int probe = 0; probe < table.length; probe++) {
            int index = (Math.abs(key.hashCode() % table.length) + probe) % table.length;
            if (table[index] != null && !table[index].isRemoved()) {
                thingsCounted++;
            }
            if (table[index] == null) {
                return false;
            } else if (!table[index].isRemoved() && table[index].getKey().equals(key)) {
                return true;
            }
            if (thingsCounted == size) {
                return false;
            }
        }
        return false;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                set.add(table[i].getKey());
            }
            if (set.size() == size) {
                break;
            }
        }
        return set;
    }

    /**
     * Returns a List view of the values contained in this map.
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> list = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                list.add(table[i].getValue());
            }
            if (list.size() == size) {
                break;
            }
        }
        return list;
    }

    /**
     * Resize the backing table to length.
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new java.lang.IllegalArgumentException("new backing table must have enough space for all data in "
                    + "the current table");
        }
        LinearProbingMapEntry<K, V>[] temp = (LinearProbingMapEntry<K, V>[]) new LinearProbingMapEntry[length];
        int newSize = 0;
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                for (int probe = 0; probe < temp.length; probe++) {
                    if (temp[(Math.abs((table[i].getKey().hashCode()) % temp.length) + probe) % temp.length] == null) {
                        temp[(Math.abs((table[i].getKey().hashCode()) % temp.length) + probe) % temp.length] = table[i];
                        newSize++;
                        break;
                    }
                }
            }
            if (newSize == size) {
                break;
            }
        }
        table = temp;
    }

    /**
     * Clears the map.
     */
    public void clear() {
        table = (LinearProbingMapEntry<K, V>[]) new LinearProbingMapEntry[INITIAL_CAPACITY];
        size = 0;
    }
}
