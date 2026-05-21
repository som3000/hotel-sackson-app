package com.hotel.controller;


import com.hotel.exceptions.InvalidCredentials;
import com.hotel.services.UserAuthenticationManager;
import com.hotel.services.UserService;
import com.hotel.utils.JwtUtils;
import com.hotel.views.UserAuthRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class AuthController {

  private final UserService userService;
  private final UserAuthenticationManager userAuthenticationManager;
  private final JwtUtils jwtUtils;

  public AuthController(UserService userService, UserAuthenticationManager userAuthenticationManager, JwtUtils jwtUtils) {
    this.userService = userService;
    this.userAuthenticationManager = userAuthenticationManager;
    this.jwtUtils = jwtUtils;
  }

  @PostMapping("/register")
  public ResponseEntity<String> post(@RequestBody UserAuthRequest userAuthRequest) {

    userService.register(userAuthRequest.username(), userAuthRequest.password());

    return ResponseEntity.ok("registered successfully");
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody UserAuthRequest userAuthRequest) {

    try {
      userAuthenticationManager
              .authenticate(
                      userAuthRequest.username(),
                      userAuthRequest.password()
              );

      return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(jwtUtils.generateToken(userAuthRequest.username()));
    } catch (InvalidCredentials e) {
      return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body("invalid credential");
    }

  }
}
