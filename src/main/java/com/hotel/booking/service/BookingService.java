package com.hotel.booking.service;

import com.hotel.booking.repository.BookingRepository;
import com.hotel.dto.BookingRequest;
import com.hotel.dto.BookingResponse;
import com.hotel.dto.DetailedReceipt;
import com.hotel.dto.HotelReceipt;
import com.hotel.booking.entity.Receipt;
import com.hotel.exceptions.ReceiptIdNotFound;
import com.hotel.exceptions.RoomLimitExceeded;
import org.bson.types.ObjectId;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class BookingService {
  private final BookingRepository bookingRepository;
  private final RestTemplate restTemplate;

  public BookingService(BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
    this.restTemplate = new RestTemplate();
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
    String url = "http://localhost:8080/api/search/internal/book";
    ResponseEntity<HotelReceipt> response = this.restTemplate.postForEntity(url, bookingRequest, HotelReceipt.class);
    if (!response.getStatusCode().is2xxSuccessful()) {
      throw new RoomLimitExceeded();
    }
      assert response.getBody() != null;
      return processBookingRequest(response.getBody(), name);
  }

  private @NonNull BookingResponse processBookingRequest(HotelReceipt hotelReceipt, String username) {
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
