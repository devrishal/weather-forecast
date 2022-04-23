package com.wfs.utility.algorithms;

import com.wfs.utility.exception.WeatherForecastServiceException;
import com.wfs.utility.vo.WeatherVO;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUCache {
    private Node head;
    private Node tail;
    private Map<String, Node> container;
    private Integer capacity;
    private Integer currentSize;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    class Node {
        String key;
        WeatherVO value;
        Node prev;
        Node next;

        public Node(Node prev, Node next, String key, WeatherVO value) {
            this.prev = prev;
            this.next = next;
            this.key = key;
            this.value = value;
        }
    }

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.currentSize = 0;
        head = new Node(null, null, null, null);
        tail = head;
        container = new HashMap<>();
    }

    public WeatherVO get(String key) {
        this.lock.readLock().lock();
        try {
            Node tempNode = container.get(key);
            if (tempNode == null) {
                throw new WeatherForecastServiceException("city not found", 404);
            }
            //first element requested keep everything as is.
            else if (tempNode.key.equals(tail.key)) {
                return tail.value;
            }

            //Next and previous node stored
            Node nextNode = tempNode.next;
            Node prevNode = tempNode.prev;

            /**
             * if last element is accessed, updating the list
             */
            if (tempNode.key.equals(head.key)) {
                nextNode.prev = null;
                head = nextNode;
            }

            /**
             * if element is not the first element and last element, then it present somewhere in mid.
             * Update the position in linked list.
             */
            else {
                prevNode.next = nextNode;
                nextNode.prev = prevNode;
            }

            /**
             * updating the recently accessed item to be available at first place.
             */
            tempNode.prev = tail;
            tail.next = tempNode;
            tail = tempNode;
            tail.next = null;
            return tempNode.value;
        } finally {
            this.lock.readLock().unlock();
        }

    }

    public void put(String key, WeatherVO value) {
        this.lock.writeLock().lock();
        try {
            if (container.containsKey(key)) {
                return;
            }

            // Put the new node at the right-most end of the linked-list
            Node myNode = new Node(tail, null, key, value);
            tail.next = myNode;
            container.put(key, myNode);
            tail = myNode;

            // Delete the left-most entry and update the LRU pointer
            if (currentSize.equals(capacity)) {
                container.remove(head.key);
                head = head.next;
                tail.prev = null;
            }

            // Update container size, for the first added entry update the LRU pointer
            else if (currentSize < capacity) {
                if (currentSize == 0) {
                    head = myNode;
                }
                currentSize++;
            }
        } finally {
            this.lock.writeLock().unlock();
        }
    }

}
