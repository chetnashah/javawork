
import com.com.onlinejudges.leetcode.weeklycontest208.Solution;
import org.junit.Assert;
import org.junit.Test;

public class Solution208BTest {
    @Test
    public void firstTestCase(){
        Solution s = new Solution();
        int[] arr = {8,3};
        Assert.assertEquals(s.minOperationsMaxProfit(arr,5,6),3);
    }
}
