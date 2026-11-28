package com.mycompany.restaurantinvoicecontrol;

import java.util.ArrayList;
import java.util.Scanner;

public class RestaurantInvoiceControl {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        ArrayList<Invoice> invoices = new ArrayList<>();
        ArrayList<Request> requests = new ArrayList<>();

        invoices.add(new Invoice(101, 150.0, "Active"));
        invoices.add(new Invoice(102, 250.0, "Active"));
        invoices.add(new Invoice(103, 85.0, "Active"));

        RequestService service = new RequestService(invoices, requests);
        ValidationService validationService = new ValidationService(invoices);
        NotificationService notificationService = new NotificationService();
        InvoiceService invoiceService = new InvoiceService(invoices);
        ApprovalService approvalService = new ApprovalService(requests, invoiceService);

        System.out.println("Restaurant Invoice Control System");
        System.out.println("--------------------------------");

        System.out.println("Available Invoices:");

        for (Invoice invoice : invoices) {
            System.out.println("Invoice ID: " + invoice.getInvoiceId()
                    + " | Amount: " + invoice.getTotalAmount()
                    + " SAR | Status: " + invoice.getStatus());
        }

        System.out.print("\nEnter invoice ID: ");
        int invoiceId = input.nextInt();
        input.nextLine();

        System.out.println("\n1. Modification Request");
        System.out.println("2. Cancellation Request");
        System.out.print("Select request type: ");

        int choice = input.nextInt();
        input.nextLine();

        String type;
        String details = "";

        if (choice == 1) {
            type = "Modification";
            System.out.print("Enter new invoice amount: ");
            details = input.nextLine();

        } else if (choice == 2) {
            type = "Cancellation";

        } else {
            System.out.println("Invalid choice");
            return;
        }

        System.out.print("Enter note (optional): ");
        String note = input.nextLine();

        String validationResult = validationService.validateRequest(invoiceId, type, details);
        System.out.println("\nValidation Result: " + validationResult);

        if ("Valid".equals(validationResult)) {
            String result = service.createRequest(
                    invoiceId, type, details, note);

            System.out.println("\nRequest Result: " + result);

            if ("Pending".equals(result)) {
                RequestFileWriter.saveRequest(
                        invoiceId, type, details, note, result);
                notificationService.notifySupervisor(requests.size(), invoiceId, type);
                System.out.println("\n--- Manage Submitted Requests ---");

                System.out.print("Enter role (Supervisor/Accountant/Employee): ");
                String role = input.nextLine();

                if (!approvalService.isAuthorized(role)) {
                    System.out.println("Not authorized");
                    return;
                }

                System.out.println("\nPending Requests:");

                for (Request request : approvalService.getPendingRequests(role)) {

                    System.out.println("Request ID: " + request.getRequestId()
                            + " | Invoice ID: " + request.getInvoiceId()
                            + " | Type: " + request.getRequestType()
                            + " | Status: " + request.getRequestStatus());
                }

                System.out.print("\nEnter request ID: ");
                int requestId = input.nextInt();
                input.nextLine();

                Request selectedRequest
                        = approvalService.getRequestDetails(requestId, role);

                if (selectedRequest == null) {
                    System.out.println("Request not found");
                    return;
                }

                System.out.println("\nRequest Details:");
                System.out.println("Invoice ID: " + selectedRequest.getInvoiceId());
                System.out.println("Type: " + selectedRequest.getRequestType());
                System.out.println("Details: " + selectedRequest.getModificationDetails());
                System.out.println("Note: " + selectedRequest.getNote());
                System.out.println("Status: " + selectedRequest.getRequestStatus());

                System.out.println("\n1. Approve Request");
                System.out.println("2. Reject Request");
                System.out.print("Select decision: ");

                int decisionChoice = input.nextInt();
                input.nextLine();

                String decision;
                String reason = "";

                if (decisionChoice == 1) {
                    decision = "Approve";

                } else if (decisionChoice == 2) {
                    decision = "Reject";

                    System.out.print("Enter rejection reason (optional): ");
                    reason = input.nextLine();

                } else {
                    System.out.println("Invalid decision");
                    return;
                }

                String decisionResult = approvalService.processDecision(
                        requestId, decision, role, reason);

                System.out.println("\nDecision Result: " + decisionResult);
                System.out.println("Request Status: " + selectedRequest.getRequestStatus());

                Invoice updatedInvoice
                        = invoiceService.findInvoice(selectedRequest.getInvoiceId());

                if (updatedInvoice != null) {
                    System.out.println("Invoice Amount: "
                            + updatedInvoice.getTotalAmount() + " SAR");

                    System.out.println("Invoice Status: "
                            + updatedInvoice.getStatus());
                }

                if ("Rejected".equals(decisionResult)) {
                    System.out.println("Rejection Reason: "
                            + selectedRequest.getRejectionReason());
                }
            }
        }
    }
}
