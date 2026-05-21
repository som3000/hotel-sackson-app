package com.hotel.controller;

import com.hotel.services.UserService;
import com.hotel.views.UserSignupRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest
@AutoConfigureRestTestClient
class AuthControllerTest {

  @Autowired
  private RestTestClient client;

  @MockitoBean
  private UserService userService;

  @Test
  void itShouldRegisterUser() {

    client.post()
            .uri("/api/users/register")
            .body(new UserSignupRequest("u1", "123"))
            .exchange()
            .expectStatus().isOk();

    Mockito.verify(userService).register("u1", "123");
  }
}