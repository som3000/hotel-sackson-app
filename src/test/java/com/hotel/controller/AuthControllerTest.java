package com.hotel.controller;

import com.hotel.exceptions.InvalidCredentials;
import com.hotel.services.UserAuthenticationManager;
import com.hotel.services.UserService;
import com.hotel.utils.JwtUtils;
import com.hotel.views.UserAuthRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureRestTestClient
class AuthControllerTest {

  @Autowired
  private RestTestClient client;

  @MockitoBean
  private UserService userService;

  @MockitoBean
  private JwtUtils jwtUtils;

  @MockitoBean
  private UserAuthenticationManager auth;

  @Test
  void itShouldRegisterUser() {

    client.post()
            .uri("/users/register")
            .body(new UserAuthRequest("u1", "123"))
            .exchange()
            .expectStatus().isOk();

    Mockito.verify(userService).register("u1", "123");
  }

  @Test
  void itShouldLoginAndReturnAccessToken() {

    String expectedToken = "abc";
    Mockito.when(jwtUtils.generateToken("u1")).thenReturn(expectedToken);
    Mockito.doNothing().when(auth).authenticate("u1", "123");

    String token = client.post()
            .uri("/users/login")
            .body(new UserAuthRequest("u1", "123"))
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .returnResult()
            .getResponseBody();

    assertEquals(expectedToken, token);
    Mockito.verify(auth).authenticate("u1", "123");
  }

  @Test
  void itShouldLoginAndReturnErrorMessage() {

    String expectedMsg = "invalid credential";
    Mockito.doThrow(InvalidCredentials.class).when(auth).authenticate("u1", "123");

    String msg = client.post()
            .uri("/users/login")
            .body(new UserAuthRequest("u1", "123"))
            .exchange()
            .expectStatus().isBadRequest()
            .expectBody(String.class)
            .returnResult()
            .getResponseBody();

    assertEquals(expectedMsg, msg);
    Mockito.verify(auth).authenticate("u1", "123");
  }

}