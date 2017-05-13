package com.hazardmanager.users.services;

import com.hazardmanager.users.models.Location;
import com.hazardmanager.users.repositories.LocationRepository;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if(locations.isEmpty()){
            return null;
        }else{
            return locations.get(0);
        }

    }

}
