package com.mycompany.restaurantinvoicecontrol;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RequestServiceTest {

    private RequestService service;
    private ArrayList<Invoice> invoices;
    private ArrayList<Request> requests;

    @BeforeEach
    public void setUp() {
        invoices = new ArrayList<>();
        requests = new ArrayList<>();

        invoices.add(new Invoice(101, 150.0, "Active"));
        invoices.add(new Invoice(102, 250.0, "Active"));

        service = new RequestService(invoices, requests);
    }

    @Test
    public void testModificationRequest() {
        String result = service.createRequest(
                101, "Modification",
                "Change amount to 120 SAR",
                "Incorrect amount");

        assertEquals("Pending", result);
        assertEquals(1, requests.size());
    }

    @Test
    public void testCancellationRequest() {
        String result = service.createRequest(
                102, "Cancellation", "",
                "Duplicate invoice");

        assertEquals("Pending", result);
        assertEquals(1, requests.size());
    }

    @Test
    public void testInvoiceNotFound() {
        String result = service.createRequest(
                999, "Modification", "", "");

        assertEquals("Invoice not found", result);
        assertEquals(0, requests.size());
    }

    @Test
    public void testRequestWithoutNote() {
        String result = service.createRequest(
                101, "Cancellation", "", "");

        assertEquals("Pending", result);
        assertEquals(1, requests.size());
    }
}