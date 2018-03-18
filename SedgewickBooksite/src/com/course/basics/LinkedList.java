package com.course.basics;

public class LinkedList<T> {
    static class Node<T> {
        Node next;
        T data;

        public Node(T data) {
            this.data = data;
        }
    }

    // head is a maintained property of LinkedList class
    Node head;

    public LinkedList() {
        this.head = null;
    }

    void addToFront(T data) {
        Node n = new Node<T>(data);
        if (head == null) {
            head = n;
            return;
        }
        n.next = head;
        head = n;
    }

    void addToTail(T data) {
        Node n = head;
        while(n.next != null){
            n = n.next;
        }
        Node nn = new Node<T>(data);
        n.next = nn;
    }

    /**
     * removes all nodes with data
     * @param data
     */
    void delete(T data){
        Node prev = head;
        Node curr = head;
        while(curr != null) {
            if (prev == curr && curr.data == data) {// Case 1. head needs to be removed
                head = curr.next;
                curr.next = null;// gc the node
                prev = head;
                curr = head;
            } else if (curr.data == data) { // Case 2. some intermediate node needs to be removed
                prev.next = curr.next;
                curr.next = null;// gc the node to be removed
                curr = prev.next;
            } else {
                // Case 3. nothing matched, move ahead
                prev = curr;
                curr = curr.next;
            }
        }
    }

    void printList(){
        Node n = head;
        while (n != null) {
            System.out.printf(n.data.toString() + "->");
            n = n.next;
        }
        System.out.println("X");
    }

    public static void main(String[] args) {
        LinkedList<Integer> ll = new LinkedList<Integer>();
        ll.addToFront(4);
        ll.addToFront(3);
        ll.addToTail(5);
        ll.addToTail(5);
        ll.addToFront(4);
        ll.printList();
        ll.delete(5);
        ll.printList();
        ll.delete(4);
        ll.printList();
        ll.delete(3);
        ll.printList();
    }
}
