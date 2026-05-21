package com.hotel.exceptions;

public class ReceiptIdNotFound extends Throwable {
  public ReceiptIdNotFound() {
    super("Invalid receipt id");
  }
}
