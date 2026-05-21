package com.hotel.exception;

public class RoomLimitExceeded extends Throwable {
  public RoomLimitExceeded() {
    super("Room Limit Exceeded");
  }
}
