package com.hotel.repositories;

import com.hotel.dto.HotelView;
import com.hotel.entities.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface HotelsRepository extends MongoRepository<Hotel, Integer> {
    List<HotelView> findByCity(String city);
    Hotel findHotelById(int hotelId);
}
