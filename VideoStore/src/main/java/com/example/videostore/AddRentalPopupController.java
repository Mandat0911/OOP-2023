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

public class AddRentalPopupController implements Initializable {
    @FXML
    private Button confirm;
    @FXML
    private Button cancel;
    @FXML
    private Label customerType;
    @FXML
    private TableView<Item> itemTableView;
    @FXML
    private TableColumn<Item, Integer> itemCopies;
    @FXML
    private TableColumn<Item, Float> itemFee;
    @FXML
    private TableColumn<Item, String> itemGenre;
    @FXML
    private TableColumn<Item, String> itemLoanType;
    @FXML
    private TableColumn<Item, String> itemRentType;
    @FXML
    private TableColumn<Item, String> itemTitle;
    @FXML
    private TableColumn<Item, String> itemID;
    static ArrayList<Item> itemListA;
    public void setItemDataA(ArrayList<Item> item) {
        itemListA = item;
    }
    private ObservableList<Item> getItems() {
        // Create an ObservableList using the FXCollections.observableArrayList() method
        // and pass in the itemListA as the initial data for the list
        return FXCollections.observableArrayList(itemListA);
    }
    @FXML
    private void refreshItem() {
        // Set cell value factories for each column
        itemID.setCellValueFactory(new PropertyValueFactory<>("ID")); // ID column
        itemTitle.setCellValueFactory(new PropertyValueFactory<>("Title")); // Title column
        itemRentType.setCellValueFactory(new PropertyValueFactory<>("Rent_Type")); // Rent Type column
        itemLoanType.setCellValueFactory(new PropertyValueFactory<>("Loan_Type")); // Loan Type column
        itemCopies.setCellValueFactory(new PropertyValueFactory<>("Num_of_copies")); // Number of copies column
        itemFee.setCellValueFactory(new PropertyValueFactory<>("Rent_fee")); // Rent fee column
        itemGenre.setCellValueFactory(new PropertyValueFactory<>("Genre")); // Genre column
        // Set the rented items of the table view
        itemTableView.setItems(getItems());
    }
    public void cancel(ActionEvent event) throws IOException {
        // Load the Customer-view.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Customer-view.fxml"));
        Parent customerView = loader.load();
        // Create a new scene with the loaded FXML file
        Scene customerViewScene = new Scene(customerView);

        // Get the current window (stage)
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Set the scene of the window to the customerViewScene
        window.setScene(customerViewScene);
        // Show the window
        window.show();
    }
    public void confirm(ActionEvent event) throws IOException {
        Item selectItem = itemTableView.getSelectionModel().getSelectedItem();
        if (selectItem!= null && selectItem.getNum_of_copies() > 0) {
            // Load the Customer-view.fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Customer-view.fxml"));
            Parent customerView = loader.load();

            // Create a new scene for the customer view
            Scene customerViewScene = new Scene(customerView);
            // Get the controller of the CustomerViewController
            CustomerViewController customerViewController = loader.getController();
            // Pass the selected item to the controller
            customerViewController.receiveRentedItem(selectItem);

            // Get the window (stage) of the current event
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Set the customer view scene in the window
            window.setScene(customerViewScene);
            window.show();
        } else if (selectItem == null) {
            // Display an error alert if no item is selected
            Alert alert = new Alert(Alert.AlertType.ERROR, "No item is selected!");
            alert.show();
        } else {
            // Display an error alert if the item is out of stock
            Alert alert = new Alert(Alert.AlertType.ERROR, "Item is out of stock!");
            alert.show();
        }
    }
    @FXML
    private void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Parent logoutView = loader.load();
        Scene logoutScene = new Scene(logoutView);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(logoutScene);
        window.setResizable(false);
        window.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Refresh the item data on the JavaFX application thread
        Platform.runLater(this::refreshItem);
    }
}
