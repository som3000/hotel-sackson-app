package com.hotel.services;

import com.hotel.dto.BookingRequest;
import com.hotel.dto.BookingResponse;
import com.hotel.dto.DetailedReceipt;
import com.hotel.dto.HotelReceipt;
import com.hotel.entities.Hotel;
import com.hotel.entities.Receipt;
import com.hotel.exception.ReceiptIdNotFound;
import com.hotel.exception.RoomLimitExceeded;
import com.hotel.repositories.BookingRepository;
import com.hotel.repositories.HotelRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class BookingService {
  private final HotelRepository hotelRepository;
  private final BookingRepository bookingRepository;
  private int nextReceiptId;

  public BookingService(HotelRepository hotelRepository, BookingRepository bookingRepository) {
    this.hotelRepository = hotelRepository;
    this.bookingRepository = bookingRepository;
    this.nextReceiptId = 1;
  }

  public byte[] generateReceipt(int bookingId) throws ReceiptIdNotFound {
    Receipt receipt = bookingRepository.getReceiptById(bookingId);
    if (receipt == null) {
      throw new ReceiptIdNotFound();
    }
    String detailedReceipt = receipt.toString();
    return detailedReceipt.getBytes(StandardCharsets.UTF_8);
  }

  public BookingResponse book(BookingRequest bookingRequest) throws RoomLimitExceeded {
    int hotelId = bookingRequest.hotel_id();
    Hotel hotel = hotelRepository.find(hotelId);
    boolean isBookable = hotel.areRoomsAvailable(bookingRequest.noOfRooms());
    if (!isBookable) {
      throw new RoomLimitExceeded();
    }

    return processBookingRequest(bookingRequest, hotel);
  }

  private @NonNull BookingResponse processBookingRequest(BookingRequest bookingRequest, Hotel hotel) {
    HotelReceipt hotelReceipt = hotel.book(bookingRequest.noOfRooms());
    int receiptId = nextReceiptId++;
    String message = "Room booked successfully! \n ReceiptId is : " + receiptId;
    String username = "user1";
    bookingRepository.store(receiptId, username, hotelReceipt);
    return new BookingResponse(message);
  }
}
