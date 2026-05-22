package com.hotel.exceptions;

public class UsernameAlreadyExits extends RuntimeException {
  public UsernameAlreadyExits(String message) {
    super(message);
  }
}
