import com.course.basics.FenwickTreeOneBasedIndexing;
import org.junit.Assert;
import org.junit.Test;


public class FenwickTreeOneBasedIndexingTest {


    @Test
    public void testUpdates(){
        FenwickTreeOneBasedIndexing f = new FenwickTreeOneBasedIndexing(new int[]{1,2,3,4,5});
        Assert.assertEquals(f.sum(4), 15);
        System.out.println("single update");
        f.pointUpdate(0, 100);
        Assert.assertEquals(f.sum(4), 115);
    }

    @Test
    public void testSum() {
        FenwickTreeOneBasedIndexing f = new FenwickTreeOneBasedIndexing(new int[]{1,2,3,4,5});
        Assert.assertEquals(f.sum(0), 1);
        Assert.assertEquals(f.sum(1), 3);
        Assert.assertEquals(f.sum(2), 6);
        Assert.assertEquals(f.sum(3), 10);
        Assert.assertEquals(f.sum(4), 15);

    }
}
