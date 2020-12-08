package com.course.basics;

public class UnionFind {
    int[] parent;
    int[] size;

    public UnionFind(int n){
        parent = new int[n];
        for(int i=0; i< n; i++) {
            make_set(i);
        }
    }

    private void make_set(int i) {
        parent[i] = i;
    }

    public void union(int i, int j) {
        int iRoot = find(i);
        int jRoot = find(j);// TODO optimize using Quick Union by rank
        // System.out.println("i = " + i + " j = "+ j + " iRoot = " + iRoot + " jRoot = " + jRoot);
        if(iRoot != jRoot) {
            parent[jRoot] = iRoot;
        }
    }

    /**
     * Union by size is an optimization which considers size[] of set i and set j
     * and attaches the smaller set as a child of larger set
     * @param i
     * @param j
     */
    public void union_size(int i, int j) {

    }

    public int find(int k) {
        if(parent[k] != k) {
            // compressing path by pointing all parents to root
            parent[k] = find(parent[k]);
        }
        return parent[k];
    } 
    
}
