package com.hotel.booking.queue;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookingQueueProducer {
    private final RedisTemplate<String, Object> redisTemplate;

    public BookingQueueProducer(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
