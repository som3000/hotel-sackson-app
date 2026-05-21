package com.hotel.controller;


import com.hotel.exceptions.InvalidCredentials;
import com.hotel.services.UserAuthenticaionManager;
import com.hotel.services.UserService;
import com.hotel.utils.JwtUtils;
import com.hotel.views.UserAuthRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class AuthController {

  private final UserService userService;
  private final UserAuthenticaionManager userAuthenticaionManager;

  public AuthController(UserService userService, UserAuthenticaionManager userAuthenticaionManager) {
    this.userService = userService;
    this.userAuthenticaionManager = userAuthenticaionManager;
  }

  @PostMapping("/register")
  public ResponseEntity<String> post(@RequestBody UserAuthRequest userAuthRequest) {

    userService.register(userAuthRequest.username(), userAuthRequest.password());

    return ResponseEntity.ok("registered successfully");
  }

  @PostMapping("/login")
  public String login(@RequestBody UserAuthRequest userAuthRequest) {

    try {
      userAuthenticaionManager
              .authenticate(
                      userAuthRequest.username(),
                      userAuthRequest.password()
              );


      return JwtUtils.generateToken(userAuthRequest.username());
    } catch (InvalidCredentials e) {
      return "invalid Request";
    }

  }
}
