package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Add product controller that is used by AddProductView.fxml */
public class AddProductController implements Initializable {
    public TextField addProductPartSearch;
    public Button addProductCancelB;
    public Button removeAssociatedPartB;
    public Button addAssociatedPartB;
    public Button addProductSaveB;
    public TableView<Part> addProductPartTable;
    public TableView<Part> addProductAssociatedPartTable;
    public TextField addProductNameTF;
    public TextField addProductInvTF;
    public TextField addProductPriceTF;
    public TextField addProductMinTF;
    public TextField addProductMaxTF;
    public TextField addProductIDTF;
    public TableColumn<Part, Integer> partIDCol;
    public TableColumn<Part, String> partNameCol;
    public TableColumn<Part, Integer> partInvLevelCol;
    public TableColumn<Part, Double> partPricePerUnitCol;
    public TableColumn<Part, Integer> partAIDCol;
    public TableColumn<Part, String> partANameCol;
    public TableColumn<Part, Integer> partAInvLevelCol;
    public TableColumn<Part, Double> partAPricePerUnitCol;

    // Creating stage and scene variables for scene changes
    Stage stage;
    Parent scene;

    ObservableList<Part> productAssociatedParts = FXCollections.observableArrayList();

    /** Initialize method used to initialize any controls */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the part table and columns to retrieve the correct part data
        addProductPartTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        // Initialize the part associated columns to retrieve the correct part data
        partAIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partANameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partAInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partAPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    /** Search for part by id or name */
    public void onAddProductPartSearch(ActionEvent actionEvent) {
        String search = addProductPartSearch.getText();
        ObservableList<Part> parts = Inventory.lookupPart(search);
        if (parts.size() == 0) {
            int partID = Integer.parseInt(search);
            Part id = Inventory.lookupPart(partID);
            if (id != null) {
                parts.add(id);
            }
        }
        addProductPartTable.setItems(parts);
    }

    /** When cancel button is pressed cancel creating a new product */
    public void onAddProductCancelB(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("This will cancel creating this product. Would you like to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/InventoryManagementView.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** When remove associated part button is pressed remove the associated part */
    public void onRemoveAssociatedPartB(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("This will delete the selected associated part. Would you like to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part part = addProductAssociatedPartTable.getSelectionModel().getSelectedItem();
            productAssociatedParts.remove(part);
        }
    }

    /** When add associated part button is pressed add the selected part to the associated part list */
    public void onAddAssociatedPartB(ActionEvent actionEvent) {
        Part part = addProductPartTable.getSelectionModel().getSelectedItem();
        if (part != null && !productAssociatedParts.contains(part)) {
            productAssociatedParts.add(part);
            addProductAssociatedPartTable.setItems(productAssociatedParts);
        }
    }

    /** When save button is pressed create a new product */
    public void onAddProductSaveB(ActionEvent actionEvent) throws IOException {
        try {
            // Create variables to store the text in the addProduct fields
            String name = addProductNameTF.getText();
            int stock = Integer.parseInt(addProductInvTF.getText());
            double price = Double.parseDouble(addProductPriceTF.getText());
            int min = Integer.parseInt(addProductMinTF.getText());
            int max = Integer.parseInt(addProductMaxTF.getText());
            int id = 0;
            for (int i = 0; i < Inventory.getAllProducts().size(); i++) {
                if (id <= Inventory.getAllProducts().get(i).getId()) {
                    id = Inventory.getAllProducts().get(i).getId() + 1;
                }
            }
            if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Inv must be more than Min and less than Max");
                alert.showAndWait();
            }
            else {
                Product product = new Product(id, name, price, stock, min, max);
                for (int i = 0; i < productAssociatedParts.size(); i++) {
                    product.addAssociatedPart(productAssociatedParts.get(i));
                }
                Inventory.addProduct(product);
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/InventoryManagementView.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please enter valid values for each text field");
            alert.showAndWait();
        }
    }

}
