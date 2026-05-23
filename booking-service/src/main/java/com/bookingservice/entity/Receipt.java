package com.bookingservice.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Receipt {
  @Id
  private ObjectId id;

  private final String username;
  private final String hotelId;
  private final String hotel;
  private final int rooms;
  private final double bill;
  private String pdfUrl;

  public Receipt(String username, String hotelId, String hotel, int rooms, double bill, String pdfUrl) {
    this.username = username;
    this.hotelId = hotelId;
    this.hotel = hotel;
    this.rooms = rooms;
    this.bill = bill;
    this.pdfUrl = pdfUrl;
  }

  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }
}