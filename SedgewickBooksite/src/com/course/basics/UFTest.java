import java.util.Scanner;
import java.util.Arrays;

class UFTest {
    public static void main(String[] args) {
        System.out.println("hello UFtest!!");
        Scanner sc = new Scanner(System.in);
        Integer N = sc.nextInt();
        int[] parent = new int[N];
        // initially each set is separate
        for(int i= 0; i< N; i++){
            parent[i] = i;
        }

        // union all edges in input
        Integer E = sc.nextInt();
        for(int i = 0; i< E; i++){
            int p = sc.nextInt();
            int q = sc.nextInt();
            union(p, q, parent);
        }

        // finally only have essential set representatives in parent array
        for(int i = 0; i < N; i++) {
            parent[i] = find(i, parent);
        }
        System.out.println(Arrays.toString(parent));
        //System.out.println(N);
    }

    private static void union(int i, int j, int[] parent) {
        int iRoot = find(i, parent);
        int jRoot = find(j, parent);// TODO optimize using Quick Union by rank
        // System.out.println("i = " + i + " j = "+ j + " iRoot = " + iRoot + " jRoot = " + jRoot);
        parent[jRoot] = iRoot;
    }

    private static int find(int k, int[] parent) {
        if(k != parent[k]) {
            // compressing path by pointing all parents to root
            parent[k] = find(parent[k], parent);
        }
        return parent[k];
    } 
    
}
