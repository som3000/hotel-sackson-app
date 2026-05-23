package com.bookingservice.dto;

public record ReceiptPdfUrl(String id, String pdfUrl, String username, String hotel_id, String hotel, int rooms, double bill) {
}
