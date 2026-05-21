package com.hotel.repositories;

import com.hotel.dto.DetailedReceipt;
import com.hotel.dto.HotelReceipt;
import com.hotel.entities.Receipt;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BookingRepository extends MongoRepository<Receipt, Integer> {
  DetailedReceipt findReceiptById(ObjectId id);
  List<DetailedReceipt> findReceiptsByUsername(String username);
}