package com.hotel.dto;

public record DetailedReceipt(int receiptId, String username, int id, String hotel, int rooms, double bill) {
}
