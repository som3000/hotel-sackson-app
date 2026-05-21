package com.hotel.repositories;

import com.hotel.dto.HotelReceipt;
import com.hotel.entities.Receipt;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class BookingRepository {
  private final Map<Integer, Receipt> receipts;

  public BookingRepository(Map<Integer, Receipt> receipts) {
    this.receipts = receipts;
  }

  public void store(int receiptId, String username, HotelReceipt hotelReceipt) {
    Receipt receipt = new Receipt(receiptId, username, hotelReceipt.id(), hotelReceipt.hotel(), hotelReceipt.rooms(), hotelReceipt.bill());
    receipts.put(receiptId, receipt);
  }


  public Receipt getReceiptById(int receiptId) {
    return receipts.get(receiptId);
  }
}
