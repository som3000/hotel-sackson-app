package com.hotel.services;

import com.hotel.exceptions.UsernameAlreadyExits;
import com.hotel.models.User;
import com.hotel.repositories.UserRepository;
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
      throw new UsernameAlreadyExits("username already exists");
    }
    User newUser = new User(username, password);
    userRepository.save(newUser);
    System.out.println(userRepository);
  }

}
