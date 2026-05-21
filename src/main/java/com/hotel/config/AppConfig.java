package com.hotel.config;

import com.hotel.entities.Hotel;
import com.hotel.repositories.HotelRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {
  @Bean
  public HotelRepository hotelRepository() {
    List<Hotel> hotels = new ArrayList<>();
    hotels.add(new Hotel("SACKSON", 1, "Delhi", 1000.00, 55));
    hotels.add(new Hotel("ITC ROYAL BENGAL", 2, "Kolkata", 10000.00, 120));
    return new HotelRepository(hotels);
  }
}
