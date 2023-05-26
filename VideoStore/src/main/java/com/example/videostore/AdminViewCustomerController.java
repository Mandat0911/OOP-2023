package com.example.videostore;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.videostore.Save_Data.saveCustomerData;
import static com.example.videostore.Save_Data.saveItemData;

public class AdminViewCustomerController implements Initializable {
    @FXML
    public Button addNewCustomer;
    @FXML
    private Button edit;
    @FXML
    private Button delete;
    @FXML
    private TableColumn<Customer, String> id;
    @FXML
    private TableColumn<Customer, String> customerType;
    @FXML
    private TableColumn<Customer, String> address;
    @FXML
    private TableColumn<Customer, Integer> phone;
    @FXML
    private TableColumn<Customer, String> name;
    @FXML
    private TableColumn<Customer, String> username;
    @FXML
    private TableColumn<Customer, String> password;
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> searchBox;
    @FXML
    private ComboBox<String> customerGroup;

    static ArrayList<Customer> customerListA;
    static ArrayList<Item> itemListA;
    public void setCustomerDataA(ArrayList<Customer> customer) {
        customerListA = customer;
    }
    public void setItemDataA(ArrayList<Item> item) {
        itemListA = item;
    }
    private ObservableList<Customer> getCustomer() {
        ObservableList<Customer> customer = FXCollections.observableArrayList();
        customer.addAll(customerListA);
        return customer;
    }
    private void refreshCustomer() {
        id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        phone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        customerType.setCellValueFactory(new PropertyValueFactory<>("Customer_type"));
        username.setCellValueFactory(new PropertyValueFactory<>("Username"));
        password.setCellValueFactory(new PropertyValueFactory<>("Password"));
        customerTableView.setItems(getCustomer());
    }
    @FXML
    private void addCustomer() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddCustomer-view.fxml"));
        DialogPane addCustomer = fxmlLoader.load();

        AddCustomerAdminController addCustomerAdminController = fxmlLoader.getController();
        addCustomerAdminController.setCustomerDataA(customerListA);
        addCustomerAdminController.setItemDataA(itemListA);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(addCustomer);
        dialog.setTitle("Add Customer");

        Optional<ButtonType> clickedButton = dialog.showAndWait();
        refreshCustomer();
        saveCustomerData(customerListA);
    }
    @FXML
    private void editCustomer() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditCustomer-view.fxml"));
        DialogPane editCustomer = fxmlLoader.load();

        EditCustomerAdminController editCustomerAdminController = fxmlLoader.getController();
        editCustomerAdminController.setCustomerDataA(customerListA);
        editCustomerAdminController.setItemDataA(itemListA);
        editCustomerAdminController.receiveCustomerToEdit(customerTableView.getSelectionModel().getSelectedItem());

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(editCustomer);
        dialog.setTitle("Edit Customer");

        Optional<ButtonType> clickedButton = dialog.showAndWait();
            refreshCustomer();
            saveCustomerData(customerListA);
            showSuccessAlert("Edit Successful","Customer Edited Successfully!");
    }
    @FXML
    private void deleteCustomer() throws IOException {
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        customerListA.removeIf(customer -> customer.equals(selectedCustomer));
        refreshCustomer();
        saveCustomerData(customerListA);
    }


//    @FXML
//    private void promoteCustomer() {
//        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
//
//        if (selectedCustomer != null) {
//            for (Customer customer : customerListA) {
//                if (customer.equals(selectedCustomer)) {
//                    customer.promoteCustomer();
//                    break;
//                }
//            }
//            refreshCustomer();
//            showSuccessAlert("Promote Successful", "Customer Promoted Successfully!");
//        }
//    }
    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void switchItem(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("Admin-view.fxml"));
        Parent adminView = fxmlLoader.load();

        Scene adminViewScene = new Scene(adminView);
        AdminViewController adminViewController = fxmlLoader.getController();
        adminViewController.setItemDataA(itemListA);
        adminViewController.setCustomerDataA(customerListA);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(adminViewScene);
        currentStage.show();
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
        initializeSearchBox();
        initializeCustomerGroup();

        Platform.runLater(() -> {
            refreshCustomer();
            FilteredList<Customer> searchCustomer = new FilteredList<>(getCustomer(), p -> true);
            customerTableView.setItems(searchCustomer);
            customerTableView.getItems();

            searchField.textProperty().addListener((obs, oldValue, newValue) -> {
                handleSearchFieldChange(searchCustomer, newValue);
            });

            customerGroup.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newValue) -> {
                handleCustomerGroupChange(searchCustomer, newValue);
            });

            searchBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                handleSearchBoxChange(newVal, searchCustomer);
            });
        });
    }

    private void initializeSearchBox() {
        searchBox.getItems().addAll(
                "ID",
                "Name",
                "Customer Type"
        );
        searchField.setVisible(true);
        customerGroup.setVisible(false);
    }

    private void initializeCustomerGroup() {
        customerGroup.getItems().addAll(
                "Guest",
                "Regular",
                "VIP"
        );
        customerGroup.setVisible(false);
    }

    private void handleSearchFieldChange(FilteredList<Customer> searchCustomer, String newValue) {
        switch (searchBox.getValue()) {
            case "ID":
                searchCustomer.setPredicate(p -> p.getID().toLowerCase().contains(newValue.toLowerCase().trim()));
                break;
            case "Name":
                searchCustomer.setPredicate(p -> p.getName().toLowerCase().contains(newValue.toLowerCase().trim()));
                break;
        }
    }

    private void handleCustomerGroupChange(FilteredList<Customer> searchCustomer, String newValue) {
        if (searchBox.getValue().equals("Customer Type")) {
            searchCustomer.setPredicate(p -> p.getCustomer_type().toLowerCase().contains(newValue.toLowerCase().trim()));
        }
    }

    private void handleSearchBoxChange(String newValue, FilteredList<Customer> searchCustomer) {
        if (Objects.equals(newValue, "ID") || Objects.equals(newValue, "Name")) {
            customerGroup.setVisible(false);
            searchCustomer.setPredicate(p -> true);
            searchField.setText("");
            searchField.setVisible(true);
        } else if (Objects.equals(newValue, "Customer Type")) {
            customerGroup.setVisible(true);
            searchCustomer.setPredicate(p -> true);
            customerGroup.setValue("");
            searchField.setText("");
            searchField.setVisible(false);
        }
    }

}
