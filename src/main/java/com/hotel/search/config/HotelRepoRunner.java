package com.hotel.search.config;

import com.hotel.search.entities.Hotel;
import com.hotel.search.repositories.HotelsRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class HotelRepoRunner implements ApplicationRunner {
  private final HotelsRepository hotelsRepository;

  public HotelRepoRunner(HotelsRepository hotelsRepository) {
    this.hotelsRepository = hotelsRepository;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    if (hotelsRepository.count() == 0L) {
      hotelsRepository.save(new Hotel("SACKSON", "Delhi", 1000.00, 55));
      hotelsRepository.save(new Hotel("ITC ROYAL BENGAL", "Kolkata", 10000.00, 120));
    }
  }
}
