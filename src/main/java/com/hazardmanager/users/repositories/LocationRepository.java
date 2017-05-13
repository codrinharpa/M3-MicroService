package com.hazardmanager.users.repositories;

import com.hazardmanager.users.models.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocationRepository extends MongoRepository<Location, String> {
}
