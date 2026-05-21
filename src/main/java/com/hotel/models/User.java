package com.hotel.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document("user")
public class User {
  @Id
  private final String id;
  private final String username;
  private final String password;

  public User(String id, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof User user)) return false;
    return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, password);
  }
}
