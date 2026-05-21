package com.hotel.dto;

import org.bson.types.ObjectId;

public record DetailedReceipt(String username, ObjectId id, String hotel, int rooms, double bill) {
    @Override
    public String toString() {
        return "Receipt{" +
                "username='" + username + '\'' +
                ", id=" + id +
                ", hotel='" + hotel + '\'' +
                ", rooms=" + rooms +
                ", bill=" + bill +
                '}';
    }
}
