package controller;

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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Loads the modify product form of the application
 *
 */
public class ModifyProductFormController implements Initializable {
    @FXML private TableView<Part> partToAdd;
    @FXML private TableColumn partIDCol;
    @FXML private TableColumn partNameCol;
    @FXML private TableColumn stockCol;
    @FXML private TableColumn priceCol;
    @FXML private TableView<Part> addedPartsList;
    @FXML private TableColumn addedPartIDCol;
    @FXML private TableColumn addedPartNameCol;
    @FXML private TableColumn addedStockCol;
    @FXML private TableColumn addedPriceCol;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Product selectedProduct;
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField stockField;
    @FXML private TextField priceField;
    @FXML private TextField maxField;
    @FXML private TextField minField;
    @FXML private Button addAssocPart;
    @FXML private Button removeAssocPart;
    @FXML private TextField partsSearchBar;
    private int currIndex;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** Sends the selected product and its index from the main screen to be modified
     *
     * @param currIndex
     * @param product
     */
    public void productToModify(int currIndex, Product product) {
        this.selectedProduct = product;
        this.currIndex = currIndex;

        idField.setText(Integer.toString(selectedProduct.getID()));
        nameField.setText(selectedProduct.getName());
        stockField.setText(Integer.toString(selectedProduct.getStock()));
        priceField.setText(Double.toString(selectedProduct.getPrice()));
        maxField.setText(Integer.toString(selectedProduct.getMax()));
        minField.setText(Integer.toString(selectedProduct.getMin()));

        associatedParts.clear();

        for (Part part : product.getAllAssociatedParts()) {
            if (!associatedParts.contains(part)) {
                associatedParts.addAll(product.getAllAssociatedParts());
            }
        }

    }

    /** Initializes the modify part form and populates the parts and associated parts list
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

    /** Adds the selected part to the associated parts list for the product
     *
     * @param actionEvent
     */
    public void addButtonSelected(ActionEvent actionEvent) {
        Part selectedPart = partToAdd.getSelectionModel().getSelectedItem();
        associatedParts.add(selectedPart);
        addedPartsList.setItems(associatedParts);
    }

    /** Removes the associated part from the associated parts list for that product
     *
     * @param actionEvent
     */
    public void removeSelected(ActionEvent actionEvent) {
        Part selectedPart = addedPartsList.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove this associated part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            associatedParts.remove(selectedPart);
            addedPartsList.setItems(associatedParts);
        }

    }

    /** Saves all updated information and associated parts to a new updated parts and
     * replaces the product at that index.
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
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());
            int min = Integer.parseInt(minField.getText());
            int max = Integer.parseInt(maxField.getText());

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

            Product updatedProduct = new Product(id, name, price, stock, min, max);
            if (updatedProduct != associatedParts) {
                for (Part part : associatedParts) {
                    updatedProduct.addAssociatedPart(part);
                }
            }
            Inventory.updateProduct(currIndex, updatedProduct);


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

    /** Sends the application back to the main screen when the cancel button is selected
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

    /** Searches the parts list based on user input
     *
     * @param actionEvent
     */
    public void searchParts(ActionEvent actionEvent) {
        String searchText = partsSearchBar.getText();

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
