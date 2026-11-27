package com.mycompany.restaurantinvoicecontrol;

import java.util.ArrayList;

public class RequestService {

    private ArrayList<Invoice> invoices;
    private ArrayList<Request> requests;
    private int nextRequestId = 1;

    public RequestService(ArrayList<Invoice> invoices,
                          ArrayList<Request> requests) {
        this.invoices = invoices;
        this.requests = requests;
    }

    public String createRequest(int invoiceId, String type,
                                String details, String note) {

        for (Invoice invoice : invoices) {

            if (invoice.getInvoiceId() == invoiceId) {

                Request request = new Request(
                        nextRequestId++, invoiceId,
                        type, details, note);

                requests.add(request);

                return request.getRequestStatus();
            }
        }

        return "Invoice not found";
    }
}