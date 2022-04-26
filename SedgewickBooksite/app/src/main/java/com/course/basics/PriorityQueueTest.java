import java.util.Iterator;
import java.util.PriorityQueue;

public class PriorityQueueTest {
    public static void main(String[] args) {
        System.out.println("hello!");
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        pq.offer(9);
        pq.offer(2);
        pq.offer(11);
        pq.offer(5);
    
        // iteration order is random
        for(Integer j: pq) {
            System.out.println(j);
        }
        // same as above more explicit
        Iterator<Integer> it = pq.iterator();
        while(it.hasNext()) {
            System.out.println(it.next());
        }

        System.out.println("----");
        Integer k = 0;
        // emptying the queue using poll
        while((k = pq.poll()) != null){
            System.out.println(k);
        }
    }
}