package com.hotel.services;


import com.hotel.exceptions.InvalidCredentials;
import com.hotel.models.User;
import com.hotel.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationManager {
  public final UserRepository userRepository;

  public UserAuthenticationManager(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void authenticate(String username, String password) {
    User user = userRepository.findByUsernameAndPassword(username, password);
    System.out.println(user);
    if (user == null) {
      throw new InvalidCredentials("invalid username and password");
    }
  }
}
