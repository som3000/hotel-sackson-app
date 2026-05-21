package com.hotel.controller;

import com.hotel.entities.Hotel;
import com.hotel.entities.HotelView;
import com.hotel.services.HotelService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
  private final HotelService hotelService;

  public SearchController(HotelService hotelService) {
    this.hotelService = hotelService;
  }

  @GetMapping("/hotels")
  public ResponseEntity<List<HotelView>> search(@RequestParam(defaultValue = "") String city) {
    List<HotelView> hotels = hotelService.search(city);
    System.out.println(hotels);
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(hotels);
  }
}
