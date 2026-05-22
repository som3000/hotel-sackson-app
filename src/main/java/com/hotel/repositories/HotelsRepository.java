package com.hotel.repositories;

import com.hotel.dto.HotelView;
import com.hotel.entities.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HotelsRepository extends MongoRepository<Hotel, Integer> {
  List<HotelView> findByCity(String city);

  Hotel findHotelById(String id);
}
