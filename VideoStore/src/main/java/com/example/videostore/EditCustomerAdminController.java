package com.example.videostore;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    Pattern idPattern = Pattern.compile("^C\\d{3}$"); // ID Regex
    Pattern phonePattern = Pattern.compile("^0\\d{9}$"); // Phone Regex

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
    private void confirm() throws IOException {
        boolean isValid = true;
        Alert alert = new Alert(Alert.AlertType.WARNING);
        // Validate ID
        if (checkData(id.getText(), idPattern)) {
            idCheck.setText("Invalid customer ID!");
            isValid = false;
        } else if (!editCustomer.getID().equals(id.getText()) && customerListA.stream().anyMatch(customer -> customer.getID().startsWith(id.getText()))) {
            idCheck.setText("Customer ID already exists!");
            isValid = false;
        } else {
            idCheck.setText("");
        }
        // Validate Name
        if (name.getText().trim().isEmpty()) {
            nameCheck.setText("Name cannot be empty!");
            isValid = false;
        }else {
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
        } else if (!editCustomer.getPhone().equals(phone.getText()) && customerListA.stream().anyMatch(customer -> customer.getPhone().startsWith(phone.getText()))) {
            phoneCheck.setText("Phone number already exists");
            isValid = false;
        } else {
            phoneCheck.setText("");
        }
        // Validate Username
        if (username.getText().trim().isEmpty()) {
            usernameCheck.setText("Username cannot be empty!");
            isValid = false;
        } else if (!editCustomer.getUsername().equals(username.getText()) && customerListA.stream().anyMatch(customer -> customer.getUsername().startsWith(username.getText()))) {
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
            Customer updatedCustomer = new Customer();
            updatedCustomer.setID(id.getText());
            updatedCustomer.setName(name.getText());
            updatedCustomer.setAddress(address.getText());
            updatedCustomer.setPhone(phone.getText());
            updatedCustomer.setCustomer_type(customerType.getValue());
            updatedCustomer.setUsername(username.getText());
            updatedCustomer.setPassword(password.getText());
            updatedCustomer.setRent_items(editCustomer.getRent_items());
            // Update customerListA
            for (int i = 0; i < customerListA.size(); i++) {
                if (editCustomer.equals(customerListA.get(i))) {
                    customerListA.set(i, updatedCustomer); // Replace i as updatedCustomer
                    break;
                }
            }
            closeButtonAction();
        } else {
            // Pop up alert window
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
    // Check Data
    private boolean checkData(String id, Pattern pattern) {
        return !pattern.matcher(id).find();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerType.getItems().addAll("Guest","Regular","VIP");
        // Set the initial values of the input fields to the values of the editCustomer object
        Platform.runLater(() ->{
            id.setText(editCustomer.getID());
            name.setText(editCustomer.getName());
            address.setText(editCustomer.getAddress());
            phone.setText(editCustomer.getPhone());
            customerType.setValue(editCustomer.getCustomer_type());
            username.setText(editCustomer.getUsername());
            password.setText(editCustomer.getPassword());
        });
    }
}
