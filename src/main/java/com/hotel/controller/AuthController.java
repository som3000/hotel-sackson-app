package com.hotel.controller;


import com.hotel.services.UserService;
import com.hotel.views.UserSignupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class AuthController {

  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("register")
  public ResponseEntity<String> post(@RequestBody UserSignupRequest userSignupRequest) {

    userService.register(userSignupRequest.username(), userSignupRequest.password());

    return ResponseEntity.ok("registered successfully");
  }
}
