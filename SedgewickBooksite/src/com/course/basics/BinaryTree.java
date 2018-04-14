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
    }



}
