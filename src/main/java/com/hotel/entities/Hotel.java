package com.hotel.entities;

import com.hotel.dto.HotelReceipt;
import com.hotel.dto.HotelView;
import org.springframework.data.annotation.Id;

public class Hotel {
  @Id
  private final int id;
  private final String name;
  private final String city;
  private double rent;
  private int availableRooms;

  public Hotel(String name, int id, String city, double rent, int availableRooms) {
    this.name = name;
    this.id = id;
    this.city = city;
    this.rent = rent;
    this.availableRooms = availableRooms;
  }

  public boolean isInCity(String city) {
    return city.equals(this.city);
  }

  public HotelView project() {
    return new  HotelView(this.name, this.city, this.id, this.rent, this.availableRooms);
  }

  public boolean areRoomsAvailable(int noOfRooms) {
    return this.availableRooms >= noOfRooms;
  }

  public HotelReceipt book(int noOfRooms) {
    this.availableRooms = this.availableRooms - noOfRooms;
    double bill = noOfRooms * rent;
    return new HotelReceipt(name, id, noOfRooms, bill);
  }

  public boolean isSameId(int hotelId) {
    return this.id == hotelId;
  }
}
