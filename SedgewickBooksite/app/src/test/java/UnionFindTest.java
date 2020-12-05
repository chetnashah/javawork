
import com.course.basics.UnionFind;
import org.junit.Assert;
import org.junit.Test;

public class UnionFindTest {
    @Test
    public void firstTestCase(){
        UnionFind uf = new UnionFind(10);
        // odd numbers in one group
        uf.union(1,3);
        uf.union(3,5);
        uf.union(1,7);
        uf.union(1,9);
        // even numbers in another
        uf.union(0,2);
        uf.union(2,4);
        uf.union(4,8);
        uf.union(0,6);

        Assert.assertEquals(uf.find(0), uf.find(8));
        Assert.assertEquals(uf.find(1), uf.find(7));
    }
}
