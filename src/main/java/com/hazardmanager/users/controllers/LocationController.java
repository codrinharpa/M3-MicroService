package com.hazardmanager.users.controllers;

import com.hazardmanager.users.DTO.CreatingLocationDto;
import com.hazardmanager.users.DTO.LocationDto;
import com.hazardmanager.users.models.Location;
import com.hazardmanager.users.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class LocationController {

    @Autowired
    private LocationService service;

    @RequestMapping(value = {"/{userId}/locations"}, method = RequestMethod.GET)
    public ResponseEntity<List<LocationDto>> getAllLocationsById(@PathVariable("userId") String userId) {

        List<Location> locationModels = this.service.getAllUserLocations(userId);

        if (locationModels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<LocationDto> result = new ArrayList<>();
        for (Location location : locationModels) {
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

    @RequestMapping(value = {"/{userId}/locations/{alias}"}, method = RequestMethod.PUT)
    public ResponseEntity<LocationDto> modifyLocation(@RequestBody CreatingLocationDto locationDto, @PathVariable("userId") String userId, @PathVariable("alias") String alias) {

        Location location = this.service.getLocationByUserIdAndAlias(userId,alias);

        if (location == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        modifyLocationAccordingToDTO(location, locationDto);
        this.service.save(location);
        return new ResponseEntity<>(toDto(location), HttpStatus.OK);
    }

    @RequestMapping(value = {"/{userId}/locations/{alias}"}, method = RequestMethod.DELETE)
    public ResponseEntity deleteLocation(@PathVariable("userId") String userId, @PathVariable("alias") String alias){
        Location location = this.service.getLocationByUserIdAndAlias(userId,alias);
        if (location == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        this.service.delete(location.getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = {"/{userId}/locations"}, method = RequestMethod.DELETE)
    public ResponseEntity deleteLocations(@PathVariable("userId") String userId){

        List<Location> locations = this.service.getAllUserLocations(userId);

        if(locations.isEmpty()){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        for (Location location : locations) {
            this.service.delete(location.getId());
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    private LocationDto toDto(Location savedLocation) {
        LocationDto locationDto = new LocationDto();
        locationDto.id = savedLocation.getId();
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

    private void modifyLocationAccordingToDTO(Location location, CreatingLocationDto locationDto) {

        if (locationDto.alias != null) {
            location.setAlias(locationDto.alias);
        }
        if (locationDto.latitude >= -90 && locationDto.latitude <= 90) {
            location.setLatitude(locationDto.latitude);
        }
        if (locationDto.longitude >= -180 && locationDto.longitude <= 180) {
            location.setLongitude(locationDto.longitude);
        }
    }
}
