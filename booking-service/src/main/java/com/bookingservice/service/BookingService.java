package com.bookingservice.service;

import com.bookingservice.dto.*;
import com.bookingservice.entity.Receipt;
import com.bookingservice.exceptions.ReceiptGenerationInProcess;
import com.bookingservice.exceptions.ReceiptIdNotFound;
import com.bookingservice.exceptions.RoomLimitExceeded;
import com.bookingservice.queue.BookingQueueProducer;
import com.bookingservice.repository.BookingRepository;
import org.bson.types.ObjectId;
import org.jspecify.annotations.NonNull;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;
    private final BookingQueueProducer bookingQueueProducer;

    public BookingService(BookingRepository bookingRepository, BookingQueueProducer bookingQueueProducer) {
        this.bookingRepository = bookingRepository;
        this.bookingQueueProducer = bookingQueueProducer;
        this.restTemplate = new RestTemplate();
    }

    public Resource generateReceipt(String bookingId) throws ReceiptIdNotFound, ReceiptGenerationInProcess {
        ReceiptPdfUrl receipt = bookingRepository.findReceiptById(new ObjectId(bookingId));
        System.out.println(": receipt" + receipt);
        if (receipt == null) {
            throw new ReceiptIdNotFound();
        }

        String pdfUrl = receipt.pdfUrl();

        if (Objects.equals(pdfUrl, "")) {
            throw new ReceiptGenerationInProcess();
        }

        try {
            return new UrlResource(URI.create(pdfUrl));
        } catch (Exception e) {
            throw new RuntimeException("Error generating receipt for receipt id " + bookingId, e);
        }
    }

    public BookingResponseForUser book(BookingRequest bookingRequest, String name) throws RoomLimitExceeded {
        String url = "http://localhost:8081/api/search/internal/book";
        ResponseEntity<HotelReceipt> response = this.restTemplate.postForEntity(url, bookingRequest, HotelReceipt.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RoomLimitExceeded();
        }
        HotelReceipt responseBody = response.getBody();
        assert responseBody != null;
        BookingResponse processResponse = processBookingRequest(responseBody, name);
        bookingQueueProducer.pushJob(processResponse.receiptId().toString());
        return new BookingResponseForUser(processResponse.message());
    }

    private @NonNull BookingResponse processBookingRequest(HotelReceipt hotelReceipt, String username) {
        Receipt receipt = new Receipt(username, hotelReceipt.id(), hotelReceipt.hotel(), hotelReceipt.rooms(), hotelReceipt.bill(), "");
        Receipt savedHotel = bookingRepository.save(receipt);
        ObjectId receiptId = savedHotel.getId();
        String message = "Room booked successfully! \n ReceiptId is : " + receiptId;
        return new BookingResponse(message, receiptId);
    }

    public List<ReceiptPdfUrl> getBookingsByUser(String username) {
        return bookingRepository.findReceiptsByUsername(username);
    }

    public void store(String receiptId, String url) {
        ReceiptPdfUrl r = bookingRepository.findReceiptById(new ObjectId(receiptId));
        Receipt receipt = new Receipt(r.username(), r.hotel_id(), r.hotel(), r.rooms(), r.bill(), url);
        receipt.setId(new ObjectId(receiptId));
        bookingRepository.save(receipt);
        System.out.println(bookingRepository.findReceiptById(new ObjectId(receiptId)) + ":Here");
        System.out.println("Database successfully updated for Receipt ID: " + receiptId);
    }
}
