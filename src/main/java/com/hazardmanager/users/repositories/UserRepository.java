package com.hazardmanager.users.repositories;

import com.hazardmanager.users.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
