package compute;

import junit.framework.Assert;
import org.junit.Test;

import java.util.List;

public class TestUtils {
    @Test
    public void allCombinations(){
        int size = 3;
        List<String> res = Utils.allCombinations(size);
        Assert.assertEquals((int)Math.pow(2, size), res.size());
    }
}
