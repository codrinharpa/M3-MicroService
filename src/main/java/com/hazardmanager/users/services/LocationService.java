package com.hazardmanager.users.services;

import com.hazardmanager.users.models.Location;

import java.util.List;

public interface LocationService extends CrudService<Location>{
    List<Location> getAllUserLocations(String userId);
    Location getLocationByUserIdAndAlias(String userId, String alias);

}
