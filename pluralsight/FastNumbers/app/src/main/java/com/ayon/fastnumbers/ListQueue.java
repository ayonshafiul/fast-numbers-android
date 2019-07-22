package com.ayon.fastnumbers;

import android.util.Log;

public class ListQueue {
    int size = 0;
    Node front = null;
    Node rear = null;
    static final String TAG = "myList";

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isAlmostEmpty() {
        return size == 2;
    }
    public void enqueue(int element) {
        Node n = new Node(element, null);
        if (isEmpty()) {
            front = n;
            rear = n;
        } else {
            rear.next = n;
            rear = n;
        }
        size++;
    }

    public int dequeue() throws RuntimeException {
        if (isEmpty()) {
            throw new RuntimeException("Queue is Empty!");
        } else {
            Node t = front;
            int element = front.element;
            front = front.next;
            size--;
            return element;
        }

    }
}