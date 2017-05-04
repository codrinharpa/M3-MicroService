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

//    public Location getByAlias(String userId, String alias) {
//        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient("127.0.0.1"), "hazardmanager");
//        Query query = new Query();
//        query.addCriteria(Criteria.where("userId").is(userId));
//        query.addCriteria(Criteria.where("alias").is(alias));
//        List<Location> locations = mongoTemplate.find(query, Location.class);
//
//        return locations.get(0);
//    }

    @Override
    public void delete(String id) {
        this.repository.delete(id);
    }

//    public void deleteByAlias(String userId, String alias) {
//        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient("127.0.0.1"), "hazardmanager");
//        Query query = new Query();
//        query.addCriteria(Criteria.where("userId").is(userId));
//        query.addCriteria(Criteria.where("alias").is(alias));
//        List<Location> locations = mongoTemplate.find(query, Location.class);
//        this.repository.delete(locations.get(0).getUserId());
//      }
}
