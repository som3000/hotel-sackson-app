package com.authservice.controller;


import com.authservice.exceptions.UsernameAlreadyExists;
import com.authservice.exceptions.InvalidCredentials;
import com.authservice.services.UserAuthenticationManager;
import com.authservice.services.UserService;
import com.authservice.utils.JwtUtils;
import com.authservice.views.UserAuthRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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

    try {
      userService.register(userAuthRequest.username(), userAuthRequest.password());
      return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("registered successfully");
    } catch (UsernameAlreadyExists e) {
      return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body("username already exists");
    }
  }

  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> login(@RequestBody UserAuthRequest userAuthRequest) {
    Map<String, String> view = new HashMap<>();

    try {
      userAuthenticationManager
              .authenticate(
                      userAuthRequest.username(),
                      userAuthRequest.password()
              );

      String token = jwtUtils.generateToken(userAuthRequest.username());
      view.put("token", token);
      return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(view);
    } catch (InvalidCredentials e) {
      view.put("msg", "token not found for you");
      return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(view);
    }

  }
}
