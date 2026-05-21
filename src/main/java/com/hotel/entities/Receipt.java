package com.hotel.entities;

import com.hotel.dto.DetailedReceipt;

public class Receipt {
  private final int receiptId;
  private final String username;
  private final int id;
  private final String hotel;
  private final int rooms;
  private final double bill;

  public Receipt(int receiptId, String username, int id, String hotel, int rooms, double bill) {
    this.receiptId = receiptId;
    this.username = username;
    this.id = id;
    this.hotel = hotel;
    this.rooms = rooms;
    this.bill = bill;
  }

  @Override
  public String toString() {
    return "Receipt{" +
            "receiptId=" + receiptId +
            ", username='" + username + '\'' +
            ", id=" + id +
            ", hotel='" + hotel + '\'' +
            ", rooms=" + rooms +
            ", bill=" + bill +
            '}';
  }
}
