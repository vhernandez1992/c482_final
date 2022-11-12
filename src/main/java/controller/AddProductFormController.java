package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.nio.DoubleBuffer;
import java.util.Optional;
import java.util.ResourceBundle;

/** Loads the form to add a product to the application */
public class AddProductFormController implements Initializable {
    public TableView<Part> partToAdd;
    public TableColumn partIDCol;
    public TableColumn partNameCol;
    public TableColumn stockCol;
    public TableColumn priceCol;
    public TableView<Part> addedPartsList;
    public TableColumn addedPartIDCol;
    public TableColumn addedPartNameCol;
    public TableColumn addedStockCol;
    public TableColumn addedPriceCol;
    public Button saveButton;
    public Button cancelButton;
    public TextField nameField;
    public TextField stockField;
    public TextField priceField;
    public TextField maxField;
    public TextField minField;
    public TextField idField;
    public Button removeAssocPart;
    public Button addAssocPart;
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    public TextField partSearchBar;

    /** Initializes the product add form and also sets the parts and associated parts list for that product
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partToAdd.setItems(Inventory.getAllParts());

        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        addedPartsList.setItems(associatedParts);

        addedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addedStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** When the add part button is selected, the selected part is added to the associated parts list
     *
     * RUNTIME ERROR: If no part is selected, an informational notification will
     * pop up
     *
     * @param actionEvent
     */
    public void addButtonSelected(ActionEvent actionEvent) {
        Part selectedPart = partToAdd.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("No part selected to add");
            alert.showAndWait();
        }
        else {
            associatedParts.add(selectedPart);
            addedPartsList.setItems(associatedParts);
        }
    }

    /** When the remove associated part button is selected, the selected part is removed from the associated parts list
     *
     * RUNTIME ERROR: If no part is selected to remove, and informational notification
     * will pop up
     *
     * @param actionEvent
     */
    public void removeSelected(ActionEvent actionEvent) {
        Part selectedPart = addedPartsList.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("No part selected to remove");
            alert.showAndWait();
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove this associated part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            associatedParts.remove(selectedPart);
            addedPartsList.setItems(associatedParts);
        }
    }

    /** When the save button is selected, the product and its associated parts are added to the application
     *
     * RUNTIME ERROR: If the max is less than the min, an error notification will pop up
     * RUNTIME ERROR: If the inventory is not within the min and max, an error
     * notification will pop up
     * RUNTIME ERROR: If a text field is left blank or using the incorrect format,
     * an error notification will pop up
     *
     * @param actionEvent
     * @throws IOException
     */
    public void saveSelected(ActionEvent actionEvent) throws IOException {
        try {
            int id = Inventory.newProductID();
            String name = nameField.getText();
            int stock = Integer.parseInt(stockField.getText());
            double price = Double.parseDouble(priceField.getText());
            int max = Integer.parseInt(maxField.getText());
            int min = Integer.parseInt(minField.getText());

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Max must be greater than Min");
                alert.showAndWait();
                return;
            }
            else if (min > stock || max < stock) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Stock must be within the Min and Max");
                alert.showAndWait();
                return;
            }

            Product newProduct = new Product(id, name, price, stock, min, max);
            for (Part addedParts : associatedParts) {
                newProduct.addAssociatedPart(addedParts);
            }
            Inventory.addProduct(newProduct);


            Parent root = FXMLLoader.load(getClass().getResource("/hernandez/c482_final/MainScreen.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1250, 500);
            stage.setTitle("CyberOne Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
        catch (NumberFormatException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Incorrect format used for input");
            alert.showAndWait();
        }
    }

    /** When the cancel button is selected, the application returns to the main screen
     *
     * @param actionEvent
     * @throws IOException
     */
    public void cancelSelected(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/hernandez/c482_final/MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1250, 500);
        stage.setTitle("CyberOne Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /** Uses the search bar to search for parts from the parts list
     *
     * @param actionEvent
     */
    public void searchParts(ActionEvent actionEvent) {
        String searchText = partSearchBar.getText();

        ObservableList<Part> partResults = Inventory.lookupPart(searchText);

        if (partResults.size() == 0) {
            int partIDSearch = Integer.parseInt(searchText);
            Part searchPart = Inventory.lookupPart(partIDSearch);
            if (searchPart != null) {
                partResults.add(searchPart);
            }
        }

        partToAdd.setItems(partResults);
    }


}
