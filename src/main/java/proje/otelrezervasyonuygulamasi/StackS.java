package proje.otelrezervasyonuygulamasi;

import java.io.Serializable;
import java.util.EmptyStackException;

public class StackS<E> implements Serializable {
    private Node<E> head;
    public int size;
    public StackS() {}
    public void push(E data) {
        Node<E> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            size += 1;
            return;
        }
        newNode.next = head;
        head = newNode;
        size += 1;
    }
    public E pop() {
        if (head == null) {
            throw new EmptyStackException();

        }
        E data = head.data;
        head = head.next;
        size -=1;
        return data;
    }
    public E peek() {
        if (head == null) {
            throw new EmptyStackException();
        }
        return head.data;
    }
    public boolean isEmpty() {
        return head == null;
    }
    public void copy(StackS<E> stackS) {
        head = stackS.head;
        Node<E> current = head;
        Node<E> copyCurr = stackS.head;
        while(copyCurr != null) {
            current.next = copyCurr.next;
            current = current.next;
            copyCurr = copyCurr.next;
        }
        this.size = stackS.size;
    }

}
