package edu.ping.damian.enzinlum.develop;

import org.junit.Assert;
import org.junit.Test;

public class AddressTest {
    Address address = null;

    @Test
    public void setupTest(){
        this.address = new Address();
        this.address.generateKeyPair();
        //Assert.assertTrue(this.address.equals(this.address.toString()));
        Assert.assertEquals(this.address.toString(), this.address.toString());
    }
}
