package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** Loads the form to modify existing parts in the application
 *
 */
public class ModifyPartFormController implements Initializable {

    public RadioButton inhouseRadio;
    public RadioButton outsourcedRadio;
    public Label inhouseOrOutsourced;
    public Button addPartSave;
    public Button addPartCancel;
    public TextField idField;
    public TextField nameField;
    public TextField stockField;
    public TextField priceField;
    public TextField maxField;
    public TextField minField;
    public TextField machineOrCompanyField;
    public Part selectedPart;
    private int currIndex = 0;


    /** Sends the selected part and its index to be modified
     *
     * @param currIndex
     * @param part
     */
    public void partToModify(int currIndex, Part part) {
        this.selectedPart = part;
        this.currIndex = currIndex;
        if (part instanceof Outsourced) {
            idField.setText(Integer.toString(selectedPart.getId()));
            nameField.setText(selectedPart.getName());
            stockField.setText(Integer.toString(selectedPart.getStock()));
            priceField.setText(Double.toString(selectedPart.getPrice()));
            maxField.setText(Integer.toString(selectedPart.getMax()));
            minField.setText(Integer.toString(selectedPart.getMin()));
            machineOrCompanyField.setText(((Outsourced) selectedPart).getCompanyName());
            outsourcedRadio.setSelected(true);
            inhouseOrOutsourced.setText("Company Name");
        }
        else {
            idField.setText(Integer.toString(selectedPart.getId()));
            nameField.setText(selectedPart.getName());
            stockField.setText(Integer.toString(selectedPart.getStock()));
            priceField.setText(Double.toString(selectedPart.getPrice()));
            maxField.setText(Integer.toString(selectedPart.getMax()));
            minField.setText(Integer.toString(selectedPart.getMin()));
            machineOrCompanyField.setText(Integer.toString(((InHouse) selectedPart).getMachineID()));
            inhouseRadio.setSelected(true);
            inhouseOrOutsourced.setText("Machine ID");
        }
    }

    /** Initializes the modify part form
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /** Changes the text to Machine ID when the In-house radio button is selected
     *
     * @param actionEvent
     */
    public void onInHouse(ActionEvent actionEvent) {
        inhouseOrOutsourced.setText("Machine ID");
    }

    /** Changes the text to Company Name when the Outsourced radio button is selected
     *
     * @param actionEvent
     */
    public void onOutsourced(ActionEvent actionEvent) {
        inhouseOrOutsourced.setText("Company Name");
    }

    /** Updates the part and saves the new input information to the application
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

            if (inhouseRadio.isSelected()) {
                int machineID = Integer.parseInt(machineOrCompanyField.getText());
                Part updatedPart = new InHouse(id, name, price, stock, min, max, machineID);
                Inventory.updatePart(currIndex, updatedPart);
            } else {
                String companyName = machineOrCompanyField.getText();
                Part updatedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.updatePart(currIndex, updatedPart);
            }


            Parent root = FXMLLoader.load(getClass().getResource("/hernandez/c482_final/MainScreen.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1250, 500);
            stage.setTitle("CyberOne Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
        catch(NumberFormatException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Incorrect format for input");
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
}
