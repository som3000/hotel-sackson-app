package com.hotel.workerservice.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class PdfService {
  private final RestTemplate restTemplate;
  private final Cloudinary cloudinary;

  public PdfService(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
    this.restTemplate = new RestTemplate();
  }

  public void processBooking(ObjectId bookingId) throws Exception {
    String fileName = bookingId + ".pdf";
    String path = "/tmp/" + fileName;

    File file = new File(path);

    try (PdfWriter writer = new PdfWriter(path)) {
      PdfDocument pdf = new PdfDocument(writer);
      Document document = new Document(pdf);
      document.add(new Paragraph("HOTEL BOOKING RECEIPT"));
      document.add(new Paragraph("BookingId: " + bookingId));
      document.close();
    }

    Map<?, ?> upload = cloudinary.uploader().upload(file, ObjectUtils.asMap(
            "resource_type", "raw",
            "type", "upload",
            "public_id", "receipts/" + fileName,
            "use_filename", true,
            "unique_filename", false
    ));

    String pdfUrl = String.valueOf(upload.get("secure_url"));

    String encodedPdfUrl = URLEncoder.encode(pdfUrl, StandardCharsets.UTF_8);

    String url = "http://localhost:8082/api/bookings/internal/save-pdf-path"
            + "?receiptId=" + bookingId
            + "&pdfUrl=" + encodedPdfUrl;
    URI finalUri = URI.create(url);
    ResponseEntity<Void> response = this.restTemplate.getForEntity(finalUri, Void.class);

    if (!response.getStatusCode().is2xxSuccessful()) {
      throw new RuntimeException("Failed to upload pdf");
    }
  }
}
