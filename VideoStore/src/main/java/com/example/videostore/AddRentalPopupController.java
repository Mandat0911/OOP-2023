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
    static ArrayList<Customer> customerList;
    static int itr;
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
        itemID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        itemTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        itemRentType.setCellValueFactory(new PropertyValueFactory<>("Rent_Type"));
        itemLoanType.setCellValueFactory(new PropertyValueFactory<>("Loan_Type"));
        itemCopies.setCellValueFactory(new PropertyValueFactory<>("Num_of_copies"));
        itemFee.setCellValueFactory(new PropertyValueFactory<>("Rent_fee"));
        itemGenre.setCellValueFactory(new PropertyValueFactory<>("Genre"));
        itemTableView.setItems(getItems());
    }
    public void cancel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Customer-view.fxml"));
        Parent customerView = loader.load();
        Scene customerViewScene = new Scene(customerView);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(customerViewScene);
        window.show();
    }
    public void confirm(ActionEvent event) throws IOException {
        Item selectItem = itemTableView.getSelectionModel().getSelectedItem();
        if (selectItem.getNum_of_copies() > 0) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Customer-view.fxml"));
            Parent customerView = loader.load();

            Scene customerViewScene = new Scene(customerView);
            CustomerViewController customerViewController = loader.getController();
            customerViewController.receiveRentedItem(selectItem);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(customerViewScene);
            window.show();
        } else {
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
        Platform.runLater(this::refreshItem);
    }
}
