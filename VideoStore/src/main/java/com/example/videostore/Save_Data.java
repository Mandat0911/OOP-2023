package com.example.videostore;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Save_Data {
    public static void saveCustomerData(ArrayList<Customer> customers) throws IOException {
        // This code saves the customer data, including their details and the IDs of the items they have rented,
        // to a text file named "customers.txt".
        File file = new File("VideoStore/doc/customers.txt"); // Specify the file path
        FileWriter fw = new FileWriter(file); // Create a FileWriter to write to the file
        PrintWriter pw = new PrintWriter(fw);// Create a PrintWriter to write formatted representations of objects to the file

        for (Customer customer : customers) {
            pw.println(customer.customerFile()); // Write customer details to the file
            for (Item item : customer.getRent_items()) {
                pw.println(item.getID()); // Write rented item IDs to the file
            }
        }
        pw.close(); // Close the PrintWriter
    }
    // This code saves the item data, including their details to a text file named "items.txt".
    public static void saveItemData(ArrayList<Item> item) throws IOException {
        File file = new File("VideoStore/doc/items.txt");
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);

        // Iterate over the items and write each item's file representation to the file.
        for (Item value : item) {
            pw.println(value.itemFile());
        }
        pw.close();// Close the PrintWriter
    }
}

