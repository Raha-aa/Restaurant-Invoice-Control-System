package com.mycompany.restaurantinvoicecontrol;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ApprovalServiceTest {

    private ApprovalService approvalService;
    private ArrayList<Invoice> invoices;
    private ArrayList<Request> requests;

    @Before
    public void setUp() {

        invoices = new ArrayList<>();
        requests = new ArrayList<>();

        invoices.add(new Invoice(101, 150.0, "Active"));
        invoices.add(new Invoice(102, 250.0, "Active"));

        requests.add(new Request(
                1, 101, "Modification",
                "120", "Incorrect invoice amount"));

        requests.add(new Request(
                2, 102, "Cancellation",
                "", "Duplicate invoice"));

        InvoiceService invoiceService
                = new InvoiceService(invoices);

        approvalService
                = new ApprovalService(requests, invoiceService);
    }

    @Test
    public void testApproveModification() {

        String result = approvalService.processDecision(
                1, "Approve", "Accountant", "");

        assertEquals("Approved", result);
        assertEquals("Approved", requests.get(0).getRequestStatus());
        assertEquals(120.0, invoices.get(0).getTotalAmount(), 0.001);
    }

    @Test
    public void testRejectRequest() {

        String result = approvalService.processDecision(
                1, "Reject", "Supervisor",
                "Insufficient justification");

        assertEquals("Rejected", result);
        assertEquals("Rejected", requests.get(0).getRequestStatus());
        assertEquals("Insufficient justification",
                requests.get(0).getRejectionReason());
        assertEquals(150.0, invoices.get(0).getTotalAmount(), 0.001);
    }

    @Test
    public void testUnauthorizedEmployee() {

        String result = approvalService.processDecision(
                2, "Approve", "Employee", "");

        assertEquals("Not authorized", result);
        assertEquals("Pending", requests.get(1).getRequestStatus());
        assertEquals("Active", invoices.get(1).getStatus());
    }

    @Test
    public void testApproveCancellation() {

        String result = approvalService.processDecision(
                2, "Approve", "Supervisor", "");

        assertEquals("Approved", result);
        assertEquals("Approved", requests.get(1).getRequestStatus());
        assertEquals("Cancelled", invoices.get(1).getStatus());
    }
}
