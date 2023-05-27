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
    public Button promote;
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
        // Create a new ObservableList to hold the items
        ObservableList<Customer> customer = FXCollections.observableArrayList();
        // Add all customers the list
        customer.addAll(customerListA);
        // Return the list of customer
        return customer;
    }
    private void refreshCustomer() {
        // Set the cell value factories for each column
        id.setCellValueFactory(new PropertyValueFactory<>("ID")); // ID column
        name.setCellValueFactory(new PropertyValueFactory<>("Name")); // Name column
        address.setCellValueFactory(new PropertyValueFactory<>("Address")); // Address column
        phone.setCellValueFactory(new PropertyValueFactory<>("Phone")); // ID column
        customerType.setCellValueFactory(new PropertyValueFactory<>("Customer_type")); // Customer_type column
        username.setCellValueFactory(new PropertyValueFactory<>("Username")); // Username column
        password.setCellValueFactory(new PropertyValueFactory<>("Password")); // Password column

        // Set the items of the table view to the customer data
        customerTableView.setItems(getCustomer());
    }
    @FXML
    private void addCustomer() throws IOException {
        // Load the FXML file for the add customer view
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddCustomer-view.fxml"));
        DialogPane addCustomer = fxmlLoader.load();

        // Get the controller for the add customer view
        AddCustomerAdminController addCustomerAdminController = fxmlLoader.getController();
        addCustomerAdminController.setCustomerDataA(customerListA);
        addCustomerAdminController.setItemDataA(itemListA);

        // Create a dialog and set the dialog pane to the add customer view
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(addCustomer);
        dialog.setTitle("Add Customer");

        // Show the dialog and wait for the user's response
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        // Refresh the customer view
        refreshCustomer();
        // Save the updated customer data
        saveCustomerData(customerListA);
    }
    @FXML
    private void editCustomer() throws IOException {
        // Load the FXML file for the edit customer view
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditCustomer-view.fxml"));
        DialogPane editCustomer = fxmlLoader.load();

        // Get the controller for the add customer view
        EditCustomerAdminController editCustomerAdminController = fxmlLoader.getController();
        editCustomerAdminController.setCustomerDataA(customerListA);
        editCustomerAdminController.setItemDataA(itemListA);
        editCustomerAdminController.receiveCustomerToEdit(customerTableView.getSelectionModel().getSelectedItem());

        // Create a dialog and set the dialog pane to the add customer view
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(editCustomer);
        dialog.setTitle("Edit Customer");

        // Show the dialog and wait for the user's response
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        // Refresh the customer view
        refreshCustomer();
        // Save the updated customer data
        saveCustomerData(customerListA);
    }
    @FXML
    private void deleteCustomer() throws IOException {
        // Get the selected customer from the table view
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        // Remove the selected customer from the customer list using a lambda expression
        customerListA.removeIf(customer -> customer.equals(selectedCustomer));
        // Refresh the customer table view to reflect the updated list
        refreshCustomer();
        // Save the updated customer data to a file or database
        saveCustomerData(customerListA);
    }
    @FXML
    private void promoteCustomer() throws IOException {
        // Get the selected customer from the table view
        Customer selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            // Iterate through the customer list to find the selected customer
            for (Customer customer : customerListA) {
                if (customer.equals(selectedCustomer)) {
                    // Promote the customer
                    customer.promoteCustomer();
                    break; // Exit the loop once the customer is found and promoted
                }
            }
            // Save the updated customer data to a file
            saveCustomerData(customerListA);
            // Refresh the customer table view
            refreshCustomer();
            // Show a success alert
            showSuccessAlert("Promote Successful", "Customer Promoted Successfully!");
        }
    }
    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Create an instance of Alert with Information type
        alert.setTitle(title); // Set the title of the alert
        alert.setHeaderText(null); // Set the header text to null (no header)
        alert.setContentText(message); // Set the content text of the alert
        alert.showAndWait(); // Show the alert and wait for user interaction
    }
    @FXML
    public void switchItem(ActionEvent event) throws IOException {
        // Create an FXMLLoader to load the Admin-view.fxml file
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("Admin-view.fxml"));
        // Load the FXML file and create a Parent node
        Parent adminView = fxmlLoader.load();

        // Create a new Scene using the loaded FXML and Parent
        Scene adminViewScene = new Scene(adminView);
        // Get the controller instance from the FXMLLoader
        AdminViewController adminViewController = fxmlLoader.getController();
        // Pass the item data and customer data to the admin view controller
        adminViewController.setItemDataA(itemListA);
        adminViewController.setCustomerDataA(customerListA);

        // Get the current stage from the event source and set the new scene
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
        initializeSearchBox(); // Initialize the search box with available options
        initializeCustomerGroup(); // Initialize the customer group dropdown with available options
        Platform.runLater(() -> {
            refreshCustomer();// Refresh the customer data
            FilteredList<Customer> searchCustomer = new FilteredList<>(getCustomer(), p -> true);
            customerTableView.setItems(searchCustomer);
            customerTableView.getItems();
            // Listen for changes in the search field and apply filtering
            searchField.textProperty().addListener((obs, oldValue, newValue) -> {
                handleSearchFieldChange(searchCustomer, newValue);
            });
            // Listen for changes in the customer group dropdown and apply filtering
            customerGroup.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newValue) -> {
                handleCustomerGroupChange(searchCustomer, newValue);
            });
            // Listen for changes in the search box selection and adjust visibility and filtering
            searchBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                handleSearchBoxChange(newVal, searchCustomer);
            });
        });
    }
    // Initializes the search box with available options and sets visibility of fields
    private void initializeSearchBox() {
        searchBox.getItems().addAll(
                "ID",
                "Name",
                "Customer Type"
        );
        searchField.setVisible(true);
        customerGroup.setVisible(false);
    }
    // Initializes the customer group dropdown with available options
    private void initializeCustomerGroup() {
        customerGroup.getItems().addAll(
                "Guest",
                "Regular",
                "VIP"
        );
        customerGroup.setVisible(false);
    }
    // Handles changes in the search field
    private void handleSearchFieldChange(FilteredList<Customer> searchCustomer, String newValue) {
        switch (searchBox.getValue()) {
            case "ID" ->
                    searchCustomer.setPredicate(p -> p.getID().toLowerCase().contains(newValue.toLowerCase().trim()));
            case "Name" ->
                    searchCustomer.setPredicate(p -> p.getName().toLowerCase().contains(newValue.toLowerCase().trim()));
        }
    }
    // Handles changes in the customer group dropdown
    private void handleCustomerGroupChange(FilteredList<Customer> searchCustomer, String newValue) {
        if (searchBox.getValue().equals("Customer Type")) {
            searchCustomer.setPredicate(p -> p.getCustomer_type().toLowerCase().contains(newValue.toLowerCase().trim()));
        }
    }
    // Handles changes in the search box selection
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
