/*
 * Mahdeen Ahmed Khan Sameer
 * BSTMap Class: Binary search tree implementation of the MapSet interface and allows for the insertion, deletion, and retrieval of key-value pairs, as well as provides methods for getting a set of keys, values, and entries and calculating the maximum depth of the tree.
 */

import java.util.ArrayList;
import java.util.Comparator;

public class BSTMap<K, V> implements MapSet<K, V> {
    private Node<K, V> root;
    private int size;
    private Comparator<K> comparator;

    public BSTMap() {
        this(null);
    }

    public BSTMap(Comparator<K> comparator) {
        this.comparator = comparator;
        root = null;
        size = 0;
    }

    private static class Node<K, V> extends KeyValuePair<K, V> {
        private Node<K, V> left;
        private Node<K, V> right;

        Node(K key, V value) {
            super(key, value);
            left = null;
            right = null;
        }

        public Node<K, V> getLeft() {
            return left;
        }

        public Node<K, V> getRight() {
            return right;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            return null;
        }
        Node<K, V> newNode = new Node<>(key, value);
        if (root == null) {
            root = newNode;
            size++;
            return null;
        }
        Node<K, V> parent = null;
        Node<K, V> current = root;
        int cmp;
        while (current != null) {
            cmp = compare(key, current.getKey());
            parent = current;
            if (cmp == 0) {
                V oldValue = current.getValue();
                current.setValue(value);
                return oldValue;
            } else if (cmp < 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        cmp = compare(key, parent.getKey());
        if (cmp < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        size++;
        return null;
    }

    @SuppressWarnings("unchecked")
    private int compare(K a, K b) {
        if (comparator != null) {
            return comparator.compare(a, b);
        }
        return ((Comparable<K>) a).compareTo(b);
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        Node<K, V> current = root;
        int cmp;
        while (current != null) {
            cmp = compare(key, current.getKey());
            if (cmp == 0) {
                return current.getValue();
            } else if (cmp < 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        if (containsKey(key)) {
            V oldValue = get(key);
            root = remove(root, key);
            size--;
            return oldValue;
        }
        return null;
    }

    private Node<K, V> min(Node<K, V> node) {
        if (node.left == null) {
            return node;
        } else {
            return min(node.left);
        }
    }
    

    private Node<K, V> remove(Node<K, V> node, K key) {
        if (node == null) {
            return null;
        }
    
        int compare = compare(key, node.getKey());
        if (compare < 0) {
            node.left = remove(node.getLeft(), key);
        } else if (compare > 0) {
            node.right = remove(node.getRight(), key);
        } else {
            // Case with only a right child or no child
            if (node.getLeft() == null) {
                return node.getRight();
            }
            // Case with only a left child
            else if (node.getRight() == null) {
                return node.getLeft();
            }
            // Case with two children
            else {
                Node<K, V> minNode = min(node.getRight());
                node = new Node<>(minNode.getKey(), minNode.getValue());
                node.right = remove(node.getRight(), minNode.getKey());
            }
        }
        return node;
    }
    

@Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    inOrderTraversal(root, node -> sb.append(node.toString()).append(", "));
    return sb.length() > 0 ? sb.substring(0, sb.length() - 2) : "";
}

@Override
public ArrayList<K> keySet() {
    ArrayList<K> keys = new ArrayList<>();
    inOrderTraversal(root, node -> keys.add(node.getKey()));
    return keys;
}

@Override
public ArrayList<V> values() {
    ArrayList<V> values = new ArrayList<>();
    inOrderTraversal(root, node -> values.add(node.getValue()));
    return values;
}

@Override
public ArrayList<MapSet.KeyValuePair<K, V>> entrySet() {
    ArrayList<MapSet.KeyValuePair<K, V>> entries = new ArrayList<>();
    inOrderTraversal(root, node -> entries.add(new MapSet.KeyValuePair<>(node.getKey(), node.getValue())));
    return entries;
}


private void inOrderTraversal(Node<K, V> node, java.util.function.Consumer<Node<K, V>> consumer) {
    if (node == null) {
        return;
    }
    inOrderTraversal(node.getLeft(), consumer);
    consumer.accept(node);
    inOrderTraversal(node.getRight(), consumer);
}

@Override
public int maxDepth() {
    return calculateMaxDepth(root);
}

private int calculateMaxDepth(Node<K, V> node) {
    if (node == null) {
        return 0;
    }
    int leftDepth = calculateMaxDepth(node.getLeft());
    int rightDepth = calculateMaxDepth(node.getRight());
    return Math.max(leftDepth, rightDepth) + 1;
}
}