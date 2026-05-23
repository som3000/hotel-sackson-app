package com.bookingservice.dto;

public record DetailedReceipt(String username, String id, String hotel, int rooms, double bill) {
  @Override
  public String toString() {
    return "Receipt{" +
            "username='" + username + '\'' +
            ", id=" + id +
            ", hotel='" + hotel + '\'' +
            ", rooms=" + rooms +
            ", bill=" + bill +
            '}';
  }
}
