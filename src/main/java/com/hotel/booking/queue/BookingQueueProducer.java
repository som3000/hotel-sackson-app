package com.hotel.booking.queue;

import com.hotel.booking.dto.BookingDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookingQueueProducer {
    private static final Logger log = LoggerFactory.getLogger(BookingQueueProducer.class);
    private static final String QUEUE_NAME = "booking_pdf_queue";
    private final RedisTemplate<String, Object> redisTemplate;

    public BookingQueueProducer(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void pushJob(String bookingId) {
        log.info("Push Booking to Queue: {}", bookingId);
        BookingDetails bookingDetails = new BookingDetails(bookingId);
        redisTemplate.opsForList().leftPush(QUEUE_NAME, bookingDetails);
    }
}
