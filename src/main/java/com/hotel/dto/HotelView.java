package com.hotel.dto;

import org.bson.types.ObjectId;

public record HotelView(String name, String city, ObjectId id, double rent, int availableRooms) {
}
