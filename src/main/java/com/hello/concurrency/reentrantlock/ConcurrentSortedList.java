package com.hello.concurrency.reentrantlock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentSortedList {

    private class Node {
        int value;
        Node prev;
        Node next;
        ReentrantLock lock = new ReentrantLock();

        Node() {}

        Node(int value, Node prev, Node next){
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private final Node head;
    private final Node tail;

    public ConcurrentSortedList() {
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
    }

    public void insert(int value) {
        Node current = head;
        current.lock.lock();
        Node next = current.next;
        try {
            while(true) {
                next.lock.lock();
                try {
                    if(next == tail || next.value > value){
                        Node node = new Node(value, current, next);
                        next.prev = node;
                        current.next = node;
                        return;
                    }
                } finally {
                    current.lock.unlock();
                }
                current = next;
                next = current.next;
            }
        } finally {
            next.lock.unlock();
        }
    }

    public List<Integer> toList() {
        List<Integer> list = new ArrayList<>();
        Node current = head.next;
        while (current != tail) {
            list.add(current.value);
            current = current.next;
        }
        return list;
    }
}
