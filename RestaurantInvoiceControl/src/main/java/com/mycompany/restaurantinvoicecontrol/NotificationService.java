package com.mycompany.restaurantinvoicecontrol;

public class NotificationService {

    public String notifySupervisor(int requestId, int invoiceId, String type) {
        if (requestId <= 0 || invoiceId <= 0) {
            return "Notification failed: Invalid ID";
        }
        if (type == null || type.trim().isEmpty()) {
            return "Notification failed: Request type missing";
        }

        String notificationMessage = "Notification sent to Supervisor: New " + type
                + " request (Request ID: " + requestId + ") for Invoice ID: "
                + invoiceId + " is Pending.";
        System.out.println(notificationMessage);
        return "Notification sent";
    }
}