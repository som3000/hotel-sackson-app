package com.bookingservice.controller;

import com.bookingservice.service.BookingService;
import com.bookingservice.dto.BookingRequest;
import com.bookingservice.dto.BookingResponse;
import com.bookingservice.dto.DetailedReceipt;
import com.bookingservice.exceptions.ReceiptIdNotFound;
import com.bookingservice.exceptions.RoomLimitExceeded;
import org.bson.types.ObjectId;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookController {
  private final BookingService bookingService;

  public BookController(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  @PostMapping
  public ResponseEntity<BookingResponse> bookings(@RequestBody BookingRequest bookingRequest, Principal principal) {
    try {
      BookingResponse responseForBooking = bookingService.book(bookingRequest, principal.getName());
      return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(responseForBooking);
    } catch (RoomLimitExceeded e) {
      BookingResponse bookingResponse = new BookingResponse(e.getMessage());
      return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(bookingResponse);
    }
  }

  @GetMapping
  public ResponseEntity<List<DetailedReceipt>> getBookings(Principal principal) {
    String username = principal.getName();
    List<DetailedReceipt> bookingsByUser = bookingService.getBookingsByUser(username);
    return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(bookingsByUser);
  }

  @GetMapping("{bookingId}/receipt.pdf")
  public ResponseEntity<byte[]> generateReceipt(@PathVariable ObjectId bookingId) {
    try {
      byte[] receiptPdf = bookingService.generateReceipt(bookingId);
      return ResponseEntity
              .ok()
              .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=receipt.pdf")
              .contentType(MediaType.APPLICATION_PDF)
              .body(receiptPdf);
    } catch (ReceiptIdNotFound e) {
      return ResponseEntity.notFound().build();
    }
  }
}
