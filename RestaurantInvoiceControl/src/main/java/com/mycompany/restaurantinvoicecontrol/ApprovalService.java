package com.mycompany.restaurantinvoicecontrol;

import java.util.ArrayList;

public class ApprovalService {

    private ArrayList<Request> requests;
    private InvoiceService invoiceService;

    public ApprovalService(ArrayList<Request> requests,
            InvoiceService invoiceService) {
        this.requests = requests;
        this.invoiceService = invoiceService;
    }

    public boolean isAuthorized(String role) {
        return "Supervisor".equals(role)
                || "Accountant".equals(role);
    }

    public Request findRequest(int requestId) {

        for (Request request : requests) {
            if (request.getRequestId() == requestId) {
                return request;
            }
        }

        return null;
    }

    public ArrayList<Request> getPendingRequests(String role) {

        ArrayList<Request> pendingRequests = new ArrayList<>();

        if (!isAuthorized(role)) {
            return pendingRequests;
        }

        for (Request request : requests) {
            if ("Pending".equals(request.getRequestStatus())) {
                pendingRequests.add(request);
            }
        }

        return pendingRequests;
    }

    public Request getRequestDetails(int requestId, String role) {

        if (!isAuthorized(role)) {
            return null;
        }

        return findRequest(requestId);
    }

    public String processDecision(int requestId, String decision,
            String role, String reason) {

        if (!isAuthorized(role)) {
            return "Not authorized";
        }

        Request request = findRequest(requestId);

        if (request == null) {
            return "Request not found";
        }

        if (!"Pending".equals(request.getRequestStatus())) {
            return "Request already processed";
        }

        if ("Approve".equals(decision)) {

            String result = invoiceService.applyApprovedRequest(request);

            if ("Invoice updated".equals(result)
                    || "Invoice cancelled".equals(result)) {

                request.updateRequestStatus("Approved");
                return "Approved";
            }

            return result;

        } else if ("Reject".equals(decision)) {

            request.updateRequestStatus("Rejected");
            request.setRejectionReason(reason == null ? "" : reason);

            return "Rejected";
        }

        return "Invalid decision";
    }
}
