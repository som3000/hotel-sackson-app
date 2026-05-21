package com.hotel.exception;

public class ReceiptIdNotFound extends Throwable {
  public ReceiptIdNotFound() {
    super("Invalid receipt id");
  }
}
