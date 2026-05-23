package com.inventoryservice.entities;

import com.inventoryservice.dto.HotelReceipt;
import org.springframework.data.annotation.Id;

public class Hotel {
  @Id
  private String id;
  private final String name;
  private final String city;
  private final double rent;
  private int availableRooms;

  public Hotel(String name, String city, double rent, int availableRooms) {
    this.name = name;
    this.city = city;
    this.rent = rent;
    this.availableRooms = availableRooms;
  }

  public boolean areRoomsAvailable(int noOfRooms) {
    return this.availableRooms >= noOfRooms;
  }

  public HotelReceipt book(int noOfRooms) {
    this.availableRooms = this.availableRooms - noOfRooms;
    double bill = noOfRooms * rent;
    return new HotelReceipt(name, id, noOfRooms, bill);
  }
}
