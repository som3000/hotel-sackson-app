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
import com.hotel.repositories.HotelsRepository;
import org.bson.types.ObjectId;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class BookingService {
  private final HotelsRepository hotelRepository;
  private final BookingRepository bookingRepository;

  public BookingService(HotelsRepository hotelRepository, BookingRepository bookingRepository) {
    this.hotelRepository = hotelRepository;
    this.bookingRepository = bookingRepository;
  }

  public byte[] generateReceipt(ObjectId bookingId) throws ReceiptIdNotFound {
    DetailedReceipt receipt = bookingRepository.findReceiptById(bookingId);
    if (receipt == null) {
      throw new ReceiptIdNotFound();
    }
    String detailedReceipt = receipt.toString();
    return detailedReceipt.getBytes(StandardCharsets.UTF_8);
  }

  public BookingResponse book(BookingRequest bookingRequest, String name) throws RoomLimitExceeded {
    int hotelId = bookingRequest.hotel_id();
    Hotel hotel = hotelRepository.findHotelById(hotelId);
    boolean isBookable = hotel.areRoomsAvailable(bookingRequest.rooms());
    if (!isBookable) {
      throw new RoomLimitExceeded();
    }

    return processBookingRequest(bookingRequest, hotel, name);
  }

  private @NonNull BookingResponse processBookingRequest(BookingRequest bookingRequest, Hotel hotel, String username) {
    HotelReceipt hotelReceipt = hotel.book(bookingRequest.rooms());
    hotelRepository.save(hotel);
    Receipt receipt = new Receipt(username, hotelReceipt.id(), hotelReceipt.hotel(), hotelReceipt.rooms(), hotelReceipt.bill());
    Receipt savedHotel = bookingRepository.save(receipt);
    ObjectId receiptId = savedHotel.getId();
    String message = "Room booked successfully! \n ReceiptId is : " + receiptId;
    return new BookingResponse(message);
  }

  public List<DetailedReceipt> getBookingsByUser(String username) {
    return bookingRepository.findReceiptsByUsername(username);
  }
}
