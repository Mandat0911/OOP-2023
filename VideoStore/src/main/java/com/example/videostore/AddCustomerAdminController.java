package com.example.videostore;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddCustomerAdminController implements Initializable {
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
    static ArrayList<Item> itemsListA;
    static ArrayList<Customer> customerListA;
    Pattern idPattern = Pattern.compile("^C\\d{3}$");
    Pattern phonePattern = Pattern.compile("^0\\d{9}$");
    public void setItemDataA(ArrayList<Item> item){
        itemsListA = item;
    }
    public void setCustomerDataA(ArrayList<Customer> customer){
        customerListA = customer;
    }
    @FXML
    public void confirm() throws IOException {
        boolean isValid = true;
        Alert alert = new Alert(Alert.AlertType.WARNING);

        // Validate ID
        if (checkData(id.getText(), idPattern)) {
            idCheck.setText("Invalid customer ID!");
            isValid = false;
        } else if (customerListA.stream().anyMatch(customer -> customer.getID().startsWith(id.getText()))) {
            idCheck.setText("Customer ID already exist!");
            isValid = false;
        } else {
            idCheck.setText("");
        }

        // Validate Name
        if (name.getText().trim().isEmpty()) {
            nameCheck.setText("Name cannot be empty!");
            isValid = false;
        } else {
            nameCheck.setText("");
        }

        // Validate Address
        if (address.getText().trim().isEmpty()) {
            addressCheck.setText("Address cannot be empty!");
            isValid = false;
        } else {
            addressCheck.setText("");
        }

        // Validate Phone Number
        if (checkData(phone.getText(), phonePattern)) {
            phoneCheck.setText("Invalid phone number!");
            isValid = false;
        } else if (customerListA.stream().anyMatch(customer -> customer.getPhone().startsWith(phone.getText()))) {
            phoneCheck.setText("Phone number already exists");
            isValid = false;
        } else {
            phoneCheck.setText("");
        }

        // Validate Username
        if(username.getText().trim().isEmpty()) {
            usernameCheck.setText("Username cannot be empty!");
            isValid = false;
        } else if (customerListA.stream().anyMatch(customer -> customer.getUsername().startsWith(username.getText()))) {
            usernameCheck.setText("Username already exists");
            isValid = false;
        } else {
            usernameCheck.setText("");
        }

        // Validate Password
        if (password.getText().trim().isEmpty()) {
            passwordCheck.setText("Password cannot be empty!");
            isValid = false;
        }else {
            passwordCheck.setText("");
        }
        if (isValid) {
            // Create a new Customer object and set its properties
            Customer newCustomer = new Customer();
            newCustomer.setID(id.getText());
            newCustomer.setName(name.getText());
            newCustomer.setAddress(address.getText());
            newCustomer.setPhone(phone.getText());
            newCustomer.setCustomer_type(customerType.getValue());
            newCustomer.setUsername(username.getText());
            newCustomer.setPassword(password.getText());
            // Add the new customer to the list
            customerListA.add(newCustomer);
            closeButtonAction();
        } else {
            // Display validation error message
            alert.setTitle("Validation Error");
            alert.setHeaderText("Please fix the errors and try again.");
            alert.setContentText("There are validation errors in the form.");
            alert.showAndWait();
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
        return !pattern.matcher(id).find();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Add options to customerType dropdown
        customerType.getItems().addAll(
                "Guest",
                "Regular",
                "VIP");
    }
}
