package com.example.videostore;

import javafx.application.Platform;
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

public class EditItemAdminController implements Initializable {
    @FXML
    private Label idCheck;
    @FXML
    private Label titleCheck;
    @FXML
    private Label rentTypeCheck;
    @FXML
    private Label loanTypeCheck;
    @FXML
    private Label copiesCheck;
    @FXML
    private Label feeCheck;
    @FXML
    private Label genreCheck;
    @FXML
    private Label genreLabel;
    @FXML
    private Button confirm;
    @FXML
    private Button cancel;
    @FXML
    private TextField copies;
    @FXML
    private TextField fee;
    @FXML
    private TextField id;
    @FXML
    private TextField title;
    @FXML
    private ComboBox<String> genre;
    @FXML
    private ComboBox<String> loanType;
    @FXML
    private ComboBox<String> rentType;
    static ArrayList<Item> itemListA;
    static ArrayList<Customer> customerListA;
    private Item editItem = new Item();

    // It defines a regular expression pattern for matching a specific format of ID. EX:"I001"
    Pattern idPattern = Pattern.compile("^I\\d{3}-\\d{4}$");
    public void receiveItemToEdit(Item item){
        editItem = item;
    }
    public void setCustomerDataA(ArrayList<Customer> customer){
        customerListA = customer;
    }
    public void setItemDataA(ArrayList<Item> item){
        itemListA = item;
    }
    @FXML
    public void confirm() throws IOException {
        Item newItem = new Item();
        boolean idValid = checkIdFormat();
        boolean titleValid = checkTitle();
        boolean rentTypeValid = checkRentType();
        boolean loanTypeValid = checkLoanType();
        boolean copiesValid = checkCopies();
        boolean feeValid = checkFee();
        boolean idUnique = checkUniqueId();
        if (idValid && titleValid && rentTypeValid && loanTypeValid && copiesValid && feeValid && idUnique){
            // Set item properties
            newItem.setID(id.getText());
            newItem.setTitle(title.getText());
            newItem.setRent_Type(rentType.getValue());
            newItem.setLoan_Type(loanType.getValue());
            newItem.setNum_of_copies(Integer.parseInt(copies.getText()));
            newItem.setRent_fee(Float.parseFloat(fee.getText()));
            if (rentType.getValue().contentEquals("Game")) {
                newItem.setGenre(null);
            }else {
                newItem.setGenre(genre.getValue());
            }

            // Update itemsListA
            int itemIndex = itemListA.indexOf(editItem);
            if (itemIndex >= 0) {
                itemListA.set(itemIndex, newItem);
            }
            // Update customerListA
            for (Customer customer : customerListA){
                List<Item> rentItems = customer.getRent_items();
                int itemIndexInRent = rentItems.indexOf(editItem);
                if (itemIndexInRent >= 0) {
                    rentItems.set(itemIndexInRent, newItem);
                }
            }
            closeButtonAction();
        }
    }
    // Check ID format
    private boolean checkIdFormat() {
        if (checkData(id.getText(), idPattern)){
            idCheck.setText("");
            return true;
        }else {
            idCheck.setText("Invalid id!");
            return false;
        }
    }
    // Check Title
    private boolean checkTitle() {
        if (!title.getText().trim().isEmpty()){
            titleCheck.setText("");
            return true;
        }else {
            titleCheck.setText("Title cannot be empty");
            return false;
        }
    }
    // Check Rent Type
    private boolean checkRentType() {
        if (rentType.getValue() != null) {
            rentTypeCheck.setText("");
            return true;
        }else {
            rentTypeCheck.setText("Rent Type cannot be empty!");
            return false;
        }
    }
    // Check Loan Type
    private boolean checkLoanType() {
        if (loanType.getValue() != null) {
            loanTypeCheck.setText("");
            return true;
        }else {
            loanTypeCheck.setText("Loan Type cannot be empty!");
            return false;
        }
    }
    // Check copies
    private boolean checkCopies() {
        try {
            int numCopies = Integer.parseInt(copies.getText());
            if (numCopies >= 0) {
                copiesCheck.setText("");
                return true;
            }else {
                copiesCheck.setText("Copies cannot be negative!");
                return false;
            }
        } catch (NumberFormatException e) {
            copiesCheck.setText("Invalid number of copies!");
            return false;
        }
    }
    // Check fee
    private boolean checkFee() {
        try {
            float feeValue = Float.parseFloat(fee.getText());
            if (feeValue > 0) {
                feeCheck.setText("");
                return true;
            }else {
                feeCheck.setText("Fee must be greater than 0!");
                return false;
            }
        } catch (NumberFormatException e) {
            feeCheck.setText("Invalid fee!");
            return false;
        }
    }
    // Check if ID is unique
    private boolean checkUniqueId() {
        if (!id.getText().contentEquals(editItem.getID())) {
            for (Item item : itemListA) {
                if (id.getText().contains(item.getID().substring(0, 4))) {
                    idCheck.setText("ID already exist!");
                    return false;
                }
            }
        }
        return true;
    }
    @FXML
    private void closeButtonAction() {
        // Get a handle to the stage
        Stage stage = (Stage) confirm.getScene().getWindow();
        // Close the stage;
        stage.close();
    }
    // Check ID match pattern
    public boolean checkData(String id, Pattern pattern){
        return pattern.matcher(id).find();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the ComboBox for rent type
        initializeRentTypeComboBox();

        // Initialize the ComboBox for loan type
        initializeLoanTypeComboBox();

        // Initialize the ComboBox for genre type
        initializeGenreTypeComboBox();

        // Initialize the fields with data from the editItem object
        initializeFieldsWithEditItemData();
    }
    // Initialize the ComboBox for rent type
    private void initializeRentTypeComboBox() {
        rentType.getItems().addAll(
                "Record",
                "DVD",
                "Game"
        );
        // Set an event handler to handle the selection change event.
        // If "Game" is selected, we hide the genreLabel and genre ComboBox; otherwise, we make them visible.
        rentType.setOnAction(e -> {
            boolean isGameSelected = rentType.getValue().contentEquals("Game");
            genreLabel.setVisible(!isGameSelected);
            genre.setVisible(!isGameSelected);
        });
    }
    // Initialize the ComboBox for loan type
    private void initializeLoanTypeComboBox() {
        loanType.getItems().addAll(
                "2-days",
                "1-week"
        );
    }
    // Initialize the ComboBox for genre type
    private void initializeGenreTypeComboBox() {
        genre.getItems().addAll(
                "Action",
                "Horror",
                "Drama",
                "Comedy"
        );
    }
    // Initialize the fields with data from the editItem object
    private void initializeFieldsWithEditItemData() {
        // To display a default value based on the item's value.
        Platform.runLater(() -> {
            id.setText(editItem.getID());
            title.setText(editItem.getTitle());
            rentType.setValue(editItem.getRent_Type());
            loanType.setValue(editItem.getLoan_Type());
            copies.setText(Integer.toString(editItem.getNum_of_copies()));
            fee.setText(Float.toString(editItem.getRent_fee()));
            genre.setValue(editItem.getGenre());
        });
    }
}
