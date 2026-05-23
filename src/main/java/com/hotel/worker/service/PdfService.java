package com.hotel.worker.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

@Service
public class PdfService {
  private Cloudinary cloudinary;
  public PdfService(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
  }

  public void processBooking(String bookingId) throws Exception {
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
            "public_id", "receipts/" + fileName
    ));

    String pdfUrl = (String) upload.get("secure_url");

  }
}
