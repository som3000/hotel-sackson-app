package com.hotel.services;

import com.hotel.models.User;
import com.hotel.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final UUIDGenerator uuidGenerator;

  public UserService(UserRepository userRepository, UUIDGenerator uuidGenerator) {
    this.userRepository = userRepository;
    this.uuidGenerator = uuidGenerator;
  }

  public void register(String username, String password) {
    String id = uuidGenerator.generate();
    User user = new User(id, username, password);
    userRepository.save(user);
    System.out.println(userRepository);
  }

}
