package com.hotel.entities;

import com.hotel.dto.HotelView;

public class Hotel {
  private final String name;
  private final String city;
  private final int id;
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
    System.out.println("Is in city: " + city);
    System.out.println(city.equals(this.city));
    return city.equals(this.city);
  }

  public HotelView project() {
    return new  HotelView(this.name, this.city, this.id, this.rent, this.availableRooms);
  }

  public boolean areRoomsAvailable(int noOfRooms) {
    return this.availableRooms >= noOfRooms;
  }

  public void book(int noOfRooms) {
    this.availableRooms = this.availableRooms - noOfRooms;
  }
}
