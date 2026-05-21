package com.hotel.exceptions;

public class RoomLimitExceeded extends Throwable {
  public RoomLimitExceeded() {
    super("Room Limit Exceeded");
  }
}
