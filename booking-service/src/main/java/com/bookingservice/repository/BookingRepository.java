package com.bookingservice.repository;

import com.bookingservice.dto.ReceiptPdfUrl;
import com.bookingservice.entity.Receipt;
import org.bson.types.ObjectId;
import org.jspecify.annotations.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Receipt, ObjectId> {
  @Query("{ '_id' : ?0 }")
  ReceiptPdfUrl findReceiptById(@NonNull ObjectId id);
  List<ReceiptPdfUrl> findReceiptsByUsername(String username);
}