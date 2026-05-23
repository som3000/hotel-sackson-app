package com.inventoryservice.controller;

import com.inventoryservice.dto.BookingRequest;
import com.inventoryservice.dto.HotelReceipt;
import com.inventoryservice.dto.HotelView;
import com.inventoryservice.exceptions.RoomLimitExceeded;
import com.inventoryservice.service.SearchService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
  private final SearchService searchService;

  public SearchController(SearchService searchService) {
    this.searchService = searchService;
  }

  @GetMapping("/hotels")
  public ResponseEntity<List<HotelView>> search(@RequestParam(defaultValue = "") String city) {
    List<HotelView> hotels = searchService.search(city);
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(hotels);
  }

  @PostMapping("/internal/book")
  public ResponseEntity<HotelReceipt> bookRoom(@RequestBody BookingRequest bookingRequest) {
      try {
          HotelReceipt hotelReceipt = searchService.allocateRoom(bookingRequest);
          return ResponseEntity
                  .ok()
                  .body(hotelReceipt);
      } catch (RoomLimitExceeded e) {
          return ResponseEntity
                  .badRequest()
                  .build();
      }
  }
}
