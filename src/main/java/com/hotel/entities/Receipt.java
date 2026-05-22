package com.hotel.entities;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Receipt {
  @Id
  private ObjectId id;

  private final String username;
  private final String hotel_id;
  private final String hotel;
  private final int rooms;
  private final double bill;

  public Receipt(String username, String hotel_id, String hotel, int rooms, double bill) {
    this.username = username;
    this.hotel_id = hotel_id;
    this.hotel = hotel;
    this.rooms = rooms;
    this.bill = bill;
  }

  public ObjectId getId() {
    return id;
  }
}