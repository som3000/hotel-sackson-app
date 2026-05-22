package com.hotel.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document("user")
public class User {
  @Id
  private final String username;
  private final String password;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof User user)) return false;
    return Objects.equals(username, user.username) && Objects.equals(password, user.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password);
  }
}
