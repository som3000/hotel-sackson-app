package com.hotel.workerservice.service;

import com.hotel.workerservice.dto.BookingDetails;
import jakarta.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.Executor;

@Service
public class PdfWorkerService implements ApplicationRunner {
  private final RedisTemplate<String, BookingDetails> redisTemplate;
  private final PdfService pdfService;
  private final Executor taskExecutor;

  public PdfWorkerService(RedisTemplate<String, BookingDetails> redisTemplate, PdfService pdfService, Executor taskExecutor) {
    this.redisTemplate = redisTemplate;
    this.pdfService = pdfService;
    this.taskExecutor = taskExecutor;
  }

  @Async("taskExecutor")
  public void startWorker() {
    while (true) {
      try {
        Object job = redisTemplate.opsForList().rightPop("booking_pdf_queue", Duration.ofSeconds(30));
        if (job instanceof BookingDetails(String receiptId)) {
          pdfService.processBooking(new ObjectId(receiptId));
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    taskExecutor.execute(this::startWorker);
  }
}
