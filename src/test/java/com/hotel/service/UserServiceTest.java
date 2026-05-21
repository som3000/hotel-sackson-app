package com.hotel.service;

import com.hotel.models.User;
import com.hotel.repositories.UserRepository;
import com.hotel.services.UUIDGenerator;
import com.hotel.services.UserService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class UserServiceTest {

  @Test
  void itShouldRegisterUser() {
    UserRepository repo = mock(UserRepository.class);
    UUIDGenerator uuidGenerator = mock(UUIDGenerator.class);
    User user = new User("1", "username", "password");

    when(uuidGenerator.generate()).thenReturn("1");

    UserService userService = new UserService(repo, uuidGenerator);

    userService.register("username", "password");

    verify(repo).save(user);

  }
}