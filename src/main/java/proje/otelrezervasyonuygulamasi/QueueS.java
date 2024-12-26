package proje.otelrezervasyonuygulamasi;

import java.io.Serializable;

public class QueueS<E> implements Serializable {
    private Node<E> head;
    public int size = 0;
    public void enqueue(E e) {
        Node<E> newNode = new Node<>(e);
        if (head == null) {
            head = newNode;
            size += 1;
            return;
        }
        Node<E> current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
        size += 1;
    }
    public E dequeue() {
        if (head == null) {
            return null;
        }
        E e = head.data;
        head = head.next;
        size -= 1;
        return e;
    }
    public E peek() {
        if (head == null) {
            return null;
        }
        return head.data;
    }
    public boolean isEmpty() {
        return head == null;
    }
    public void copy(QueueS<E> queueS) {
        head = queueS.head;
        Node<E> current = head;
        Node<E> copyCurr = queueS.head;
        while (copyCurr != null) {
            current.next = copyCurr.next;
            current = current.next;
            copyCurr = copyCurr.next;
        }
        this.size = queueS.size;
    }
}
