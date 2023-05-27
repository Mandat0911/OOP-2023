package com.example.videostore;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.videostore.Save_Data.saveCustomerData;
import static com.example.videostore.Save_Data.saveItemData;

public class CustomerViewController implements Initializable {
    @FXML
    private Button addItem;
    @FXML
    private Button returnItem;
    @FXML
    private Button updateList;
    @FXML
    private Label customerType;
    @FXML
    private Label currentRent;
    @FXML
    private TableColumn<Item, String> idColumn;
    @FXML
    private TableColumn<Item, String> nameColumn;
    @FXML
    private TableView<Item> rentTable;

    static ArrayList<Customer> customerList;
    static ArrayList<Item> itemsList;
    static int itr;
    public void setCustomerData(ArrayList<Customer> customer, int index) {
        customerList = customer;
        itr = index;
    }
    public void setItemData(ArrayList<Item> item) {
        itemsList = item;
    }
    public void receiveRentedItem(Item rentedItem) throws IOException {
        Customer currentCustomer = customerList.get(itr);
        // Check if adding the rented item would result in duplicate rentals
        if (currentCustomer.checkAddDuplicateRental(rentedItem)) {
            // Check if adding the rented item is allowed based on rental type restrictions
            if (currentCustomer.checkAddRental(rentedItem)) {
                // Check if the customer has reached the maximum rental limit
                if (currentCustomer.checkRentLimit()) {
                    // Add the rented item to the customer's rentals
                    currentCustomer.addRental(rentedItem);
                    currentRent.setText("Currently rented: " + Integer.toString(currentCustomer.getRentLimit()));
                    saveCustomerData(customerList);
                    saveItemData(itemsList);
                    updateRental();
                } else {
                    // Show an error alert if the guest account has reached the maximum rental limit
                    showErrorAlert("Guest accounts can only rent up to 2 items at a time");
                }
            } else {
                // Show an error alert if the guest account is trying to rent a 2-day item
                showErrorAlert("Guest accounts cannot rent 2-day items");
            }
        } else {
            // Show an error alert if the customer is trying to rent two of the same items
            showErrorAlert("Cannot rent 2 of the same items");
        }
    }
    // Show alert
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.show();
    }
    public ObservableList<Item> getItems() {
        // Create a new ObservableList to hold the items
        ObservableList<Item> item = FXCollections.observableArrayList();
        // Add all the rent items from the customer to the list
        item.addAll(customerList.get(itr).getRent_items());
        // Return the list of items
        return item;
    }
    private void updateRental() {
        // Set the cell value factories for the columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        // Set the items of the rental table
        rentTable.setItems(getItems());
    }
    public void deleteClick() throws IOException {
        // Get the current customer
        Customer currentCustomer = customerList.get(itr);
        // Get the selected item from the rentTable
        Item selectedItem = rentTable.getSelectionModel().getSelectedItem();
        // Remove the selected item from the rentTable
        rentTable.getItems().removeAll(rentTable.getSelectionModel().getSelectedItems());
        // Return the rental item for the current customer
        currentCustomer.returnRental(selectedItem);
        // Automatically promote the current customer if applicable
        currentCustomer.autoPromote();
        // Update the UI with the current rental limit and customer type
        currentRent.setText("Currently rented: " + Integer.toString(currentCustomer.getRentLimit()));
        customerType.setText(currentCustomer.getCustomer_type());
        // Save the updated customer and item data
        saveCustomerData(customerList);
        saveItemData(itemsList);
    }
    public void addRental(ActionEvent event) throws IOException {
        // Load the FXML file for the AddRentalPopUp view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddRentalPopUp-view.fxml"));
        Parent addRentView = loader.load();

        // Create a new scene with the AddRentalPopUp view
        Scene addRentedViewScene = new Scene(addRentView);

        // Get the controller instance from the loader
        AddRentalPopupController addRentalController = loader.getController();

        // Pass the item data to the controller
        addRentalController.setItemDataA(itemsList);

        // Get the window/stage from the event source
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Get the window/stage from the event source
        window.setScene(addRentedViewScene);
        window.show();
    }
    @FXML
    private void logout(ActionEvent event) throws IOException {
        // Load the login view FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Parent logoutView = loader.load();

        // Create a new scene with the login view
        Scene logoutScene = new Scene(logoutView);

        // Get the current window (stage) from the event source
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene of the window to the login scene
        window.setScene(logoutScene);

        // Show the window with the login view
        window.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->{
            // Set cell value factories for table columns
            idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
            nameColumn.setCellValueFactory( new PropertyValueFactory<>("Title"));

            // Set customer type and rent limit labels
            customerType.setText(customerList.get(itr).getCustomer_type());
            currentRent.setText("Currently Rented: " + Integer.toString(customerList.get(itr).getRentLimit()));

            // Set items for rent table
            rentTable.setItems(getItems());
        });
    }
}
