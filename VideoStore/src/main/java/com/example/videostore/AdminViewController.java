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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.videostore.Save_Data.saveItemData;
import static com.example.videostore.Save_Data.saveCustomerData;

public class AdminViewController implements Initializable {
    @FXML
    private TableColumn<Item, Integer> copies;
    @FXML
    private TableColumn<Item, String> genre;
    @FXML
    private TableColumn<Item, String> id;
    @FXML
    private TableColumn<Item, String> loan_type;
    @FXML
    private TableColumn<Item, Float> rent_fee;
    @FXML
    private TableColumn<Item, String> rent_type;
    @FXML
    private TableColumn<Item, String> title;
    @FXML
    private TableView<Item> itemTableViewA;
    @FXML
    private ComboBox<String> searchBox;
    @FXML
    private TextField searchField;
    @FXML
    private Button edit;
    @FXML
    private Button delete;

    static ArrayList<Item> itemListA;
    static ArrayList<Customer> customerListA;
    private ObservableList<Item> getItems() {
        // Create an ObservableList using the FXCollections.observableArrayList() method
        // and pass in the itemListA as the initial data for the list
        ObservableList<Item> item = FXCollections.observableArrayList();
        item.addAll(itemListA);
        return item;
    }

    @FXML
    private void refreshItem() {
        //Set cell value factories for each column in the TableView
        id.setCellValueFactory(new PropertyValueFactory<>("ID")); //ID column
        title.setCellValueFactory(new PropertyValueFactory<>("Title")); //Title column
        rent_type.setCellValueFactory(new PropertyValueFactory<>("Rent_Type")); //Rent Type column
        loan_type.setCellValueFactory(new PropertyValueFactory<>("Loan_Type")); //Loan Type column
        copies.setCellValueFactory(new PropertyValueFactory<>("Num_of_copies")); //Number of Copies column
        rent_fee.setCellValueFactory(new PropertyValueFactory<>("Rent_fee")); //Rent Fee column
        genre.setCellValueFactory(new PropertyValueFactory<>("genre")); //Genre column

        // Set the items in the TableView
        itemTableViewA.setItems(getItems());

        //Refresh the TableView to update its content
        itemTableViewA.refresh();
    }
    public void setCustomerDataA(ArrayList<Customer> customer){
        customerListA = customer;
    }
    public void setItemDataA(ArrayList<Item> item){
        itemListA = item;
    }
    @FXML
    private void addItem() throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("AddItem-view.fxml"));
            DialogPane addItem = fxmlLoader.load();

            AddItemAdminController addItemController = fxmlLoader.getController();
            addItemController.setItemDataA(itemListA);
            addItemController.setCustomerDataA(customerListA);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(addItem);
            dialog.setTitle("Add Item");

            Optional<ButtonType> clickedButton = dialog.showAndWait();
            refreshItem();
            saveItemData(itemListA);

    }
    @FXML
    private void editItem() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("EditItem-view.fxml"));
        DialogPane editItem = fxmlLoader.load();

        EditItemAdminController editItemController = fxmlLoader.getController();
        editItemController.setItemDataA(itemListA);
        editItemController.setCustomerDataA(customerListA);
        editItemController.receiveItemToEdit(itemTableViewA.getSelectionModel().getSelectedItem());

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(editItem);
        dialog.setTitle("Edit item");

        Optional<ButtonType> result = dialog.showAndWait();
        refreshItem();
        saveItemData(itemListA);
        saveCustomerData(customerListA);

    }
    @FXML
    private void deleteItem() throws IOException {
        Item selectedItem = itemTableViewA.getSelectionModel().getSelectedItem();

        itemListA.removeIf(item -> item.equals(selectedItem));
        refreshItem();
        saveItemData(itemListA);
    }

    @FXML
    public void switchCustomer(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("AdminCustomer-view.fxml"));
        Parent adminView = fxmlLoader.load();

        Scene adminViewScene = new Scene(adminView);
        AdminViewCustomerController adminViewCustomerController = fxmlLoader.getController();
        adminViewCustomerController.setItemDataA(itemListA);
        adminViewCustomerController.setCustomerDataA(customerListA);


        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(adminViewScene);
        currentStage.show();
    }
    @FXML
    private void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent logoutView = loader.load();

            Scene logoutScene = new Scene(logoutView);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(logoutScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Display an error message to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Logout Error");
            alert.setContentText("An error occurred during logout. Please try again.");
            alert.showAndWait();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchBox.getItems().addAll(
                "ID", // Add "ID" option to searchBox
                "Title", // Add "Title" option to searchBox
                "Out of stock" // Add "Title" option to searchBox
        );

        Platform.runLater(() -> {
            refreshItem(); // Refresh the items
            // Create a filtered list based on the items
            FilteredList<Item> searchItem = new FilteredList<>(getItems(), p -> true);
            itemTableViewA.setItems(searchItem); // Set the table's items to the filtered list


            searchField.textProperty().addListener((obs, oldValue, newValue) -> {
                String selectedSearchOption = searchBox.getValue(); // Get the selected search option

                if (selectedSearchOption != null) {
                    searchItem.setPredicate(item -> {
                        String lowerCaseNewValue = newValue.toLowerCase().trim();
                        return switch (selectedSearchOption) {
                            case "ID" ->
                                // Filter table by item ID
                                    item.getID().toLowerCase().contains(lowerCaseNewValue);
                            case "Title" ->
                                // Filter table by item title
                                    item.getTitle().toLowerCase().contains(lowerCaseNewValue);
                            case "Out of stock" ->
                                // Filter table by item out-of-stock count
                                    item.getNum_of_copies() == Integer.parseInt(newValue);
                            default ->
                                // For other search options, return true to include all items
                                    true;
                        };
                    });
                }
            });

            searchBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if ("Out of stock".equals(newVal)) {
                    searchField.setText("0");
                    searchField.setVisible(false);
                } else {
                    searchField.setText("");
                    searchField.setVisible(true);
                }
            });
        });
    }

}
