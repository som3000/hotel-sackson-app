package com.hotel.worker.service;

import com.hotel.booking.dto.BookingDetails;
import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PdfWorkerService {
  private final RedisTemplate<String, Object> redisTemplate;

  public PdfWorkerService(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @PostConstruct
  @Async("workerExecutor")
  public void startWorker() {
    while (true) {
      try {
        Object job = redisTemplate.opsForValue().get("worker");
        if (job != null) {
          BookingDetails jobDetails = (BookingDetails) job;
          // Pdf service.processBooking
        }
      }
    }
  }
}
