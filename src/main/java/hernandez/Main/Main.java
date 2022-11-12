package hernandez.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

//Java docs have been attached in a separate folder titled "Javadocs"

/** Main class that inputs the test data and launches the application. */
public class Main extends Application {

    /** Loads the Main Screen of the application */
    @Override
    public void start(Stage mainStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/hernandez/c482_final/MainScreen.fxml"));
        Scene scene = new Scene(root, 1250, 500);
        mainStage.setTitle("CyberOne Inventory Management System");
        mainStage.setScene(scene);
        mainStage.show();
    }

    /** Adds test data into Part and Product classes. */
    public static void addTestData() {

        int partID;
        int productID;

        partID = Inventory.newPartID();
        Part ram1 = new InHouse(partID, "CyberOne 8GB", 50.99, 41, 0, 100, 155);
        Inventory.addPart(ram1);

        partID = Inventory.newPartID();
        Part case1 = new InHouse(partID, "CyberOne Case", 79.99, 7, 0,30, 11);
        Inventory.addPart(case1);

        partID = Inventory.newPartID();
        Part psu1 = new Outsourced(partID, "HX750", 100.99, 3, 0, 50, "Corsair");
        Inventory.addPart(psu1);

        partID = Inventory.newPartID();
        Part gpu1 = new Outsourced(partID, "RTX 3070", 639.99, 2, 0, 10, "MSI");
        Inventory.addPart(gpu1);

        productID = Inventory.newProductID();
        Product cpu1 = new Product(productID, "CyberOne Elite X-33", 1299.99, 6, 0, 20);
        Inventory.addProduct(cpu1);

        productID = Inventory.newProductID();
        Product cpu2 = new Product(productID, "CyberOne Premium X-11", 899.99, 11, 0, 20);
        Inventory.addProduct(cpu2);

    }

    /** Adds test data to the application and launches the application */
    public static void main(String[] args) {

        addTestData();

        launch();

    }
}