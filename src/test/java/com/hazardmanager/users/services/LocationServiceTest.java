package com.hazardmanager.users.services;

import com.hazardmanager.users.DTO.AreaDto;
import com.hazardmanager.users.DTO.LocationDto;
import com.hazardmanager.users.models.Location;
import com.hazardmanager.users.utilis.DistanceCalculator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.springframework.data.geo.Distance;

import java.util.List;

public class LocationServiceTest {

    LocationService service;
    AreaDto area;
    @Before
    public void buildUp() {
        service = new LocationServiceImpl();
        area = new AreaDto();
    }
    @Test
    public void distanceTest1(){
        double latitude1 =  47.1585;
        double longitude1 = 27.6014;
        double latitude2 =  46.6407;
        double longitude2 = 27.7276;
        double result = DistanceCalculator.distance(latitude1,longitude1,latitude2,longitude2);
        Assert.assertEquals(58433.8 ,result,90);
    }
    @Test
    public void distanceTest2(){
        double latitude1 =  47.15407;
        double longitude1 = 27.57088;
        double latitude2 =  47.1585;
        double longitude2 = 27.6014;
        double result = DistanceCalculator.distance(latitude1,longitude1,latitude2,longitude2);
        Assert.assertEquals(2362 ,result,90);
    }
    @Test
    public void isInAreaTest1(){
        area.latitude = 47.1585;
        area.longitude = 27.6014;
        area.radius = 5000;
        LocationDto inCity = new LocationDto();
        inCity.latitude = 47.15407;
        inCity.longitude = 	27.57088;
        boolean isInArea = LocationServiceImpl.isInArea(inCity,area);
        Assert.assertTrue(isInArea == true);
    }
    @Test(expected = NullPointerException.class)
    public void when_getLocationsWithinEventName_called_should_throw_NullPointerException() {
        List<Location> locations = service.getLocationsWithinEventArea(null);
    }

}

