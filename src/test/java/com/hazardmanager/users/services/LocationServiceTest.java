package com.hazardmanager.users.services;

import javax.tools.JavaFileManager.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;


public class LocationServiceTest {

      private Location location;

    @Before
    public void buildUp() {
        location = new Location("590b1c84ce65c6189c9ca4ff") {
            public String getName() {
                throw new UnsupportedOperationException("Not supported yet."); 
            }

            public boolean isOutputLocation() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    @After
    public void tearDown() {
        location = null;
    }
    
    @Test
    public void getAllUserLocationsTest {
        location.setAlias(alias);
        Assert.assertEquals(location.getAllUserLocations("userid") , null);
   }
    @Test
     public void getLocationByUserIdAndAliasTest()
     {
        Assert.assertEquals(location.getLocationByUserIdAndAlias("user","alias") , null);
     }
     

}
