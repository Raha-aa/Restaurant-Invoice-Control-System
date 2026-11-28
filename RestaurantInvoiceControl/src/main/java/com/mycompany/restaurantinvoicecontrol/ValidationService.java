
package com.mycompany.restaurantinvoicecontrol;

import java.util.ArrayList;

public class ValidationService {

    private ArrayList<Invoice> invoices;

    public ValidationService(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }

    public String validateRequest(int invoiceId, String type, String details) {

        Invoice found = null;

        for (Invoice invoice : invoices) {
            if (invoice.getInvoiceId() == invoiceId) {
                found = invoice;
            }
        }

        if (found == null) {
            return "Invoice not found";
        }

        if (type == null || (!type.equals("Modification") && !type.equals("Cancellation"))) {
            return "Invalid request type";
        }

        if (found.getStatus().equals("Cancelled")) {
            return "Invoice already cancelled";
        }

        if (type.equals("Modification") && (details == null || details.trim().isEmpty())) {
            return "Modification details required";
        }

        return "Valid";
    }
}