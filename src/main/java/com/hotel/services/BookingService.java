package com.hotel.services;

import com.hotel.dto.BookingRequest;
import com.hotel.dto.BookingResponse;
import com.hotel.entities.Hotel;
import com.hotel.exception.RoomLimitExceeded;
import com.hotel.repositories.HotelRepository;
import org.springframework.stereotype.Component;

@Component
public class BookingService {
  private final HotelRepository hotelRepository;
  private int receiptId;

  public BookingService(HotelRepository hotelRepository) {
    this.hotelRepository = hotelRepository;
    this.receiptId = 1;
  }

  public BookingResponse book(BookingRequest bookingRequest) throws RoomLimitExceeded {
    Hotel hotel = hotelRepository.find(bookingRequest.hotel_id());
    boolean isBookable = hotel.areRoomsAvailable(bookingRequest.noOfRooms());
    if(!isBookable){
      throw new RoomLimitExceeded();
    }

    hotel.book(bookingRequest.noOfRooms());
    String message = "Room booked successfully! \n ReceiptId is : receipt" + receiptId++;
    return new BookingResponse(message);
  }
}
