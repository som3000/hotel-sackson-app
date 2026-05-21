package com.hotel.services;

import com.hotel.dto.HotelView;
import com.hotel.repositories.HotelRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
  private final HotelRepository hotelRepository;

  public HotelService(HotelRepository hotelRepository) {
    this.hotelRepository = hotelRepository;
  }

  public List<HotelView> search(String city) {
    return hotelRepository.filterHotels(city);
  }
}
