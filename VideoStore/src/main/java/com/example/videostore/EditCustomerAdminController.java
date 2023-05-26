package com.example.videostore;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EditCustomerAdminController implements Initializable {
    @FXML
    private Label idCheck;
    @FXML
    private Label nameCheck;
    @FXML
    private Label addressCheck;
    @FXML
    private Label phoneCheck;
    @FXML
    private Label customerTypeCheck;
    @FXML
    private Label usernameCheck;
    @FXML
    private Label passwordCheck;
    @FXML
    private Button confirm;
    @FXML
    private Button cancel;
    @FXML
    private TextField password;

    @FXML
    private TextField username;

    @FXML
    private ComboBox<String> customerType;

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField address;

    @FXML
    private TextField phone;
    private Customer editCustomer;
    static ArrayList<Customer> customerListA;
    static ArrayList<Item> itemListA;
    Pattern idPattern = Pattern.compile("^C\\d{3}$");
    Pattern phonePattern = Pattern.compile("^0\\d{9}$");
    public void receiveCustomerToEdit(Customer customer) {
        editCustomer = customer;
    }
    public void setCustomerDataA(ArrayList<Customer> customer) {
        customerListA = customer;
    }
    public void setItemDataA(ArrayList<Item> item) {
        itemListA = item;
    }
    @FXML
    private void confirm() {
        String customerID = id.getText();
        String customerName = name.getText();
        String customerAddress = address.getText();
        String customerPhone = phone.getText();
        String customerUsername = username.getText();
        String customerPassword = password.getText();

        boolean idValid = checkData(customerID,idPattern);
        boolean nameValid = (customerName != null);
        boolean addressValid = (customerAddress != null);
        boolean phoneValid = checkData(customerPhone, phonePattern);
        boolean usernameValid = (customerUsername != null);
        boolean passwordValid = (customerPassword != null);

        // Check customerID
        boolean idUnique = true;
        for (Customer customer : customerListA){
            if (!editCustomer.equals(customer) && customerID.equals(customer.getID())) {
                idUnique = false;
                break;
            }
        }

        // Check phone number is unique
        boolean phoneUnique = true;
        for (Customer customer : customerListA){
            if (!editCustomer.equals(customer) && customerPhone.equals(customer.getPhone())) {
                phoneUnique = false;
                break;
            }
        }

        // Check username is unique
        boolean usernameUnique = true;
        for (Customer customer : customerListA) {
            if (!editCustomer.equals(customer) && customerUsername.equals(customer.getUsername())) {
                usernameUnique = false;
                break;
            }
        }

        // Display validation message
        idCheck.setText(idValid ? (idUnique ? "" : "ID already exist!") : "Invalid Customer ID" + "Ex: C001");
        nameCheck.setText(nameValid ? "" : "Name cannot be empty!");
        addressCheck.setText(addressValid ? "" : "Address cannot be empty!");
        phoneCheck.setText(phoneValid ? (phoneUnique ? "" : "Phone number already exist!") : "Invalid phone number!");
        usernameCheck.setText(usernameValid ? (usernameUnique ? "" : "Username already exist!") : "Username cannot be empty");
        passwordCheck.setText(passwordValid ? "" : "Password cannot be empty!");

        if (idValid && nameValid && addressValid && phoneValid && usernameValid && passwordValid && idUnique && phoneUnique && usernameUnique ){
            Customer updatedCustomer = new Customer();
            updatedCustomer.setID(customerID);
            updatedCustomer.setName(customerName);
            updatedCustomer.setAddress(customerAddress);
            updatedCustomer.setPhone(customerPhone);
            updatedCustomer.setCustomer_type(customerType.getValue());
            updatedCustomer.setUsername(customerUsername);
            updatedCustomer.setPhone(customerPassword);
            updatedCustomer.setRent_items(editCustomer.getRent_items());

            for (int i = 0; i < customerListA.size(); i++){
                if (editCustomer.equals(customerListA.get(i))){
                    customerListA.set(i, updatedCustomer);
                    break;
                }
            }
            closeButtonAction();

        }

    }
    @FXML
    private void closeButtonAction() {
        // Get a handle to the stage
        Stage stage = (Stage) confirm.getScene().getWindow();
        // Close the stage;
        stage.close();
    }
    private boolean checkData(String id, Pattern pattern) {
        return pattern.matcher(id).find();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerType.getItems().addAll("Guest","Regular","VIP");
        Platform.runLater(() ->{
            id.setText(editCustomer.getID());
            name.setText(editCustomer.getName());
            addressCheck.setText(editCustomer.getAddress());
            phoneCheck.setText(editCustomer.getPhone());
            customerType.setValue(editCustomer.getCustomer_type());
            username.setText(editCustomer.getUsername());
            password.setText(editCustomer.getPassword());
        });
    }
}
