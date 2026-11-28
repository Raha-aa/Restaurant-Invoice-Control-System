package com.mycompany.restaurantinvoicecontrol;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class NotificationServiceTest {

    private NotificationService notificationService;

    @Before
    public void setUp() {
        notificationService = new NotificationService();
    }

    @Test
    public void testValidNotification() {
        String result = notificationService.notifySupervisor(1, 101, "Modification");
        assertEquals("Notification sent", result);
    }

    @Test
    public void testInvalidRequestId() {
        String result = notificationService.notifySupervisor(0, 101, "Modification");
        assertEquals("Notification failed: Invalid ID", result);
    }

    @Test
    public void testMissingRequestType() {
        String result = notificationService.notifySupervisor(1, 101, "");
        assertEquals("Notification failed: Request type missing", result);
    }
}