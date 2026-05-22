package com.hotel.search.service;

import com.hotel.dto.BookingRequest;
import com.hotel.dto.HotelReceipt;
import com.hotel.dto.HotelView;
import com.hotel.exceptions.RoomLimitExceeded;
import com.hotel.search.entities.Hotel;
import com.hotel.search.repositories.HotelsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    private final HotelsRepository hotelRepository;

    public SearchService(HotelsRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<HotelView> search(String city) {
        return hotelRepository.findByCity(city);
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
