package com.mycompany.restaurantinvoicecontrol;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ValidationServiceTest {

    private ValidationService service;

    @Before
    public void setUp() {
        ArrayList<Invoice> invoices = new ArrayList<>();
        invoices.add(new Invoice(101, 150.0, "Active"));
        invoices.add(new Invoice(102, 250.0, "Cancelled"));
        service = new ValidationService(invoices);
    }

    @Test
    public void testValidModification() {
        assertEquals("Valid", service.validateRequest(101, "Modification", "Quantity 2"));
    }

    @Test
    public void testValidCancellationWithoutDetails() {
        assertEquals("Valid", service.validateRequest(101, "Cancellation", ""));
    }

    @Test
    public void testInvoiceNotFound() {
        assertEquals("Invoice not found", service.validateRequest(999, "Modification", "Quantity 2"));
    }

    @Test
    public void testInvalidType() {
        assertEquals("Invalid request type", service.validateRequest(101, "Delete", ""));
    }

    @Test
    public void testInvoiceAlreadyCancelled() {
        assertEquals("Invoice already cancelled", service.validateRequest(102, "Modification", "Quantity 2"));
    }

    @Test
    public void testModificationWithEmptyDetails() {
        assertEquals("Modification details required", service.validateRequest(101, "Modification", ""));
    }
}