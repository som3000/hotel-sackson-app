package com.authservice.repository;

import com.authservice.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
  User findByUsernameAndPassword(String username, String password);

  User getUserByUsername(String username);
}
