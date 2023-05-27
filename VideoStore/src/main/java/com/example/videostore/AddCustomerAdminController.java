package com.example.videostore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static com.example.videostore.Save_Data.saveCustomerData;

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
        boolean idValid = checkData(id.getText(), idPattern);
        boolean nameValid = name.getText() != null;
        boolean addressValid = address.getText() != null;
        boolean phoneValid = checkData(phone.getText(), phonePattern);
        boolean usernameValid = username.getText() != null;
        boolean passwordValid = password.getText() != null;

        if (idValid && nameValid && addressValid && phoneValid && usernameValid && passwordValid) {
            Customer c = new Customer();
            c.setID(id.getText());
            c.setName(name.getText());
            c.setAddress(address.getText());
            c.setPhone(phone.getText());
            c.setCustomer_type(customerType.getValue());
            c.setUsername(username.getText());
            c.setPassword(password.getText());
            customerListA.add(c);
            closeButtonAction();
        } else {
            if (!idValid) {
                idCheck.setText("Invalid customer ID");
            } else {
                for (Customer customer : customerListA) {
                    if (id.getText().contains(customer.getID())) {
                        idCheck.setText("ID already exists");
                        break;
                    }
                }
            }

            if (!nameValid) {
                nameCheck.setText("Name cannot be empty");
            }

            if (!addressValid) {
                addressCheck.setText("Address cannot be empty");
            }

            if (!phoneValid) {
                phoneCheck.setText("Invalid phone number");
            } else {
                for (Customer customer : customerListA) {
                    if (phone.getText().contains(customer.getPhone())) {
                        phoneCheck.setText("Phone number already exists");
                        break;
                    }
                }
            }

            if (!usernameValid) {
                usernameCheck.setText("Username cannot be empty");
            } else {
                for (Customer customer : customerListA) {
                    if (username.getText().contains(customer.getUsername())) {
                        usernameCheck.setText("Username already exists");
                        break;
                    }
                }
            }

            if (!passwordValid) {
                passwordCheck.setText("Password cannot be empty");
            }
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
        customerType.getItems().addAll(
                "Guest",
                "Regular",
                "VIP");
    }
}
