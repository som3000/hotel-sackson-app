package com.hotel.search.repositories;

import com.hotel.dto.HotelView;
import com.hotel.search.entities.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HotelsRepository extends MongoRepository<Hotel, Integer> {
  List<HotelView> findByCity(String city);

  com.hotel.search.entities.Hotel findHotelById(String id);
}
