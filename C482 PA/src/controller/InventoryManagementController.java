package controller;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Inventory;
import models.Part;
import models.Product;
import java.io.IOException;
import java.net.URL;


import java.util.Optional;
import java.util.ResourceBundle;

/** Inventory management controller that is used by InventoryManagementView.fxml */
public class InventoryManagementController implements Initializable {
    public TableView<Part> partTable;
    public TableView<Product> productTable;
    public TextField partSearch;
    public TextField productSearch;
    public Button addPartB;
    public Button modifyPartB;
    public Button deletePartB;
    public Button addProductB;
    public Button modifyProductB;
    public Button deleteProductB;
    public TableColumn<Part, Integer> partIDCol;
    public TableColumn<Part, String> partNameCol;
    public TableColumn<Part, Integer> partInvLevelCol;
    public TableColumn<Part, Double> partPricePerUnitCol;
    public TableColumn<Product, Integer> productIDCol;
    public TableColumn<Product, String> productNameCol;
    public TableColumn<Product, Integer> productInvLevelCol;
    public TableColumn<Product, Double> productPricePerUnitCol;
    public Button exitApplicationB;
    public AnchorPane inventoryManagementAP;

    // Creating stage and scene variables for scene changes
    Stage stage;
    Parent scene;

    /** Initialize method used to initialize any controls */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the part table and columns to retrieve the correct part data
        partTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Initialize the product table and columns to retrieve the correct product data
        productTable.setItems(Inventory.getAllProducts());
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** When part search is used search for part id or part name */
    public void onPartSearch(ActionEvent actionEvent) {
        String search = partSearch.getText();
        ObservableList<Part> parts = Inventory.lookupPart(search);
        if (parts.size() == 0) {
            int partID = Integer.parseInt(search);
            Part id = Inventory.lookupPart(partID);
            if (id != null) {
                parts.add(id);
            }
        }
        partTable.setItems(parts);
    }
    /** When product search is used search for product id or product name */
    public void onProductSearch(ActionEvent actionEvent) {
        String search = productSearch.getText();
        ObservableList<Product> products = Inventory.lookupProduct(search);
        if (products.size() == 0) {
            int productID = Integer.parseInt(search);
            Product id = Inventory.lookupProduct(productID);
            if (id != null) {
                products.add(id);
            }
        }
        productTable.setItems(products);
    }
    /** When add part button is selected change to the add part view */
    public void onAddPartB(ActionEvent actionEvent) throws IOException {
        // Change scene
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** When modify part button is selected change to the modify part view and send part information */
    public void onModifyPartB(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPartView.fxml"));
            loader.load();

            ModifyPartController MPCController = loader.getController();
            MPCController.sendPart(partTable.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a part to modify.");
            alert.showAndWait();
        }
    }
    /** When delete part button is selected delete the selected part */
    public void onDeletePartB(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("This will delete the selected part. Would you like to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part selectedPart = partTable.getSelectionModel().getSelectedItem();
            Inventory.deletePart(selectedPart);
        }
    }
    /** When add product button is selected change to the add product view */
    public void onAddProductB(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /** When modify product button is pressed change to the modify product view and send product information */
    public void onModifyProductB(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProductView.fxml"));
            loader.load();

            ModifyProductController MPCController = loader.getController();
            MPCController.sendProduct(productTable.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a product to modify.");
            alert.showAndWait();
        }
    }
    /** When delete product button is selected delete the selected product */
    public void onDeleteProductB(ActionEvent actionEvent) {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct.getAllAssociatedParts().size() != 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("You can not delete a product that has associated parts. Please remove all associated parts before deleting");
            alert.showAndWait();
        }
        else if (selectedProduct.getAllAssociatedParts().size() == 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("This will delete the selected product. Would you like to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                Inventory.getAllProducts().remove(selectedProduct);
                productTable.setItems(Inventory.getAllProducts());
            }
        }
    }
    /** When Exit button is selected exit the application */
    public void onExitApplicationB(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("You are about to exit this application. Would you like to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage) inventoryManagementAP.getScene().getWindow();
            stage.close();
        }
    }
}
