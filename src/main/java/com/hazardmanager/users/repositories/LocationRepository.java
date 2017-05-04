package com.hazardmanager.users.repositories;

import com.hazardmanager.users.models.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Bogdan on 04-May-17.
 */
public interface LocationRepository extends MongoRepository<Location, String> {
}
