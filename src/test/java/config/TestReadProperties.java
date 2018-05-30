package config;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestReadProperties{
    @Rulegit
    @Test
    public void fileExist(){
        OurProperties ourProperties = OurProperties.getInstance();
        Assert.assertNotNull(ourProperties);
    }
}
