package com.hotel.search.controller;

import com.hotel.dto.BookingRequest;
import com.hotel.dto.HotelReceipt;
import com.hotel.dto.HotelView;
import com.hotel.exceptions.RoomLimitExceeded;
import com.hotel.search.service.SearchService;
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
