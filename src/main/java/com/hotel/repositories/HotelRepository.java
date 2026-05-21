package com.hotel.repositories;

import com.hotel.entities.Hotel;
import com.hotel.dto.HotelView;

import java.util.List;

public class HotelRepository {
  private final List<Hotel> hotels;

  public HotelRepository(List<Hotel> hotels) {
    this.hotels = hotels;
  }

  public List<HotelView> filterHotels(String city) {
    return hotels.stream().filter(hotel -> hotel.isInCity(city)).map(Hotel::project).toList();
  }

  public Hotel find(int hotel_id) {
    return hotels.get(hotel_id);
  }
}
