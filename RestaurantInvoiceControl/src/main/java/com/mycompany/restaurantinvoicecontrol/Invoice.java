package com.mycompany.restaurantinvoicecontrol;

public class Invoice {

    private int invoiceId;
    private double totalAmount;
    private String status;

    public Invoice(int invoiceId, double totalAmount, String status) {
        this.invoiceId = invoiceId;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void updateInvoiceStatus(String status) {
        this.status = status;
    }

    public void cancelInvoice() {
        this.status = "Cancelled";
    }

    public void updateInvoiceAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
