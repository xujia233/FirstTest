package csdn;

import lombok.Data;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 手写一个简单的LinkedList，实现部分方法
 * Created by xujia on 2019/6/9
 */
@Data
public class MyLinkedList<T> implements List<T> {

    /**
     * 链表长度
     */
    private int size;
    /**
     * 头结点
     */
    private Node<T> head;
    /**
     * 尾节点
     */
    private Node<T> tail;

    class Node<T> {
        /**
         * 节点值
         */
        private T value;
        /**
         * 上一节点的引用
         */
        private Node<T> pre;
        /**
         * 下一节点的引用
         */
        private Node<T> next;

        public Node() {}

        public Node(T value) {
            this.value = value;
        }

        public Node(T value, Node<T> pre, Node<T> next) {
            this.value = value;
            this.pre = pre;
            this.next = next;
        }
    }

    public MyLinkedList() {}

    public MyLinkedList(int size) {
        this.size = size;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0)
            return true;
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    /**
     * 默认插入在链表尾部
     * @param t
     * @return
     */
    @Override
    public boolean add(T t) {

        if (null == head) {
            head = new Node<>(t, null, null);
            tail = head;
        } else {
            Node<T> temp = tail;
            Node<T> newNode = new Node<>(t, temp, null);

            tail = newNode;
            // 修改原先的尾节点的引用
            temp.next = tail;
        }


        size ++;
        return true;
    }

    /**
     * 遍历
     * @param index
     * @return
     */
    private Node<T> node(int index) {
        Node<T> node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    @Override
    public T get(int index) {
        if (isEmpty())
            return null;
        return node(index).value;
    }

    @Override
    public T remove(int index) {
        if (isEmpty())
            return null;
        Node<T> node = node(index);
        T value = node.value;
        Node<T> preNode = node.pre;
        Node<T> nextNode = node.next;
        // 首尾节点需特殊判断
        if (preNode == null)
            head = nextNode;
        else {
            preNode.next = nextNode;
            node.pre = null;
        }


        if (nextNode == null)
            tail = preNode;
        else {
            nextNode.pre = preNode;
            node.next = null;
        }


        node.value = null;
        size --;
        return value;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }



    @Override
    public T set(int index, T element) {
        return null;
    }

    @Override
    public void add(int index, T element) {

    }



    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }
}
