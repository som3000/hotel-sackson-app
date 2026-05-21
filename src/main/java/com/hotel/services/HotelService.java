package com.hotel.services;

import com.hotel.entities.Hotel;
import com.hotel.entities.HotelView;
import com.hotel.repositories.HotelRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HotelService {
  private final HotelRepository hotelRepository;

  public HotelService(HotelRepository hotelRepository) {
    this.hotelRepository = hotelRepository;
  }

  public List<HotelView> search(String city) {
    return hotelRepository.filterHotels(city);
  }
}
