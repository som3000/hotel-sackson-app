package com.hotel.repositories;

import com.hotel.entities.Hotel;
import com.hotel.dto.HotelView;

import java.util.List;
import java.util.Objects;

public class HotelRepository {
  private final List<Hotel> hotels;

  public HotelRepository(List<Hotel> hotels) {
    this.hotels = hotels;
  }

  public List<HotelView> filterHotels(String city) {
    if (Objects.equals(city, "")) {
      return hotels.stream().map(Hotel::project).toList();
    }
    return hotels.stream().filter(hotel ->  hotel.isInCity(city)).map(Hotel::project).toList();
  }

  public Hotel find(int hotel_id) {
    return hotels
            .stream()
            .filter(hotel -> hotel.isSameId(hotel_id))
            .toList()
            .getFirst();
  }
}
