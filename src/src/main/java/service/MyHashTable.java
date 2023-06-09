package service;

public class MyHashTable<K, V> {
    float loadFactor = 0.75f;
    private class HashNode<K,V>{
        private K key;
        private V value;
        private HashNode<K,V> next;
        public  HashNode(K key, V value){
            this.key = key;
            this.value = value;
        }
        @Override
        public String toString(){
            return "{" + key + " " + value + "}";
        }
    }
    private HashNode<K, V>[] chainArray;
    private int M = 11;
    private int size = 0;
    int capacity = 50;
    public MyHashTable(){
        this.chainArray = new HashNode[M];
    }
    public MyHashTable(int M){
        this.M = M;
        this.chainArray = new HashNode[M];
    }
    private int hash(K key){
        int hashCode = key.hashCode();
        int index = Math.abs(hashCode) % M;
        return index;
    }
    public void put(K key, V value){
        int index = hash(key) & (capacity-1);
        HashNode<K,V> node = chainArray[index];
        while (node != null) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
            node = node.next;
        }
        node = new HashNode<>(key, value);
        node.next = chainArray[index];
        chainArray[index] = node;
        size++;
        if (size > capacity * loadFactor) {
            resize(capacity * 2);
        }
    }
    public void getSize() {
        System.out.println("size = " + size);
    }

    public void resize(int newCapacity) {
        int temp = capacity;
        HashNode[] newChain = new HashNode[newCapacity];
        capacity = newCapacity;
        for (int i = 0; i < temp; i++) {
            HashNode node = chainArray[i];
            while (node != null) {
                int newIndex = hash((K) node.key);
                node.next = newChain[newIndex];
                newChain[newIndex] = node;
                node = node.next;
            }
        }
        chainArray = newChain;
    }

    public V get(K key){
        int index = hash(key);
        HashNode<K,V> node = chainArray[index];
        while (node != null) {
            if (node.key.equals(key)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }
    public V remove(K key){
        int index = hash(key);
        HashNode<K,V> node = chainArray[index];
        HashNode<K,V> prev = null;
        while (node != null) {
            if (node.key.equals(key)) {
                if (prev == null) {
                    chainArray[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                return node.value;
            }
            prev = node;
            node = node.next;
        }
        return null;
    }
    public boolean contains(V value){
        for (int i = 0; i < M; i++) {
            HashNode<K,V> node = chainArray[i];
            while (node != null) {
                if (node.value.equals(value)) {
                    return true;
                }
                node = node.next;
            }
        }
        return false;
    }
    public K getKey(V value){
        for (int i = 0; i < M; i++) {
            HashNode<K,V> node = chainArray[i];
            while (node != null) {
                if (node.value.equals(value)) {
                    return node.key;
                }
                node = node.next;
            }
        }
        return null;
    }
    public void printBucketSizes(){
        int[] sizes = new int[M];
        for (int i = 0; i < M; i++) {
            HashNode<K,V> node = chainArray[i];
            while (node != null) {
                sizes[i]++;
                node = node.next;
            }
        }
        for (int i = 0; i < M; i++) {
            System.out.println("Bucket " + i + ": " + sizes[i]);
        }
    }

}