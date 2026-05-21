package com.hotel.services;

import org.bson.codecs.IdGenerator;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDGenerator implements IdGenerator {
  @Override
  public String generate() {
    return UUID.randomUUID().toString();
  }
}
