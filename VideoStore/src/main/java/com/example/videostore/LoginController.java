package com.example.videostore;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.util.ArrayList;
import java.util.ResourceBundle;
import static com.example.videostore.Customer.readCustomers;
import static com.example.videostore.Item.readItem;

public class LoginController implements Initializable {
    @FXML
    private Button login;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Label alert;
    @FXML
    private void checkLogin(ActionEvent event) throws IOException {
        String usernameInput = username.getText();
        String passwordInput = password.getText();
        ArrayList<Item> itemList = readItem("VideoStore/doc/items.txt");
        ArrayList<Customer> customerList = readCustomers("VideoStore/doc/customers.txt", itemList);
        if (usernameInput.isEmpty() || passwordInput.isEmpty()){
            alert.setText("Please Enter both Username and Password!");
            return;
        }
        if (usernameInput.equals("admin") && passwordInput.equals("admin")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin-view.fxml"));
            Parent adminView = loader.load();
            Scene adminViewScene = new Scene(adminView);

            AdminViewController adminViewController = loader.getController();
            adminViewController.setCustomerDataA(customerList);
            adminViewController.setItemDataA(itemList);

            Stage adminWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
            adminWindow.setScene(adminViewScene);
            adminWindow.show();
            alert.setStyle("-fx-font-style: italic;");
            alert.setText("Admin login successful!");
        }
        for (Customer customer : customerList) {
            if (usernameInput.equals(customer.getUsername())){
                if (passwordInput.equals(customer.getPassword())){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Customer-view.fxml"));
                    Parent customerView = loader.load();
                    Scene customerViewScene = new Scene(customerView);

                    CustomerViewController customerViewController = loader.getController();
                    customerViewController.setCustomerData(customerList, customerList.indexOf(customer));
                    customerViewController.setItemData(itemList);

                    Stage customerWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    customerWindow.setScene(customerViewScene);
                    customerWindow.show();

                    alert.setText("Login successful!");
                    break;
                }else {
                    alert.setStyle("-fx-font-style: italic;");
                    alert.setText("Incorrect username or password!");
                    return;
                }
            }
        }
    }
    //
    @FXML
    private void onEnterPress(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            String usernameInput = username.getText();
            String passwordInput = password.getText();
            ArrayList<Item> itemList = readItem("VideoStore/doc/items.txt");
            ArrayList<Customer> customerList = readCustomers("VideoStore/doc/customers.txt", itemList);

            if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
                alert.setText("Please enter both Username and Password!");
                return;
            }
            if (usernameInput.equals("admin") && passwordInput.equals("admin")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin-view.fxml"));
                Parent adminView = loader.load();
                Scene adminViewScene = new Scene(adminView);

                AdminViewController adminViewController = loader.getController();
                adminViewController.setCustomerDataA(customerList);
                adminViewController.setItemDataA(itemList);

                Stage adminWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
                adminWindow.setScene(adminViewScene);
                adminWindow.show();

                alert.setStyle("-fx-font-style: italic;");
                alert.setText("Admin login successful!");
                return;
            }
            for (Customer customer : customerList) {
                if (usernameInput.equals(customer.getUsername())) {
                    if (passwordInput.equals(customer.getPassword())) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Customer-view.fxml"));
                        Parent customerView = loader.load();
                        Scene customerViewScene = new Scene(customerView);

                        CustomerViewController customerViewController = loader.getController();
                        customerViewController.setCustomerData(customerList, customerList.indexOf(customer));
                        customerViewController.setItemData(itemList);

                        Stage customerWindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        customerWindow.setScene(customerViewScene);
                        customerWindow.show();

                        alert.setText("Login successful!");
                        return;
                    } else {
                        alert.setStyle("-fx-font-style: italic;");
                        alert.setText("Incorrect username or password!");
                        return;
                    }
                }
            }
            alert.setStyle("-fx-font-style: italic;");
            alert.setText("User does not exist!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTextField(username);
        setupTextField(password);
    }
    private void setupTextField(TextField textField) {
        textField.setStyle("-fx-text-inner-color: rgb(246,231,87);");
        textField.setBackground(Background.fill(Color.TRANSPARENT));
        textField.setBorder(new Border(new BorderStroke(Color.rgb(246,231,87), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 2, 0))));
    }
}
