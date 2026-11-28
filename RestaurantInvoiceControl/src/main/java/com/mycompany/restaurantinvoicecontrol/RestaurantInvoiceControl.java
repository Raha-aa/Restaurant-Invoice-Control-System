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
            System.out.print("Enter modification details: ");
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
            }
        }
    }
}
