// identity of a vertex - a number from 0 to N-1 for N vertices
// the best representation for Graph is Array of Arraylist 
// which is top level array for N vertices
// and each item of array is adjacency list(ArrayList) for that vertex.

// given an edge a<->b i.e. undirectional, one must do
// adj[a].push(b) and adj[b].push(a)
// but for a directional edge a->b,
// only adj[a].push(b) is sufficient

// status array for each vertex cn be enum like
// 	static final int VISITED = 2, EXPLORED = 1, UNVISITED = 0;
// or in simple cases a boolean arr e.g. boolean[] visited;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;

class GraphTraversal {
    public static void main(String[] args) {
        System.out.println("Hello graph traversal!");
        Scanner sc = new Scanner(System.in);
        Graph g = new Graph();
        g.setup(sc, false);
        System.out.println(g);
        g.dfsStack();

        int[] vis = new int[g.N];
        for(int i = 0; i< g.N; i++) {
            if (vis[i] == 0) {
                g.dfs(i, vis);
            }
        }

        g.dfsCLRS();

        g.bfs();
    }
}

class Graph {
    ArrayList<Integer>[] adj;
    int N;
    boolean directed;
    static int WHITE = 0, GRAY = 1, BLACK = 2;
    int dfsTime;
        

    void addEdge(int u, int v, boolean directed) {
        adj[u].add(v);
        if (!directed) {
            adj[v].add(u);
        }
    }

    void dfs(int j, int[] visited) {
        visited[j] = 1;
        System.out.println("normal-dfs visit: " + j);
        for(Integer k: this.adj[j]) {
            if(visited[k] == 0){
                dfs(k, visited);
            }
        }
    }

    void dfsCLRS(){
        int[] visitColor = new int[this.N];
        int[] disc = new int[this.N];// discovery time
        int[] finish = new int[this.N]; // finish time
        int[] pred = new int[this.N]; // recorded predecessor
        int[] components = new int[this.N]; // component num that each vertex belongs to.
        int compCount = 0;
        for(int i=0;i<this.N;i++) {
            visitColor[i] = WHITE;
        }
        for(int i = 0; i< this.N; i++) {
            if (visitColor[i] == WHITE) {
                ++compCount;
                dfsCLRSVisit(i, disc, finish, pred, visitColor, components, compCount);
            }
        }
        System.out.println(Arrays.toString(disc));
        System.out.println(Arrays.toString(finish));
        System.out.println(Arrays.toString(pred));
        System.out.println(Arrays.toString(components));
    }

    void dfsCLRSVisit(int u, int[] disc, int[] finish, int[] pred, int[] visitColor, int[] components, int compCount) {
        visitColor[u] = GRAY;
        disc[u] = ++dfsTime;
        for(Integer v: this.adj[u]) {
            if(visitColor[v] == WHITE) {
                pred[v] = u;
                dfsCLRSVisit(v, disc, finish, pred, visitColor, components, compCount);
            }
        }
        visitColor[u] = BLACK;
        components[u] = compCount;
        System.out.println("clrs visit: "+ u);
        finish[u] = ++dfsTime;
    }

    /**
     * Visits all the dfs for the graph/forest
     */
    void dfsStack() {
        int[] visited = new int[this.N];
        System.out.println(Arrays.toString(visited));
        for(int i = 0; i< this.N; i++) {
            if(visited[i] != 0) {
                continue;
            }
            Stack<Integer> stack = new Stack<Integer>();
            stack.push(i);
            while(stack.empty() == false) {
                Integer j = stack.peek();
                stack.pop();
                if(visited[j] == 0){
                    System.out.println("visiting: " + j);
                    visited[j] = 1;
                }
                for(Integer k: this.adj[j]){
                    if(visited[k] == 0) {
                        stack.push(k);
                    }
                }
            }
            stack.clear();
        }
    }

    void bfs(){
        int[] visited = new int[N];
        int[] pred = new int[N];
        for(int i=0;i<N; i++) {
            if(visited[i] == WHITE) {
                this.bfsVisit(i, visited, pred);
            }
        }
    }

    void bfsVisit(int u, int[] visited, int[] pred) {
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(u);
        while(q.isEmpty() == false) {
            Integer k = q.poll();
            for(Integer v: this.adj[k]) {
                if(visited[v] == WHITE) {
                    visited[v] = GRAY;
                    q.add(v);
                    pred[v] = k;
                }
            }
            // node considered visted only after children made gray
            visited[k] = BLACK;
            System.out.println("Visited-bfs-: "+k);
        }
    }

    void setup(Scanner sc, boolean directed) {
        // Scanner sc = new Scanner(graphStr);
        this.directed = directed;
        this.N = sc.nextInt();
        this.adj = new ArrayList[N];
        for(int i = 0 ; i < N; i++) {
            this.adj[i] = new ArrayList<Integer>();
        }
        while(sc.hasNext()) {
            int S = sc.nextInt();
            int E = sc.nextInt();
            addEdge(S, E, directed);
        }
    }

    public String toString() {
        String edgeList = "";
        for(int i=0; i<N; i++) {
            if(this.adj[i].size() > 0) {
                edgeList = edgeList + i + " -> " + this.adj[i] + "\n";
            }
        }
        return this.N + " nodes, " + "edge list: \n" + edgeList; 
    }
}