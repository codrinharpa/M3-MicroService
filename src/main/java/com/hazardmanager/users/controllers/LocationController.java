package com.hazardmanager.users.controllers;

import com.hazardmanager.users.DTO.CreatingLocationDto;
import com.hazardmanager.users.DTO.CreatingUserDto;
import com.hazardmanager.users.DTO.LocationDto;
import com.hazardmanager.users.models.Location;
import com.hazardmanager.users.services.LocationService;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bogdan on 04-May-17.
 */
@RestController
@RequestMapping("/v1/users")
public class LocationController {
    @Autowired
    private LocationService service;

    @RequestMapping(value = {"/{userId}/locations"}, method = RequestMethod.GET)
    public ResponseEntity<List<LocationDto>> getAllLocationsById(@PathVariable("userId") String userId) {
//        List<Location> locations = this.service.getAll().stream().filter(userId)

        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient("127.0.0.1"), "hazardmanager");
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        List<Location> locations = mongoTemplate.find(query, Location.class);

        if (locations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<LocationDto> result = new ArrayList<>();
        System.out.println(locations.get(0));
        for (Location location : locations) {
            LocationDto dto = toDto(location);
            result.add(dto);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = {"/{userId}/locations"}, method = RequestMethod.POST)
    public ResponseEntity<LocationDto> addNewLocation(@RequestBody CreatingLocationDto creatinglocationDto, @PathVariable("userId") String userId) {
        Location location = toCreatingModel(creatinglocationDto, userId);
        Location savedLocation = this.service.save(location);
        return new ResponseEntity<>(toDto(savedLocation), HttpStatus.CREATED);
    }

    private LocationDto toDto(Location savedLocation) {
        LocationDto locationDto = new LocationDto();
        locationDto.userId = savedLocation.getUserId();
        locationDto.alias = savedLocation.getAlias();
        locationDto.latitude = savedLocation.getLatitude();
        locationDto.longitude = savedLocation.getLongitude();
        return locationDto;
    }

    private Location toCreatingModel(CreatingLocationDto creatingLocationDto, String userId) {
        Location location = new Location();
        location.setUserId(userId);
        location.setAlias(creatingLocationDto.alias);
        location.setLatitude(creatingLocationDto.latitude);
        location.setLongitude(creatingLocationDto.longitude);
        return location;
    }


}
