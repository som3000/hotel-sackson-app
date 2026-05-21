package com.hotel.entities;

public class Hotel {
  private final String name;
  private final String city;
  private final String id;
  private double rent;
  private int availableRooms;

  public Hotel(String name, String id, String city, double rent, int availableRooms) {
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
}
