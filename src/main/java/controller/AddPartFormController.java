package controller;

import javafx.event.ActionEvent;
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

/** Loads the form to add a part to the application */
public class AddPartFormController implements Initializable {
    public RadioButton inhouseRadio;
    public RadioButton outsourcedRadio;
    public Label inhouseOrOutsourced;
    public Button addPartSave;
    public Button addPartCancel;
    public TextField nameText;
    public TextField stockField;
    public TextField priceField;
    public TextField maxField;
    public TextField minField;
    public TextField machineOrCompanyField;

    /** Initializes the add part form */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /** Changes the text to Machine ID when the In-house radio button is selected
     * @param actionEvent  */
    public void onInHouse(ActionEvent actionEvent) {
        inhouseOrOutsourced.setText("Machine ID");
    }

    /** Changes the text to Company Name  when the Outsourced radio button is selected
     * @param actionEvent  */
    public void onOutsourced(ActionEvent actionEvent) {
        inhouseOrOutsourced.setText("Company Name");
    }

    /** When the save button is selected, all the user input is added as a new Part
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
            int ID = Inventory.newPartID();
            String name = nameText.getText();
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
                Part newPart = new InHouse(ID, name, price, stock, min, max, machineID);
                Inventory.addPart(newPart);
            } else {
                String companyName = machineOrCompanyField.getText();
                Part newPart = new Outsourced(ID, name, price, stock, min, max, companyName);
                Inventory.addPart(newPart);
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
}
