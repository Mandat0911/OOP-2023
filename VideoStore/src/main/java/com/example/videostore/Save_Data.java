package com.example.videostore;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Save_Data {
    public static void saveCustomerData(ArrayList<Customer> customers) throws IOException {
        File file = new File("VideoStore/doc/customers.txt");
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);

        for (Customer customer : customers) {
            pw.println(customer.customerFile());
            for (Item item : customer.getRent_items()) {
                pw.println(item.getID());
            }
        }
        pw.close();
    }

    public static void saveItemData(ArrayList<Item> item) throws IOException {
        File file = new File("VideoStore/doc/items.txt");
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);

        for (Item value : item) {
            pw.println(value.itemFile());

        }
        pw.close();
    }
}

