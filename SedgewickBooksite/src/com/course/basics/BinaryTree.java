package com.course.basics;

import java.util.*;
import java.util.LinkedList;

/**
 * A regular binary tree without any order assumptions,
 * All nodes have two children, using Null when absent.
 */
public class BinaryTree {

    static class BinaryTreeNode {
        BinaryTreeNode left;
        BinaryTreeNode right;
        Integer data;

        public BinaryTreeNode(Integer data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "BinaryTreeNode{" +
                    "data=" + data +
                    '}';
        }
    }

    BinaryTreeNode root;

    /**
     * adds array elements level by level
     * @param arr
     */
    void addAll(Integer[] arr) {
        List<BinaryTreeNode> btnodelist;
        btnodelist = new ArrayList<>();
        for (Integer i: arr) {
            btnodelist.add(new BinaryTreeNode(i));
        }
        for(int i=0;i<btnodelist.size();i++) {
            int childposleft = 2*i+1;
            int childposright = 2*i+2;
            if(childposleft < btnodelist.size()) {
                btnodelist.get(i).left = btnodelist.get(childposleft);
            }
            if(childposright < btnodelist.size()) {
                btnodelist.get(i).right = btnodelist.get(childposright);
            }
        }
        root = btnodelist.get(0);
        btnodelist = null;
    }

    void printLevelWithNewline() {
        if (root == null) {
            return;
        }
        Queue<BinaryTreeNode> q = new LinkedList<>();
        int currentLevel = 1;
        int nextLevel = 0;
        q.add(root);

        while(q.size() > 0) {
            BinaryTreeNode b = q.remove();
            currentLevel--;
            System.out.printf("%d ",b.data);

            if (b.left != null) {
                nextLevel++;
                q.add(b.left);
            }
            if (b.right != null) {
                nextLevel++;
                q.add(b.right);
            }
            if(currentLevel == 0) {
                System.out.print("\n");
                currentLevel = nextLevel;
            }
        }
        System.out.println("--------");
    }

    void printZigZag() {
        // going zig zag by taking right first.
        Stack<BinaryTreeNode> s1 = new Stack<>();
        Stack<BinaryTreeNode> s2 = new Stack<>();

        s1.push(root);
        while(!s1.empty() || !s2.empty()) {
            if(!s1.empty()) {
                while(!s1.empty()) {
                    BinaryTreeNode item = s1.pop();
                    System.out.println(item.data);
                    if(item.left!=null) {
                        s2.push(item.left);
                    }
                    if(item.right!=null) {
                        s2.push(item.right);
                    }
                }
            }
            if(!s2.empty()) {
                while(!s2.empty()) {
                    BinaryTreeNode item = s2.pop();
                    System.out.println(item.data);
                    if(item.right!=null) {
                        s1.push(item.right);
                    }
                    if(item.left!=null) {
                        s1.push(item.left);
                    }
                }
            }
        }
        System.out.println("---------------");
    }

    void printPreOrder2(){
        // preorder traversal that uses a single stack
        if (root == null) {
            return;
        }
        Stack<BinaryTreeNode> s = new Stack<>();
        while(!s.empty()) {
            BinaryTreeNode curr = s.pop();
            System.out.printf("%d ", curr.data);
            // push right first, so that left comes out first
            if (curr.right != null) {
                s.push(curr.right);
            }
            if (curr.left != null) {
                s.push(curr.left);
            }
        }
        System.out.println("-------------------");
    }

    void printPreOrder() {
        // preorder traversal is same as dfs traversal
        if (root == null) {
            return;
        }
        // bi-recursion simulated with 2 stack
        Stack<BinaryTreeNode> stl = new Stack<>();
        Stack<BinaryTreeNode> str = new Stack<>();
        stl.push(root);
        while(!stl.empty() || !str.empty()) {
            BinaryTreeNode current;
            if (stl.empty()) {
                current = str.pop();
            } else {
                current = stl.pop();
            }
            System.out.printf("%d ", current.data);
            if (current.left != null) {
                stl.push(current.left);
            }
            if(current.right != null) {
                str.push(current.right);
            }
        }

        System.out.println("---------------");
    }

    void printInOrder() {
        if(root == null) {
            return;
        }
        // iterative version of inorder
        // https://en.wikipedia.org/wiki/Tree_traversal#In-order_2
        Stack<BinaryTreeNode> st = new Stack<>();
        BinaryTreeNode node = root;
        // the invariant is tricky
        while(!st.empty() || node != null) {
            if (node != null) {
                // all valid nodes are pushed onto the stack,
                // left ones will come out first
                st.push(node);
                node = node.left;
            } else {
                // node points to null
                // popped from the stack would be left most unprocessed
                // parent nodes
                node = st.pop();
                System.out.printf("%d ", node.data); // visit()
                // continue process with the right branch
                node = node.right;
            }
        }
    }

    void printPostOrder() {
        if (root == null) {
            return;
        }
        // iterative version of post order traversal.
        Stack<BinaryTreeNode> s = new Stack<>();
        BinaryTreeNode lastVisitedNode = null;

        BinaryTreeNode node = root;
        BinaryTreeNode peekNode = null;
        // tricky invariant
        while(!s.empty() || node != null) {
            if (node != null) {
                s.push(node);
                node = node.left;
            } else {
                peekNode = s.peek();
                if (peekNode.right != null && lastVisitedNode != peekNode.right) {
                    // this is true executed when all children are not visited
                    // and there is a chance to visit right child
                    node = peekNode.right;
                } else {
                    System.out.printf("%d ", peekNode.data);//visit peeknode
                    lastVisitedNode = s.pop();
                }
            }

        }
    }

    void printPreOrderRecursive(BinaryTreeNode node) {
        if(root == null || node == null) {
            return;
        }

        System.out.printf("%d ", node.data);
        if(node.left != null) {
            printPreOrderRecursive(node.left);
        }
        if (node.right != null) {
            printPreOrderRecursive(node.right);
        }
    }

    void printInOrderRecursive(BinaryTreeNode node) {
        if(root == null || node == null) {
            return;
        }

        if (node.left != null) {
            printInOrderRecursive(node.left);
        }
        System.out.printf("%d ", node.data);
        if (node.right != null) {
            printInOrderRecursive(node.right);
        }
    }

    void printPostOrderRecursive(BinaryTreeNode node) {
        if(root == null || node == null) {
            return;
        }
        if (node.left != null) {
            printPostOrderRecursive(node.left);
        }
        if (node.right != null) {
            printPostOrderRecursive(node.right);
        }
        System.out.printf("%d ", node.data);
    }

    BinaryTreeNode constructBtreeWithTraversals(int[] preordertraversal, int[] inordertraversal) {
        return root;
    }

    boolean isBST() {
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;
        return isBSTRecursive(root, min, max);
    }

    boolean isBSTRecursive(BinaryTreeNode node, int leftBound, int rightBound) {
        if (node == null) {
            return true;
        }
        return node.data < rightBound && node.data >= leftBound && isBSTRecursive(node.left, leftBound, node.data) && isBSTRecursive(node.right, node.data, rightBound);
    }

    public static void main(String[] args) {
        BinaryTree bt = new BinaryTree();
        Integer[] arr = {8, 6, 10, 5, 7, 9, 11};
        bt.addAll(arr);

        System.out.println(bt.root);
        System.out.println(bt.root.left.right);
        System.out.println(bt.root.right.left);

        bt.printLevelWithNewline();
        bt.printZigZag();

        bt.printPreOrder();

        BinaryTree bt2 = new BinaryTree();
        Integer[] arr2 = { 8, 10, 6, 11, 9, 7, 5};
        bt2.addAll(arr2);

        bt2.printPreOrder();// 8 10 11 9 6 7 5
        bt2.printPreOrderRecursive(bt2.root);// 8 10 11 9 6 7 5
        System.out.println();
        bt2.printInOrder();// shud be 11 10 9 8 7 6 5
        System.out.println();
        bt2.printInOrderRecursive(bt2.root);// 11 10 9 8 7 6 5
        System.out.println();
        bt2.printPostOrderRecursive(bt2.root);// 11 9 10 7 5 6 8
        System.out.println();
        bt2.printPostOrder();// 11 9 10 7 5 6 8

        BinaryTree bt3 = new BinaryTree();
        Integer[] arr3 = { 3, 2, 5, 1, 4};
        bt3.addAll(arr3);
        System.out.println(bt3.isBST());// false

        BinaryTree bt4 = new BinaryTree();
        Integer[] arr4 = { 4, 2, 5, 1, 3};
        bt4.addAll(arr4);
        System.out.println(bt4.isBST());// true


        int[] preorder = {1, 2, 4, 7, 3, 5, 6, 8};
        int[] postorder = {4, 7, 2, 1, 5, 3, 8, 6};

    }



}
