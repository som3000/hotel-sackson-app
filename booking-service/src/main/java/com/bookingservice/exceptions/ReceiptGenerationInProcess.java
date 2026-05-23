package com.bookingservice.exceptions;

public class ReceiptGenerationInProcess extends Throwable {
    public ReceiptGenerationInProcess() {
        super("Wait for the receipt to be generated");
    }
}
