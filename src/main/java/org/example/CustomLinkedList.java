package org.example;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CustomLinkedList<E> {
    private Node<E> first;
    private Node<E> last;
    private int size = 0;

    ///////////////////////////////////////////////Основные методы/////////////////////////////////////////////////////////
    public int size() {
        return size;
    }

    public void add(int index, E element) {
        checkPositionIndex(index);
        if(index == 0)
            linkFirst(element);
        else if (index == size)
            linkLast(element);
        else
            linkBefore(element, getNodeByIndex(index));
    }

    public void add(E element) {
        linkLast(element);
    }

    //т.к. мой класс не реализует интерфейс Collection, его нельзя привести к массиву через toArray(), для него нужен отдельный метод
    public boolean addAll(int index, CustomLinkedList<E> list){
        checkPositionIndex(index);
        if (list.size == 0)
            return false;

        if (index == size) { //добавляем в конец
            for (int i = 0; i < list.size; i++)
                add(list.get(i));
        }
        else {                //добавляем в середину
            for(int i = 0; i < list.size; i++){
                add(index + i, list.get(i));
            }
        }
        return true;
    }

    public boolean addAll(CustomLinkedList<E> list) {
        return addAll(size, list);
    }

    //метод addAll для коллекций, реализующих интерфейс Collection
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }
    public boolean addAll(int index, Collection<? extends E> c) {
        checkPositionIndex(index);

        Object[] a = c.toArray();
        if (a.length == 0)
            return false;

        Node<E> prevNode;
        Node<E> nextNode;
        if (index == size) {
            nextNode = null;
            prevNode = last;
        } else {
            nextNode = getNodeByIndex(index);
            prevNode = nextNode.prev;
        }

        for (Object o : a) {
            E e = (E) o;
            Node<E> newNode = new Node<>(prevNode, e, null);
            if (prevNode == null)
                first = newNode;
            else
                prevNode.next = newNode;
            prevNode = newNode;
        }

        if (nextNode == null) {
            last = prevNode;
        } else {
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }

        size += a.length;
        return true;
    }
    public E get(int index) {
        checkElementIndex(index);
        return getNodeByIndex(index).item;
    }

    public E remove(int index) {
        checkElementIndex(index);
        return unlink(getNodeByIndex(index));
    }

    public boolean remove(E element) {
        for (Node<E> x = first; x != null; x = x.next) {
            if (Objects.equals(element, x.item)) {
                unlink(x);
                return true;
            }
        }
        return false;
    }
    ////////////////////////////////////////////////Методы, отличающие LinkedList//////////////////////////////////////
    public void addFirst(E element) {
        linkFirst(element);
    }

    public void addLast(E element) {
        linkLast(element);
    }

    public E getFirst() {
        checkForEmptiness();
        return first.item;
    }

    public E getLast() {
        checkForEmptiness();
        return last.item;
    }

    public E removeFirst() {
        checkForEmptiness();
        return unlink(first);
    }

    public E removeLast() {
        checkForEmptiness();
        return unlink(last);
    }

    /////////////////////////////////////////////Внутренние вспомогателшьные метды///////////////////////////////////
    private void checkForEmptiness(){
        if (size == 0)
            throw new NoSuchElementException();
    }
    private void checkElementIndex(int index){
        if (!(index >= 0 && index < size))
            throw new IndexOutOfBoundsException("Index: "+index+", LastAvailableIndex: "+ (size-1));
    }
    private void checkPositionIndex(int index) {
        if (!(index >= 0 && index <= this.size))
            throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
    }
    Node<E> getNodeByIndex(int index) {
        Node<E> x;
        if (index < (size / 2)) {
            x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
        } else {
            x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
        }
        return x;
    }
    private void linkFirst(E e) {
        Node<E> f = first;
        Node<E> newNode = new Node<>(null, e, f);
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
        size++;
    }
    void linkLast(E e) {
        Node<E> l = last;
        Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
    }
    void linkBefore(E e, Node<E> nextNode) {
        Node<E> prevNode = nextNode.prev;
        Node<E> newNode = new Node<>(prevNode, e, nextNode);
        nextNode.prev = newNode;
        prevNode.next = newNode;
        size++;
    }

    E unlink(Node<E> x) {
        E element = x.item;
        Node<E> nextNode = x.next;
        Node<E> prevNode = x.prev;
        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
            x.prev = null;
        }

        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
            x.next = null;
        }
        x.item = null;
        size--;
        return element;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\nСписок. элементы:");
        for(int i = 0; i < size(); i ++){
            sb.append("\n" + get(i));
        }
        return sb.toString();
    }
    /////////////////////////////////////////////////Вложенная нода///////////////////////////////////////////////////////
    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(CustomLinkedList.Node<E> prev, E element, CustomLinkedList.Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
