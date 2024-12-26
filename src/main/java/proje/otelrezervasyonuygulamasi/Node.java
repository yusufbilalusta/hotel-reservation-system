package proje.otelrezervasyonuygulamasi;

import java.io.Serializable;

public class Node<E> implements Serializable {
    public E data;
    public Node<E> next;

    public Node(E data) {
        this.data = data;
    }
}