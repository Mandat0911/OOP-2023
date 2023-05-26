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

        if (currentCustomer.checkAddDuplicateRental(rentedItem)) {
            if (currentCustomer.checkAddRental(rentedItem)) {
                if (currentCustomer.checkRentLimit()) {
                    currentCustomer.addRental(rentedItem);
                    currentRent.setText("Currently rented: " + Integer.toString(currentCustomer.getRentLimit()));
                    saveCustomerData(customerList);
                    saveItemData(itemsList);
                    updateRental();
                } else {
                    showErrorAlert("Guest accounts can only rent up to 2 items at a time");
                }
            } else {
                showErrorAlert("Guest accounts cannot rent 2-day items");
            }
        } else {
            showErrorAlert("Cannot rent 2 of the same items");
        }
    }
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.show();
    }
    public ObservableList<Item> getItems() {
        ObservableList<Item> item = FXCollections.observableArrayList();
        item.addAll(customerList.get(itr).getRent_items());
        return item;
    }
    private void updateRental() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        rentTable.setItems(getItems());
    }
    public void deleteClick() throws IOException {
        Customer currentCustomer = customerList.get(itr);

        Item selectedItem = rentTable.getSelectionModel().getSelectedItem();

        rentTable.getItems().removeAll(
                rentTable.getSelectionModel().getSelectedItems()
        );

        currentCustomer.returnRental(selectedItem);
        currentCustomer.autoPromote();

        currentRent.setText("Currently rented: " + Integer.toString(currentCustomer.getRentLimit()));
        customerType.setText(currentCustomer.getCustomer_type());

        saveCustomerData(customerList);
        saveItemData(itemsList);
    }
    public void addRental(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddRentalPopUp-view.fxml"));
        Parent addRentView = loader.load();

        Scene addRentedViewScene = new Scene(addRentView);
        AddRentalPopupController addRentalController = loader.getController();
        addRentalController.setItemDataA(itemsList);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addRentedViewScene);
        window.show();
    }
    @FXML
    private void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Parent logoutView = loader.load();
        Scene logoutScene = new Scene(logoutView);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(logoutScene);
        window.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() ->{
            idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
            nameColumn.setCellValueFactory( new PropertyValueFactory<>("Title"));
            customerType.setText(customerList.get(itr).getCustomer_type());
            currentRent.setText("Currently Rented: " + Integer.toString(customerList.get(itr).getRentLimit()));
            rentTable.setItems(getItems());
        });
    }
}
