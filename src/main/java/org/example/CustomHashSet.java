package org.example;

import java.util.Objects;

public class CustomHashSet<K> {
    private static final int DEFAULT_CAPACITY = 16;
    static final int MAXIMUM_CAPACITY = 1073741824;
    private static final float LOAD_FACTOR = 0.75f;
    private static final float DOWNSIZE_FACTOR = 0.5f;
    private static final float DOWNSIZE_CAST_FACTOR = 0.75f;

    private Node<K>[] table;
    private int size;
    private int threshold;

    ///////////////////////////////////////////////Вложенная нода/////////////////////////////////////////////////////////
    private static class Node<K> {
        final K key;
        final int hash;
        Node<K> next;

        Node(K key, int hash, Node<K> next) {
            this.key = key;
            this.hash = hash;
            this.next = next;
        }
    }

    public CustomHashSet() {
        table = new Node[DEFAULT_CAPACITY];
        threshold = (int)(DEFAULT_CAPACITY * LOAD_FACTOR);
    }
    public CustomHashSet(int initialCapacity, float loadFactor) {
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor > 1)
            loadFactor = 1;
        table = new Node[initialCapacity];
        threshold = (int)(loadFactor);
    }

    ///////////////////////////////////////////////Основные методы/////////////////////////////////////////////////////////
    public boolean add(K key) {
        int hash = hash(key);
        int index = bucketIndex(hash, table.length);

        Node<K> node = table[index];    //берем первый элемент "списка" в бакете (Т.к. из хеша получили индекс бакета, проверяем конкретноего одного)
        while (node != null) {
            if (Objects.equals(node.key, key)) {
                return false;
            }
            node = node.next;
        }

        table[index] = new Node<>(key, hash, table[index]);     //Добавляем нашу ноду в начало "списка" в текущем бакете
        size++;

        if (size > threshold) {
            resize(table.length * 2);
        }

        return true;
    }

    public boolean remove(K key) {
        int hash = hash(key);
        int index = bucketIndex(hash, table.length);

        Node<K> node = table[index];
        Node<K> prev = null;

        while (node != null) {
            if (Objects.equals(node.key, key)) {
                if (prev == null) {     //совпадение в первой ноде
                    table[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                //если после очередного удаления таблица оказалась заполнена меньше чем на половину, уменьшаем ее размер на 25%
                if (table.length > DEFAULT_CAPACITY && size < table.length * DOWNSIZE_FACTOR)
                    resize((int)(table.length * DOWNSIZE_CAST_FACTOR));
                return true;
            }
            prev = node;    //для того, чтобы после удаления связать соседние элементы, запоминаем текущую ноду до перехода к следующей
            node = node.next;
        }
        return false;
    }

    public int size(){
        return size;
    }

    ///////////////////////////////////////////////Внутренние вспомогателшьные метды/////////////////////////////////////////////////////////
    private int hash(K key) {
        int h = key.hashCode();
        return (key == null) ? 0 : h ^ (h >>> 16);  //сдвигаем старшие биты к концу для более точного распределения
    }

    private int bucketIndex(int hash, int length) {
        return hash & (length - 1);                 //на основе хэша вычисляем индекс нужного бакета
    }

    private void resize(int newCapacity) {
        if (newCapacity < DEFAULT_CAPACITY)
            newCapacity = DEFAULT_CAPACITY;
        else if (newCapacity > MAXIMUM_CAPACITY)
            newCapacity = MAXIMUM_CAPACITY;

        Node<K>[] newTable = new Node[newCapacity];
        for (Node<K> node : table) {
            while (node != null) {
                Node<K> next = node.next;
                int newIndex = bucketIndex(node.hash, newCapacity);
                node.next = newTable[newIndex];
                newTable[newIndex] = node;
                node = next;
            }
        }

        table = newTable;
        threshold = (int)(newCapacity * LOAD_FACTOR);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nМножество: [");

        boolean first = true;
        for (Node<K> node : table) {
            while (node != null) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(node.key);
                first = false;
                node = node.next;
            }
        }

        sb.append("]");
        sb.append("\nКоличество элементов в множестве: " + size + "\nРазмер внутренней таблицы: " + table.length);

        return sb.toString();
    }
}