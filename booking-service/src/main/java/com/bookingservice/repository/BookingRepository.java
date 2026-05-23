package com.bookingservice.repository;

import com.bookingservice.dto.DetailedReceipt;
import com.bookingservice.entity.Receipt;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Receipt, Integer> {
  DetailedReceipt findReceiptById(ObjectId id);
  List<DetailedReceipt> findReceiptsByUsername(String username);
}