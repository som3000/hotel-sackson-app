package com.inventoryservice.service;

import com.inventoryservice.dto.BookingRequest;
import com.inventoryservice.dto.HotelReceipt;
import com.inventoryservice.dto.HotelView;
import com.inventoryservice.exceptions.RoomLimitExceeded;
import com.inventoryservice.entities.Hotel;
import com.inventoryservice.repositories.HotelsRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class SearchService {
    private final HotelsRepository hotelRepository;
    public RedisTemplate<String, List<HotelView>> redisTemplate;

    public SearchService(HotelsRepository hotelRepository, RedisTemplate<String, List<HotelView>> redisTemplate) {
        this.hotelRepository = hotelRepository;
        this.redisTemplate = redisTemplate;
    }

    public List<HotelView> search(String city) {
        String key = "hotelSearch:city:" + city.toLowerCase();
        List<HotelView> hotels = redisTemplate.opsForValue().get(key);

        if (hotels != null) {
            System.out.println("Cache hit");
            return hotels;
        }
        System.out.println("Cache missed");

        List<HotelView> hotelsByCity = hotelRepository.findByCity(city);
        redisTemplate
                .opsForValue()
                .set(key, hotelsByCity, Duration.ofMinutes(2));

        return hotelsByCity;
    }

    public HotelReceipt allocateRoom(BookingRequest bookingRequest) throws RoomLimitExceeded {
        Hotel hotel = hotelRepository.findHotelById(bookingRequest.hotel_id());
        int noOfRooms = bookingRequest.rooms();

        if (hotel == null || !hotel.areRoomsAvailable(noOfRooms)) {
            throw new RoomLimitExceeded("no rooms available");
        }
        return hotel.book(noOfRooms);
    }
}
