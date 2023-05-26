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
    public void confirm(ActionEvent event) throws IOException {
        Customer c = new Customer();
        boolean idValid = false;
        boolean nameValid = false;
        boolean addressValid = false;
        boolean phoneValid = false;
        boolean usernameValid = false;
        boolean passwordValid = false;

        //Check customerID
        if(checkData(id.getText(), idPattern)){
            idCheck.setText("");
            idValid = true;
        }else {
            idCheck.setText("Invalid customer ID");
        }

        for(Customer customer:customerListA){
            if(id.getText().contains(customer.getID())){
                idValid = false;
                idCheck.setText("ID already exist");
            }
        }
        //Check name
        if(!(name.getText() == null)) {
            nameCheck.setText("");
            nameValid = true;

        }else {
            nameCheck.setText("Name cannot be empty");
        }
        //Check address
        if(!(address.getText() == null)) {
            addressCheck.setText("");
            addressValid = true;

        }else {
            addressCheck.setText("Address cannot be empty");
        }
        //Check Phone
        if(checkData(phone.getText(), phonePattern)){
            phoneCheck.setText("");
            phoneValid = true;
        }else {
            phoneCheck.setText("Invalid phone number");
        }

        for(Customer customer:customerListA){
            if(phone.getText().contains(customer.getPhone())){
                phoneValid = false;
                phoneCheck.setText("Phone number already exist");
            }
        }
        //Check username
        if(!(username.getText() == null)) {
            usernameCheck.setText("");
            usernameValid = true;

        }else {
            usernameCheck.setText("Username cannot be empty");
        }

        for(Customer customer:customerListA){
            if(username.getText().contains(customer.getUsername())){
                usernameValid = false;
                usernameCheck.setText("Username already exist");
            }
        }
        //Check password
        if(!(password.getText() == null)) {
            passwordCheck.setText("");
            passwordValid = true;

        }else {
            passwordCheck.setText("Password cannot be empty");
        }

        if(idValid && nameValid && addressValid && phoneValid && usernameValid && passwordValid){
            c.setID(id.getText());
            c.setName(name.getText());
            c.setAddress(address.getText());
            c.setPhone(phone.getText());
            c.setCustomer_type(customerType.getValue());
            c.setUsername(username.getText());
            c.setPassword(password.getText());
            customerListA.add(c);
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
        customerType.getItems().addAll(
                "Guest",
                "Regular",
                "VIP");
    }
}
