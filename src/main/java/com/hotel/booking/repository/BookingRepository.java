package com.hotel.booking.repository;

import com.hotel.dto.DetailedReceipt;
import com.hotel.booking.entity.Receipt;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Receipt, Integer> {
  DetailedReceipt findReceiptById(ObjectId id);
  List<DetailedReceipt> findReceiptsByUsername(String username);
}