package com.example.videostore;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddItemAdminController implements Initializable {

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
    static ArrayList<Customer> customerListA;
    static ArrayList<Item> itemsListA;
    static int itr;
    public void setCustomerDataA(ArrayList<Customer> customer) {
        customerListA = customer;
    }
    public void setItemDataA(ArrayList<Item> item) {
        itemsListA = item;
    }
    Pattern idPattern = Pattern.compile("^I\\d{3}-\\d{4}$");

    @FXML
    public void confirm() throws IOException {
        boolean isValid = true;
        Alert alert = new Alert(Alert.AlertType.WARNING);

        // Validate ID
        if (!checkData(id.getText(), idPattern)) {
            idCheck.setText("Invalid item ID");
            isValid = false;
        } else if (itemsListA.stream().anyMatch(item -> item.getID().startsWith(id.getText()))) {
            idCheck.setText("Item ID already exists");
            isValid = false;
        } else {
            idCheck.setText("");
        }

        // Validate Title
        if (title.getText().trim().isEmpty()) {
            titleCheck.setText("Title cannot be empty");
            isValid = false;
        } else {
            titleCheck.setText("");
        }

        // Validate Rent Type
        if (rentType.getValue() == null) {
            rentTypeCheck.setText("Rent Type cannot be empty");
            isValid = false;
        } else {
            rentTypeCheck.setText("");
        }

        // Validate Loan Type
        if (loanType.getValue() == null) {
            loanTypeCheck.setText("Loan Type cannot be empty");
            isValid = false;
        } else {
            loanTypeCheck.setText("");
        }

        // Validate Number of Copies
        try {
            int numCopies = Integer.parseInt(copies.getText());
            if (numCopies < 0) {
                copiesCheck.setText("Copies cannot be negative");
                isValid = false;
            } else {
                copiesCheck.setText("");
            }
        } catch (NumberFormatException e) {
            copiesCheck.setText("Invalid number of copies");
            isValid = false;
        }

        // Validate Fee
        try {
            float feeValue = Float.parseFloat(fee.getText());
            if (feeValue <= 0) {
                feeCheck.setText("Fee must be greater than 0");
                isValid = false;
            } else {
                feeCheck.setText("");
            }
        } catch (NumberFormatException e) {
            feeCheck.setText("Invalid fee");
            isValid = false;
        }

        if (isValid) {
            Item newItem = new Item();
            newItem.setID(id.getText());
            newItem.setTitle(title.getText());
            newItem.setRent_Type(rentType.getValue());
            newItem.setLoan_Type(loanType.getValue());
            newItem.setNum_of_copies(Integer.parseInt(copies.getText()));
            newItem.setRent_fee(Float.parseFloat(fee.getText()));
            if (rentType.getValue().equals("Game")) {
                newItem.setGenre(null);
            } else {
                newItem.setGenre(genre.getValue());
            }
            itemsListA.add(newItem);
            closeButtonAction();
        } else {
            alert.setTitle("Validation Error");
            alert.setHeaderText("Please fix the errors and try again.");
            alert.setContentText("There are validation errors in the form.");
            alert.showAndWait();
        }
    }
    @FXML
    private void closeButtonAction() {
        // Get a handle to the stage
        Stage stage = (Stage) confirm.getScene().getWindow();
        // Close the stage;
        stage.close();
    }
    // Check ID match pattern
    private boolean checkData(String id, Pattern pattern) {
        return pattern.matcher(id).find();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rentType.getItems().addAll("Record", "DVD", "Game");
        rentType.setOnAction(e ->{
            boolean isGameSelected = rentType.getValue().equals("Game");
            genreLabel.setVisible(!isGameSelected);
            genre.setVisible(!isGameSelected);
        });
        loanType.getItems().addAll("2-day", "1-week");
        genre.getItems().addAll("Action", "Horror", "Drama", "Comedy");
    }
}
