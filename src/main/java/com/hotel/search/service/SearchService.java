package com.hotel.search.service;

import com.hotel.dto.BookingRequest;
import com.hotel.dto.HotelReceipt;
import com.hotel.dto.HotelView;
import com.hotel.exceptions.RoomLimitExceeded;
import com.hotel.search.entities.Hotel;
import com.hotel.search.repositories.HotelsRepository;
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
                .set(key, hotelsByCity, Duration.ofMinutes(1));

        return hotelsByCity;
    }

    public HotelReceipt allocateRoom(BookingRequest bookingRequest) throws RoomLimitExceeded {
        Hotel hotel = hotelRepository.findHotelById(bookingRequest.hotel_id());
        int noOfRooms = bookingRequest.rooms();
        boolean areRoomsAvailable = hotel.areRoomsAvailable(noOfRooms);
        if (!areRoomsAvailable) {
            throw new RoomLimitExceeded();
        }
        return hotel.book(noOfRooms);
    }
}
