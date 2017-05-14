package com.hazardmanager.users.services;

import com.hazardmanager.users.DTO.AreaDto;
import com.hazardmanager.users.DTO.LocationDto;
import com.hazardmanager.users.DTO.UserDto;
import com.hazardmanager.users.models.Location;
import com.hazardmanager.users.repositories.LocationRepository;
import com.hazardmanager.users.utilis.DistanceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Location save(Location entity) {
        return this.repository.save(entity);
    }

    @Override
    public List<Location> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Location getById(String id) {
        return this.repository.findOne(id);
    }

    @Override
    public void delete(String id) {
        this.repository.delete(id);
    }

    @Override
    public List<Location> getAllUserLocations(String userId) {

        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, Location.class);
    }

    @Override
    public Location getLocationByUserIdAndAlias(String userId, String alias) {

        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        query.addCriteria(Criteria.where("alias").is(alias));
        List<Location> locations = mongoTemplate.find(query, Location.class);
        if (locations.isEmpty()) {
            return null;
        } else {
            return locations.get(0);
        }
    }

    @Override
    public List<Location> getLocationsWithinEventArea (AreaDto area) throws NullPointerException{
        if(area == null)
            throw new NullPointerException();
        List<Location> locations = this.repository.findAll();
        return locations.stream().filter(location -> isInArea(toDto(location),area)).collect(Collectors.toList());
    }
    public static boolean isInArea(LocationDto location, AreaDto area){
        double distance = DistanceCalculator.distance(location.latitude,location.longitude,area.latitude,area.longitude);
        System.out.println(location.latitude+","+location.longitude);
        System.out.println(area.latitude+","+area.longitude);
        System.out.println(distance);
        return distance < area.radius;
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
}
