
import com.course.basics.MathUtils.NumTheoryUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NumUtilsTest {

    @Test
    public void testPrime(){
        Assert.assertEquals(true, NumTheoryUtils.isPrime(47));
        Assert.assertEquals(false, NumTheoryUtils.isPrime(77));
    }

    @Test
    public void testDivisors() {
        Set<Integer> h = new HashSet<>();
        h.addAll(Arrays.asList(1,2,4,5, 10, 20, 25, 50, 100));
        Assert.assertEquals(h, NumTheoryUtils.getDivisors(100));

        h = new HashSet<>();
        h.addAll(Arrays.asList(1,2,4));
        Assert.assertEquals(h, NumTheoryUtils.getDivisors(4));
    }

    @Test
    public void testgcd() {
        Assert.assertEquals(1, NumTheoryUtils.gcd(11, 31));
        Assert.assertEquals(1, NumTheoryUtils.gcd(1, 12));
        Assert.assertEquals(5, NumTheoryUtils.gcd(5,5));
    }
}
