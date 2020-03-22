package com.course.basics;

public class LinkedList<T extends Comparable<? super T>> {
    static class Node<U extends Comparable<? super U>> {
        Node<U> next;
        U data;

        public Node(U data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    '}';
        }
    }

    // head is a maintained property of LinkedList class
    Node<T> head;

    public LinkedList() {
        this.head = null;
    }

    void addToFront(T data) {
        Node<T> n = new Node<T>(data);
        if (head == null) {
            head = n;
            return;
        }
        n.next = head;
        head = n;
    }

    void addToTail(T data) {
        if (head == null) {
            head = new Node<T>(data);
            return;
        }
        Node<T> n = head;
        while(n.next != null){
            n = n.next;
        }
        Node<T> nn = new Node<T>(data);
        n.next = nn;
    }

    void addAllToTail(T[] data) {
        for (T item: data) {
            addToTail(item);
        }
    }

    /**
     * removes all nodes with data
     * @param data
     */
    void delete(T data){
        Node<T> prev = head;
        Node<T> curr = head;
        while(curr != null) {
            if (prev == curr && curr.data == data) {// Case 1. head needs to be removed
                head = curr.next;
                curr.next = null;// gc the removed node
                prev = head;
                curr = head;
            } else if (curr.data == data) { // Case 2. some intermediate node needs to be removed
                prev.next = curr.next;
                curr.next = null;// gc the removed node
                curr = prev.next;
            } else {
                // Case 3. nothing matched, move ahead
                prev = curr;
                curr = curr.next;
            }
        }
    }

    void removeDuplicatesWithoutBuffer(){
        Node<T> curr = this.head;
        Node<T> p = curr;
        while(curr != null) {
            while(p.next != null){
                if(p.next.data == curr.data) {
                    Node<T> tmp = p.next.next;
                    p.next.next = null;
                    p.next = tmp;
                } else {
                    p = p.next;
                }
            }
            curr = curr.next;
            p = curr;
        }
    }

    void printList(){
        Node<T> n = head;
        while (n != null) {
            System.out.printf(n.data.toString() + "->");
            n = n.next;
        }
        System.out.println("X");
    }

    /**
     * Just prints/traverse reverse of linked list, does not modify
     * @param k
     */
    void printReverse(Node<T> k) {
        if(k != null) {
            printReverse(k.next);
            System.out.printf("=>" + k.data);
        }
    }

    /**
     * Reverses links in list, by mutating
     */
    void reverseList() {

    }

    int countNodes() {
        Node<T> p = head;
        int len = 0;
        while(p != null) {
            len++;
            p = p.next;
        }
        return len;
    }

    void checkPNullLoop(){
        // loop to check what gets printed if we condition p!= null
        Node<T> p = head;
        while(p.next != null) {
            System.out.println("Saw2 : " + p);
            p = p.next;
        }
        System.out.println("After termination2: " + p);
    }

    boolean isSorted() {
        Node<T> p = head;
        boolean isSorted = true;
        while(p.next != null) {
            isSorted = isSorted && (p.data.compareTo(p.next.data) <= 0);
            p = p.next;
        }
        return isSorted;
    }

    void sortLinkedList() {
        if (head == null || head.next == null) {// single always sorted
            return;
        }
        Node<T> pSorted = head;
        Node<T> pToBeSorted = head.next;
        // loop invariant pSorted always points to node upto which data is sorted
        while (pToBeSorted != null) {
            if(pToBeSorted.data.compareTo(head.data) < 0) { // needs to go in front of list
                pSorted.next = pToBeSorted.next;
                pToBeSorted.next = head;
                head = pToBeSorted;
            } else {// needs to go somewhere in the middle of the list
                if(pToBeSorted.data.compareTo(pSorted.data) >= 0) {
                    // move sorted pointer ahead
                    pSorted = pSorted.next;
                } else {
                    // find position where the node should be moved to
                    Node<T> p = head;
                    while(p != pSorted && p.next.data.compareTo(pToBeSorted.data) <= 0) {
                        p = p.next;
                    }
                    if (p == pSorted) {
                        pSorted = pSorted.next;
                    } else {
                        pSorted.next = pToBeSorted.next;
                        pToBeSorted.next = p.next;
                        p.next = pToBeSorted;
                    }
                }
            }
            // move tobesorted pointer to next of sorted pointer
            pToBeSorted = pSorted.next;
        }
    }

    public void mergeSortedLists(LinkedList<T> l2) {
        if(!this.isSorted() || !l2.isSorted()) {
            throw new IllegalArgumentException("One of the lists is not sorted");
        }
        Node<T> p = head;// first list running pointer
        Node<T> p2 = l2.head;// second list running pointer
        Node<T> finalHead = null;
        Node<T> finalp = null;// final list running pointer
        // simpler cases.
        if (p == null && p2 == null) {
            return;
        }
        if (p == null) {
            head = p2;
            return;
        }
        if (p2 == null) {
            head = p;
            return;
        }
        // more than 1 node in each.
        while(p != null && p2 != null) {
            if (p.data.compareTo(p2.data) <= 0) {
                if (finalHead == null) {
                    finalp = p;
                    finalHead = p;
                    p = p.next;
                } else {
                    finalp.next = p;
                    finalp = finalp.next;
                    p = p.next;
                }
            } else {
                if (finalHead == null) {
                    finalp = p2;
                    finalHead = p2;
                    p2 = p2.next;
                } else {
                    finalp.next = p2;
                    finalp = finalp.next;
                    p2 = p2.next;
                }
            }
        }
        // one of the lists emptied, append the rest remaining
        if (p == null) {
            finalp.next = p2;
        } else {
            finalp.next = p;
        }
        head = finalHead;
    }

    public static void main(String[] args) {
        LinkedList<Integer> ll = new LinkedList<Integer>();
        ll.addToFront(4);
        ll.addToFront(3);
        ll.addToTail(5);
        ll.addToTail(5);
        ll.addToFront(4);
        ll.printList();
        ll.printReverse(ll.head);
        ll.delete(5);
        ll.printList();
        ll.delete(4);
        ll.printList();
        ll.delete(3);
        ll.printList();

        // sort linked list
        // cases: dupes, null, single node
        LinkedList<Integer> l2 = new LinkedList<>();
        l2.addToTail(3);
        l2.addToTail(8);
        l2.addToTail(1);
        l2.addToTail(11);
        l2.addToTail(2);
        l2.printList();
        l2.sortLinkedList();
        l2.printList();
        System.out.println("l2 sorted = "+l2.isSorted());

        l2.checkPNullLoop();

        LinkedList<Integer> l3 = new LinkedList<>();
        Integer[] intlist = {914,1, 1, 2, 4,4, 238, 296, 39, 627, 668, 800, 265, 249, 364, 843, 931, 723, 78,
                967, 609, 433, 200, 508, 726, 744, 966, 2, 447, 475, 432, 59, 556, 343, 676, 651,
                250, 93, 87, 595, 539, 284, 483, 242, 36, 369, 531, 192, 622, 229, 490, 122, 322,
                675, 767};
        l3.addAllToTail(intlist);
        l3.sortLinkedList();
        System.out.println("l3 sorted = "+l3.isSorted());

        LinkedList<Integer> alreadySortedList = new LinkedList<>();
        alreadySortedList.addToTail(1);
        alreadySortedList.addToTail(2);
        alreadySortedList.addToTail(3);
        alreadySortedList.addToTail(5);
        alreadySortedList.sortLinkedList();
        alreadySortedList.printList();
        System.out.println("already count = " + alreadySortedList.countNodes());


        LinkedList<Integer> listWithDuplicates = new LinkedList<>();
        listWithDuplicates.addToTail(1);
        listWithDuplicates.addToTail(2);
        listWithDuplicates.addToTail(3);
        listWithDuplicates.addToTail(5);
        listWithDuplicates.addToTail(2);
        listWithDuplicates.addToTail(3);
        listWithDuplicates.addToTail(2);
        listWithDuplicates.addToTail(4);
        listWithDuplicates.addToTail(8);
        listWithDuplicates.addToTail(3);
        listWithDuplicates.addToTail(2);
        listWithDuplicates.addToTail(2);
        listWithDuplicates.printList();
        System.out.println("listWithDuplicates count = " + listWithDuplicates.countNodes());
        listWithDuplicates.removeDuplicatesWithoutBuffer();
        listWithDuplicates.printList();

        Integer[] randlist = {914,1, 1, 2, 4,4, 238, 296, 39, 627, 668, 800, 265, 249, 364, 843, 931, 723, 78,
                967, 609, 433, 200, 508, 726, 744, 966, 2, 447, 475, 432, 59, 556, 343, 676, 651,
                250, 93, 87, 595, 539, 284, 483, 242, 36, 369, 531, 192, 622, 229, 490, 122, 322,
                675, 767};
        LinkedList<Integer> rlist = new LinkedList<>();
        rlist.addAllToTail(randlist);
        rlist.sortLinkedList();

        rlist.printList();
        System.out.println("rlist count = " + rlist.countNodes());
        alreadySortedList.mergeSortedLists(rlist);
        alreadySortedList.printList();
        System.out.println("merged count = " + alreadySortedList.countNodes());
    }
}
