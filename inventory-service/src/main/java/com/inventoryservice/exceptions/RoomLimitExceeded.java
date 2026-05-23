package com.inventoryservice.exceptions;

public class RoomLimitExceeded  extends  RuntimeException{
  public RoomLimitExceeded(String message) {
    super(message);
  }
}
