package com.bookingservice.exceptions;

public class RoomLimitExceeded extends Throwable {
  public RoomLimitExceeded() {
    super("Room Limit Exceeded");
  }
}
