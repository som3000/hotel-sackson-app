package com.hotel.workerservice.config;

import com.hotel.workerservice.dto.BookingDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;
@Configuration
public class RedisConfigWorker {
  @Bean
  public RedisTemplate<String, BookingDetails> redisWorkerTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, BookingDetails> template = new RedisTemplate<>();

    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new JacksonJsonRedisSerializer<>(BookingDetails.class));

    return template;
  }
}
