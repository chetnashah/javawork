import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Queue is an interface for a FIFO structure
 * LinkedList is one of the implementations.
 * Methods that do not throw: offer, poll, peek 
 * (which return special values like null, false in case of failures)
 * Methods that do throw: add, remove, element
 * 
 * 
 * Note: one should not allow insertion of null elements in the queue
 * because it is a special return value in case of poll when no element is present in the queue
 * */
class QueueTest {
    public static void main(String[] args) {
        System.out.println("Running QueueTest!");

        Queue<Integer> q = new LinkedList<Integer>();
        q.offer(new Integer(2));
        q.offer(new Integer(22));
        Integer[] arr = new Integer[]{2,3,45};
        ArrayList<Integer> alist = new ArrayList<>(Arrays.asList(arr));
        q.addAll(alist);
        System.out.println(q);

        Integer k = -1;
         while((k = (Integer)q.poll()) != null){
             System.out.println(k);
         }

        for(Integer j: q) {
            System.out.println(j);
        }
        System.out.println(q);

        // poll will 
        System.out.println(q.poll());
        

    }
}