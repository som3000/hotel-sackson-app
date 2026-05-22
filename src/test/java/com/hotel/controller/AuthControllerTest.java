package com.hotel.controller;

import com.hotel.exceptions.InvalidCredentials;
import com.hotel.exceptions.UsernameAlreadyExits;
import com.hotel.models.User;
import com.hotel.repositories.UserRepository;
import com.hotel.services.UserAuthenticationManager;
import com.hotel.services.UserService;
import com.hotel.utils.JwtUtils;
import com.hotel.views.Token;
import com.hotel.views.UserAuthRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureRestTestClient
class AuthControllerTest {

  @Autowired
  private RestTestClient client;

  @MockitoBean
  private UserRepository repo;

  @MockitoBean
  private JwtUtils jwtUtils;

  @MockitoBean
  private UserAuthenticationManager auth;

  @Test
  void itShouldRegisterUser() {

    UserService userService = new UserService(repo);
    User user = Mockito.mock(User.class);

    when(repo.getUserByUsername("u1")).thenReturn(user);

    client.post()
            .uri("/users/register")
            .body(new UserAuthRequest("u1", "123"))
            .exchange()
            .expectStatus().isOk();

    Mockito.verify(userService).register("u1", "123");
  }

  @Test
  void itShouldNotLetDuplicateUsername() {
    String expectedMsg = "username already exists";

    when(repo.getUserByUsername("u1")).thenThrow(UsernameAlreadyExits.class);

    String msg = client.post()
            .uri("/users/register")
            .body(new UserAuthRequest("u1", "123"))
            .exchange()
            .expectStatus().isBadRequest()
            .expectBody(String.class)
            .returnResult()
            .getResponseBody();

    assertEquals(expectedMsg, msg);

  }

  @Test
  void itShouldLoginAndReturnAccessToken() {


    String expectedToken = "abc";
    when(jwtUtils.generateToken("u1")).thenReturn(expectedToken);
    Mockito.doNothing().when(auth).authenticate("u1", "123");

    Token token = client.post()
            .uri("/users/login")
            .body(new UserAuthRequest("u1", "123"))
            .exchange()
            .expectStatus().isOk()
            .expectBody(Token.class)
            .returnResult()
            .getResponseBody();

    assertEquals(expectedToken, token.token());
    Mockito.verify(auth).authenticate("u1", "123");
  }

  @Test
  void itShouldLoginAndReturnErrorMessage() {

    String expectedMsg = "token not found for you";
    Mockito.doThrow(InvalidCredentials.class).when(auth).authenticate("u1", "123");

    Map token = client.post()
            .uri("/users/login")
            .body(new UserAuthRequest("u1", "123"))
            .exchange()
            .expectStatus().isBadRequest()
            .expectBody(Map.class)
            .returnResult()
            .getResponseBody();

    assert token != null;
    assertEquals(expectedMsg, token.get("msg"));
    Mockito.verify(auth).authenticate("u1", "123");
  }

}