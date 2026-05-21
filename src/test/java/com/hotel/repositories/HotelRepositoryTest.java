package com.hotel.repositories;

import com.hotel.dto.HotelView;
import com.hotel.entities.Hotel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HotelRepositoryTest {

  @BeforeEach
  void setUp() {
  }

//  @Disabled
//  void shouldReturnFilteredHotels() {
//    List<Hotel> hotels = new ArrayList<>();
//    Hotel SACKSON = new Hotel("SACKSON", 1, "Delhi", 1000.00, 55);
//    Hotel IRB = new Hotel("ITC ROYAL BENGAL", 2, "Kolkata", 10000.00, 120);
//
//    hotels.add(SACKSON);
//    hotels.add(IRB);
//    HotelView expected = new HotelView("SACKSON", "Delhi", 1, 1000.00, 55);
//    HotelRepository hotelRepository = new HotelRepository(hotels);
//    assertEquals(1, hotelRepository.filterHotels("Delhi").size());
//  }
}