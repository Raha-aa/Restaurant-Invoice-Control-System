package com.mycompany.restaurantinvoicecontrol;

import java.util.ArrayList;

public class InvoiceService {

    private ArrayList<Invoice> invoices;

    public InvoiceService(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Invoice findInvoice(int invoiceId) {

        for (Invoice invoice : invoices) {
            if (invoice.getInvoiceId() == invoiceId) {
                return invoice;
            }
        }

        return null;
    }

    public String applyApprovedRequest(Request request) {

        Invoice invoice = findInvoice(request.getInvoiceId());

        if (invoice == null) {
            return "Invoice not found";
        }

        if ("Cancelled".equals(invoice.getStatus())) {
            return "Invoice already cancelled";
        }

        if ("Cancellation".equals(request.getRequestType())) {

            invoice.cancelInvoice();
            return "Invoice cancelled";

        } else if ("Modification".equals(request.getRequestType())) {

            try {
                double newAmount = Double.parseDouble(
                        request.getModificationDetails().trim());

                if (!Double.isFinite(newAmount) || newAmount <= 0) {
                    return "Invalid amount";
                }

                invoice.updateInvoiceAmount(newAmount);
                return "Invoice updated";

            } catch (NumberFormatException | NullPointerException e) {
                return "Invalid modification details";
            }
        }

        return "Invalid request type";
    }
}
