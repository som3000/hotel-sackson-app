package com.hotel.services;

import com.hotel.dto.BookingRequest;
import com.hotel.dto.BookingResponse;
import com.hotel.dto.DetailedReceipt;
import com.hotel.dto.HotelReceipt;
import com.hotel.entities.Hotel;
import com.hotel.entities.Receipt;
import com.hotel.exceptions.ReceiptIdNotFound;
import com.hotel.exceptions.RoomLimitExceeded;
import com.hotel.repositories.BookingRepository;
import com.hotel.repositories.HotelRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
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

  public BookingResponse book(BookingRequest bookingRequest, String name) throws RoomLimitExceeded {
    System.out.println(bookingRequest.hotel_id());
    int hotelId = bookingRequest.hotel_id();
    Hotel hotel = hotelRepository.find(hotelId);
    boolean isBookable = hotel.areRoomsAvailable(bookingRequest.rooms());
    if (!isBookable) {
      throw new RoomLimitExceeded();
    }

    return processBookingRequest(bookingRequest, hotel, name);
  }

  private @NonNull BookingResponse processBookingRequest(BookingRequest bookingRequest, Hotel hotel, String username) {
    HotelReceipt hotelReceipt = hotel.book(bookingRequest.rooms());
    int receiptId = nextReceiptId++;
    String message = "Room booked successfully! \n ReceiptId is : " + receiptId;
    bookingRepository.store(receiptId, username, hotelReceipt);
    return new BookingResponse(message);
  }

  public List<DetailedReceipt> getBookingsByUser(String username) {
    return bookingRepository.getBookingsByUsername(username);
  }
}
