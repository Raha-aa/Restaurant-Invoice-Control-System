package com.mycompany.restaurantinvoicecontrol;

public class Request {

    private int requestId;
    private int invoiceId;
    private String requestType;
    private String modificationDetails;
    private String note;
    private String status;
    private String rejectionReason;

    public Request(int requestId, int invoiceId, String requestType,
                   String modificationDetails, String note) {

        this.requestId = requestId;
        this.invoiceId = invoiceId;
        this.requestType = requestType;
        this.modificationDetails = modificationDetails;
        this.note = note;
        this.status = "Pending";
        this.rejectionReason = "";
    }

    public int getRequestId() {
        return requestId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getModificationDetails() {
        return modificationDetails;
    }

    public String getNote() {
        return note;
    }

    public String getRequestStatus() {
        return status;
    }

    public void updateRequestStatus(String status) {
        this.status = status;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}