package com.authservice.services;

import com.authservice.exceptions.UsernameAlreadyExists;
import com.authservice.entity.User;
import com.authservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void register(String username, String password) {
    User user = userRepository.getUserByUsername(username);
    if (user != null) {
      throw new UsernameAlreadyExists("username already exists");
    }
    User newUser = new User(username, password);
    userRepository.save(newUser);
  }

}
