package controller;

import javafx.application.Preloader;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Part;
import model.Inventory;
import model.Product;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.channels.AlreadyBoundException;
import java.util.Optional;
import java.util.ResourceBundle;

/** The main screen of the application */
public class MainScreenController implements Initializable {


    public TextField productSearchBar;
    public TextField partSearchBar;
    @FXML private Button exit;
    @FXML private TableView<Product> prodTable;
    @FXML private TableColumn<Product, Integer> prodIDCol;
    @FXML private TableColumn<Product, String> prodNameCol;
    @FXML private TableColumn<Product, Integer> prodStock;
    @FXML private TableColumn<Product, Double> prodPrice;
    @FXML private TableColumn<Part, Integer> partID;
    @FXML private TableColumn<Part, String> partName;
    @FXML private TableColumn<Part, Integer> stock;
    @FXML private TableColumn<Part, Double> price;
    @FXML private TableView<Part> partTable;


    /** Initializes the main screen and populates the part and product tables
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTable.setItems(Inventory.getAllParts());

        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodTable.setItems(Inventory.getAllProducts());

        prodIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** Sends the application to the add part form
     *
     * @param actionEvent
     * @throws IOException
     */
    public void toAddPartForm(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hernandez/c482_final/AddPartForm.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 500, 700);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /** Sends the application to the modify part form
     *
     * RUNTIME ERROR: If no part is selected to modify, a
     * error notification will pop up
     *
     * @param actionEvent
     * @throws IOException
     */
    public void toModifyPartForm(ActionEvent actionEvent) throws IOException {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        int currIndex = partTable.getSelectionModel().getSelectedIndex();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hernandez/c482_final/ModifyPartForm.fxml"));
            Parent root = loader.load();
            ModifyPartFormController partController = loader.getController();
            partController.partToModify(currIndex, selectedPart);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 500, 700);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
        catch (NullPointerException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No part selected!");
            alert.showAndWait();
        }
    }

    /** Sends the application to the add product form
     *
     * @param actionEvent
     * @throws IOException
     */
    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/hernandez/c482_final/AddProductForm.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /** Sends the application to the modify product form
     *
     * RUNTIME ERROR: If no product is selected to modify, an error
     * notification will pop up
     *
     * @param actionEvent
     * @throws IOException
     */
    public void toModifyProduct(ActionEvent actionEvent) throws IOException {
        Product selectedProduct = prodTable.getSelectionModel().getSelectedItem();
        int currIndex = prodTable.getSelectionModel().getSelectedIndex();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hernandez/c482_final/ModifyProductForm.fxml"));
            Parent root = loader.load();
            ModifyProductFormController productController = loader.getController();
            productController.productToModify(currIndex, selectedProduct);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 600);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }
        catch (NullPointerException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No product selected!");
            alert.showAndWait();
        }
    }

    /** Deletes the selected part from the application
     *
     * LOGICAL ERROR: If no part is selected, an informational warning
     * will pop up
     *
     * @param event
     */
    @FXML void deletePart(ActionEvent event) {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setContentText("No part selected to delete!");
                alert.showAndWait();
                return;
            }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }

    }

    /** Deletes the selected product from the application
     *
     * LOGICAL ERROR: If a product with associated parts is selected,
     * a warning will pop up
     *
     * @param event
     */
    @FXML void deleteProduct(ActionEvent event) {
        Product selectedProduct = prodTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setContentText("No product selected to delete!");
            alert.showAndWait();
            return;
        }
        else if(selectedProduct.getAllAssociatedParts().size() > 0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Unable to delete product with associated parts!");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this product?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deleteProduct(selectedProduct);
        }

    }

    /** Exits the application when the exit button is pressed
     *
     * @param Exit
     */
    public void exitSelected(ActionEvent Exit) {
        Stage stage = (Stage) ((Node) Exit.getSource()).getScene().getWindow();
        stage.close();
    }

    /** Searches the list of parts using user input
     *
     * FUTURE ENHANCEMENT: A search based on the Machine ID or Company name
     * FUTURE ENHANCEMENT: A new list to show what products the parts are associated with
     *
     * @param actionEvent
     */
    public void searchParts(ActionEvent actionEvent) {
        String searchText = partSearchBar.getText();

        ObservableList<Part> partResults = Inventory.lookupPart(searchText);
        try {
            if (partResults.size() == 0) {
                int partIDSearch = Integer.parseInt(searchText);
                Part searchPart = Inventory.lookupPart(partIDSearch);
                if (searchPart != null) {
                    partResults.add(searchPart);
                }
            }
            partTable.setItems(partResults);
        }
        catch (NumberFormatException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No parts found");
            alert.showAndWait();
        }


        partTable.setItems(partResults);
    }

    /** Searches the product list using user input
     *
     *
     *
     * @param actionEvent
     */
    public void searchProducts(ActionEvent actionEvent) {
        String searchText = productSearchBar.getText();

        ObservableList<Product> productResults = Inventory.lookupProduct(searchText);


        try {
            if (productResults.size() == 0) {
                int productIDSearch = Integer.parseInt(searchText);
                Product searchProduct = Inventory.lookupProduct(productIDSearch);
                if (searchProduct != null) {
                    productResults.add(searchProduct);
                }
            }
            prodTable.setItems(productResults);
        }
        catch (RuntimeException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No products found");
            alert.showAndWait();
        }
    }
}