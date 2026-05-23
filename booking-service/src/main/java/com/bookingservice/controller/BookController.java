package com.bookingservice.controller;

import com.bookingservice.dto.BookingRequest;
import com.bookingservice.dto.BookingResponseForUser;
import com.bookingservice.dto.ReceiptPdfUrl;
import com.bookingservice.exceptions.ReceiptGenerationInProcess;
import com.bookingservice.exceptions.ReceiptIdNotFound;
import com.bookingservice.exceptions.RoomLimitExceeded;
import com.bookingservice.service.BookingService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
  public ResponseEntity<BookingResponseForUser> bookings(@RequestBody BookingRequest bookingRequest, Principal principal) {
    try {
      BookingResponseForUser responseForBooking = bookingService.book(bookingRequest, principal.getName());
      return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(responseForBooking);
    } catch (RoomLimitExceeded e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping
  public ResponseEntity<List<ReceiptPdfUrl>> getBookings(Principal principal) {
    String username = principal.getName();
    List<ReceiptPdfUrl> bookingsByUser = bookingService.getBookingsByUser(username);
    return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(bookingsByUser);
  }

  @GetMapping("{bookingId}/receipt.pdf")
  public ResponseEntity<Resource> generateReceipt(@PathVariable String bookingId) {
    try {
      Resource receiptPdf = bookingService.generateReceipt(bookingId);
      return ResponseEntity
              .ok()
              .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=receipt.pdf")
              .contentType(MediaType.APPLICATION_PDF)
              .body(receiptPdf);
    } catch (ReceiptIdNotFound e) {
      System.out.println(e.getMessage());
      return ResponseEntity.notFound().build();
    } catch (ReceiptGenerationInProcess e) {
        return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/internal/save-pdf-path")
  public ResponseEntity<Void> saveReceiptPdf(
          @RequestParam("receiptId") String receiptId,
          @RequestParam("pdfUrl") String pdfUrl
  ) {
    try {
      String cleanUrl = pdfUrl;

      if (pdfUrl != null && pdfUrl.contains("%3A")) {
        cleanUrl = URLDecoder.decode(pdfUrl, StandardCharsets.UTF_8);
      }
      System.out.println(receiptId);
      bookingService.store(receiptId, cleanUrl);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.badRequest().build();
    }
  }
}
