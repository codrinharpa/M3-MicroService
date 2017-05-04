package com.hazardmanager.users.services;

import com.hazardmanager.users.models.Location;
import com.hazardmanager.users.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService{
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

    @Override
    public void delete(String id) {
        this.repository.delete(id);
    }
}
