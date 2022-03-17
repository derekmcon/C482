package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.*;

/** This class creates an inventory management application.*/
public class Main extends Application {

    /** Start method that will load InventoryManagementView.fxml */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/InventoryManagementView.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 1000, 650));
        primaryStage.show();
    }

/** Main method which creates placeholder parts and products for testing.
 * FUTURE ENHANCEMENT: Give the user the ability to remove multiple parts at the same time.*/
    public static void main(String[] args) {
        InHouse inHousePart1 = new InHouse(1, "Part1", 9.99, 5, 1, 15, 1);
        InHouse inHousePart2 = new InHouse(2, "Part2", 5.99, 20, 1, 50, 2);
        Outsourced outsourced1 = new Outsourced(3, "Part3", 29.99, 5, 1, 20, "ThisCompanyName");
        Product product1 = new Product(1, "Product1", 12.99, 10, 1, 25);
        Product product2 = new Product(2, "Product2", 19.99, 50, 1, 100);
        Inventory.addPart(inHousePart1);
        Inventory.addPart(inHousePart2);
        Inventory.addPart((outsourced1));
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);

        // Launches the program. Create everything above this line
        launch(args);
    }

}
