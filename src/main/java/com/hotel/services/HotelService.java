package com.hotel.services;

import com.hotel.dto.HotelView;
import com.hotel.repositories.HotelsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
  private final HotelsRepository hotelRepository;

  public HotelService(HotelsRepository hotelRepository) {
    this.hotelRepository = hotelRepository;
  }

  public List<HotelView> search(String city) {
    return hotelRepository.findByCity(city);
  }
}
