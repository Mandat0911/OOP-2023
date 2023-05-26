package com.example.videostore;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer {
    private String ID;
    private String Name;
    private String Address;
    private String Phone;
    private int Number_of_rentals;
    private String Customer_type;
    private String Username;
    private String Password;
    private ArrayList<Item> Rent_items = new ArrayList<>();
    private int RentLimit = 0;
    private int ItemReturn = 0;
    private int RewardPoint = 0;
    public Customer() {}

    public Customer(String ID, String name, String address, String phone, int number_of_rentals, String customer_type, String username, String password) {
        this.ID = ID;
        Name = name;
        Address = address;
        Phone = phone;
        Number_of_rentals = number_of_rentals;
        Customer_type = customer_type;
        Username = username;
        Password = password;
    }
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getNumber_of_rentals() {
        return Number_of_rentals;
    }

    public void setNumber_of_rentals(int number_of_rentals) {
        Number_of_rentals = number_of_rentals;
    }

    public String getCustomer_type() {
        return Customer_type;
    }

    public void setCustomer_type(String customer_type) {
        Customer_type = customer_type;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public ArrayList<Item> getRent_items() {
        return Rent_items;
    }

    public void setRent_items(ArrayList<Item> rent_items) {
        Rent_items = rent_items;
    }

    public int getRentLimit() {
        return RentLimit;
    }

    public void setRentLimit(int rentLimit) {
        RentLimit = rentLimit;
    }

    public int getItemReturn() {
        return ItemReturn;
    }

    public void setItemReturn(int itemReturn) {
        ItemReturn = itemReturn;
    }

    public int getRewardPoint() {
        return RewardPoint;
    }

    public void setRewardPoint(int rewardPoint) {
        RewardPoint = rewardPoint;
    }
    public static ArrayList<Customer> readCustomers(String fileName, ArrayList<Item> itemList) throws FileNotFoundException {
        int customerPosition = -1;
        File customerFile = new File(fileName);
        Scanner sc = new Scanner(customerFile);
        ArrayList<Customer> customerList = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] database = line.split(",");

            if (database[0].startsWith("C")) {
                Customer newCustomer = new Customer(database[0].trim(), database[1].trim(), database[2].trim(), database[3].trim(),
                        Integer.parseInt(database[4].trim()), database[5].trim(), database[6].trim(), database[7].trim());
                customerList.add(newCustomer);
                customerPosition++;
            } else if (database[0].startsWith("I")) {
                for (Item item : itemList) {
                    if (item.getID().contains(database[0])) {
                        customerList.get(customerPosition).addRental(item);
                    }
                }
            }
        }
        for (Customer customer : customerList) {
            customer.setRentLimit(customer.getRent_items().size());
        }
        return customerList;
    }
    public String customerFile() {
        String text;
        text = this.getID() + "," + this.getName() + "," + this.getAddress() + "," + this.getPhone() + "," + this.getNumber_of_rentals() + "," + this.getCustomer_type() + "," + this.getUsername() + "," + this.getPassword() + "," ;
        return text;
    }
    public void addRental(Item item) {
        if (item.getNum_of_copies() > 0) {
            if (this.Rent_items.contains(item)) {
                System.out.println("Item is already rented by this customer.");
            }

            this.Rent_items.add(item);
            this.setRentLimit(this.getRentLimit() + 1);
            item.setNum_of_copies(item.getNum_of_copies() - 1);
        } else {
            System.out.println("Out of Stock");
        }
    }
    public void returnRental(Item item) {
        if (this.Rent_items.contains(item)) {
            this.Rent_items.remove(item);
            item.setNum_of_copies(item.getNum_of_copies() + 1);
            this.setItemReturn(this.getItemReturn() + 1);
            this.setRentLimit(this.getRentLimit() - 1);
            this.setRewardPoint(this.getRewardPoint() + 5);
            System.out.println("Item removed successfully.");
        } else {
            System.out.println("You have not rented any items yet.");
        }
    }
    public String printRental() {
        StringBuilder sb = new StringBuilder();

        for (Item item : Rent_items) {
            sb.append(item.getID()).append(" ").append(item.getTitle()).append("\n");
        }

        return sb.toString();
    }

    public void autoPromote(){
        if (this.getCustomer_type().contentEquals("Guest") && this.getItemReturn() > 3){
            this.setCustomer_type("Regular");
            this.setItemReturn(0);
        }
        if (this.getCustomer_type().contentEquals("Regular") && this.getItemReturn() > 3){
            this.setCustomer_type("VIP");
            this.setItemReturn(0);
        }
    }
    public void promoteCustomer() {
        if (this.getCustomer_type().equals("Guest")) {
            this.setCustomer_type("Regular");
        }else if (this.getCustomer_type().equals("Regular")) {
            this.setCustomer_type("VIP");
        }
    }
    public void freeRental(){
        if (this.getCustomer_type().contentEquals("VIP") && this.getRewardPoint() == 100){
            System.out.println("You currently have one complimentary rental available");
        }
    }
    public boolean checkAddRental(Item item){
        return !(this.getCustomer_type().contentEquals("Guest") && item.getLoan_Type().contentEquals("2-day"));
    }
    public boolean checkAddDuplicateRental(Item item){
        return !(this.getRent_items().contains(item));
    }
    public boolean checkRentLimit(){
        return !(this.getCustomer_type().contentEquals("Guest") && this.getRentLimit() == 2);
    }




}
