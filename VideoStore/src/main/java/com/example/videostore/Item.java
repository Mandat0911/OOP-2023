package com.example.videostore;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Item {
    private String ID;
    private String Title;
    private String Rent_Type;
    private String Loan_Type;
    private int Num_of_copies;
    private float Rent_fee;
    private String Genre; // DVD, Video Record
    // Default constructor
    public Item() {}
    // Constructor
    public Item(String ID, String title, String rent_Type, String loan_Type, int num_of_copies, float rent_fee) {
        this.ID = ID;
        Title = title;
        Rent_Type = rent_Type;
        Loan_Type = loan_Type;
        Num_of_copies = num_of_copies;
        Rent_fee = rent_fee;
    }
    // Setter and Getter
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getRent_Type() {
        return Rent_Type;
    }

    public void setRent_Type(String rent_Type) {
        Rent_Type = rent_Type;
    }

    public String getLoan_Type() {
        return Loan_Type;
    }

    public void setLoan_Type(String loan_Type) {
        Loan_Type = loan_Type;
    }

    public int getNum_of_copies() {
        return Num_of_copies;
    }

    public void setNum_of_copies(int num_of_copies) {
        Num_of_copies = num_of_copies;
    }

    public float getRent_fee() {
        return Rent_fee;
    }

    public void setRent_fee(float rent_fee) {
        Rent_fee = rent_fee;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }
    public static ArrayList<Item> readItem(String fileName) throws FileNotFoundException {
        File itemFile = new File(fileName);
        ArrayList<Item> itemList = new ArrayList<>();

        try (Scanner sc = new Scanner(itemFile)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] itemData = line.split(","); // Put data from items.txt in an array.

                // Extracting item data from the line
                String id = itemData[0].trim();
                String title = itemData[1].trim();
                String director = itemData[2].trim();
                String format = itemData[3].trim();
                int numCopies = Integer.parseInt(itemData[4].trim());
                float rentalPrice = Float.parseFloat(itemData[5].trim());

                // Creating a new Item object with the extracted data
                Item newItem = new Item(id, title, director, format, numCopies, rentalPrice);

                if (itemData.length > 6) { // Check item data from items.txt file
                    String genre = itemData[6].trim(); // If the line contains genre information, extract and set it
                    newItem.setGenre(genre);
                }
                itemList.add(newItem); // Adding the new item to the itemList
            }
        }
        return itemList;
    }
    public String itemFile() {
        String text;
        if (this.getGenre() == null) { // Check if the genre is null
            // If genre is null, construct the text without including the genre
            text = this.getID().trim() + "," + this.getTitle().trim() + "," + this.getRent_Type().trim() + "," + this.getLoan_Type().trim() + "," + this.getNum_of_copies() + "," + this.getRent_fee();
        } else {
            // If genre is not null, include the genre in the text
            text = this.getID().trim() + "," + this.getTitle().trim() + "," + this.getRent_Type().trim() + "," + this.getLoan_Type().trim() + "," + this.getNum_of_copies() + "," + this.getRent_fee() + "," + this.getGenre().trim();
        }
        return text;
    }
}

