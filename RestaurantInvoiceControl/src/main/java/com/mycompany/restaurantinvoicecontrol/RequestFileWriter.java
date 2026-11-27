package com.mycompany.restaurantinvoicecontrol;

import java.io.FileWriter;
import java.io.IOException;

public class RequestFileWriter {

    public static void saveRequest(int invoiceId, String type,
            String details, String note, String status) {

        try (FileWriter writer = new FileWriter("requests.txt", true)) {

            writer.write("Invoice ID: " + invoiceId + "\n");
            writer.write("Request Type: " + type + "\n");
            writer.write("Modification Details: " + details + "\n");
            writer.write("Note: " + note + "\n");
            writer.write("Status: " + status + "\n");
            writer.write("------------------------------\n");

        } catch (IOException e) {
            System.out.println("Error saving request to file.");
        }
    }
}