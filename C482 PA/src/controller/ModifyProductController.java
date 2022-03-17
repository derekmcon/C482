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
import models.Inventory;
import models.Part;
import models.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Modify product controller that is used by ModifyProductView.fxml */
public class ModifyProductController implements Initializable {
    public TextField modifyProductPartSearch;
    public TableView<Part> modifyProductPartTable;
    public TableView<Part> modifyProductAssociatedPartTable;
    public Button modifyProductCancelB;
    public Button modifyRemoveAssociatedPartB;
    public Button modifyAddAssociatedPartB;
    public Button modifyProductSaveB;
    public TextField modifyProductIDTF;
    public TextField modifyProductNameTF;
    public TextField modifyProductInvTF;
    public TextField modifyProductPriceTF;
    public TextField modifyProductMinTF;
    public TextField modifyProductMaxTF;
    public TableColumn<Part, Integer> partIDCol;
    public TableColumn<Part, String> partNameCol;
    public TableColumn<Part, Integer> partInvLevelCol;
    public TableColumn<Part, Double> partPricePerUnitCol;
    public TableColumn<Part, Integer> partAIDCol;
    public TableColumn<Part, String> partANameCol;
    public TableColumn<Part, Integer> partAInvLevelCol;
    public TableColumn<Part, Double> partAPricePerUnitCol;

    // Creating stage and scene variables for scene changes
    Parent scene;
    Stage stage;

    // Will replace old product with new product using Inventory.updateProduct() index gets set in sendProduct() below
    int index;

    ObservableList<Part> productAssociatedParts = FXCollections.observableArrayList();

    /** Initialize method used to initialize any controls */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the part table and columns to retrieve the correct part data
        modifyProductPartTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        // Initialize the part associated table columns to retrieve the correct part data

        partAIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partANameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partAInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partAPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /** Search for part by id or name */
    public void onModifyProductPartSearch(ActionEvent actionEvent) {
        String search = modifyProductPartSearch.getText();
        ObservableList<Part> parts = Inventory.lookupPart(search);
        if (parts.size() == 0) {
            int partID = Integer.parseInt(search);
            Part id = Inventory.lookupPart(partID);
            if (id != null) {
                parts.add(id);
            }
        }
        modifyProductPartTable.setItems(parts);
    }

    /** When cancel button is pressed cancel modifying the current product */
    public void onModifyProductCancelB(ActionEvent actionEvent) throws IOException {
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/InventoryManagementView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** When remove associated part button is pressed remove the associated part */
    public void onModifyRemoveAssociatedPartB(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("This will delete the selected associated part. Would you like to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part part = (Part) modifyProductAssociatedPartTable.getSelectionModel().getSelectedItem();
            productAssociatedParts.remove(part);
            modifyProductAssociatedPartTable.setItems(productAssociatedParts);
        }
    }

    /** When add associated part button is pressed add the selected part to the associated part list */
    public void onModifyAddAssociatedPartB(ActionEvent actionEvent) {
        Part part = (Part)(modifyProductPartTable.getSelectionModel().getSelectedItem());
        if (part != null && !productAssociatedParts.contains(part)) {
            productAssociatedParts.add(part);
            modifyProductAssociatedPartTable.setItems(productAssociatedParts);
        }
    }

    /** When save button is pressed save the modified product */
    public void onModifyProductSaveB(ActionEvent actionEvent) throws IOException {
        try {
            // Create variables to store the text in the addProduct fields
            int id = Integer.parseInt(modifyProductIDTF.getText());
            String name = modifyProductNameTF.getText();
            int stock = Integer.parseInt(modifyProductInvTF.getText());
            double price = Double.parseDouble(modifyProductPriceTF.getText());
            int min = Integer.parseInt(modifyProductMinTF.getText());
            int max = Integer.parseInt(modifyProductMaxTF.getText());
            if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Inv must be more than Min and less than Max");
                alert.showAndWait();
            }
            else {
                Product product = new Product(id, name, price, stock, min, max);
                for (int i=0; i < productAssociatedParts.size(); i++) {
                    product.addAssociatedPart(productAssociatedParts.get(i));
                }
                Inventory.updateProduct(index, product);
                stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
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

    /** Retrieves which product we need to modify from the InventoryManagementController.
     * LOGICAL ERROR: Not setting modifyProductAssociatedPartTable.setItems(product.getAllAssociatedParts()); Leading to the Associated Part Table not being filled with associated parts when needed.*/
    public void sendProduct(Product product) {
        index = Inventory.getAllProducts().indexOf(product);
        modifyProductIDTF.setText(String.valueOf(product.getId()));
        modifyProductNameTF.setText(product.getName());
        modifyProductInvTF.setText(String.valueOf(product.getStock()));
        modifyProductPriceTF.setText(String.valueOf(product.getPrice()));
        modifyProductMinTF.setText(String.valueOf(product.getMin()));
        modifyProductMaxTF.setText(String.valueOf(product.getMax()));
        modifyProductAssociatedPartTable.setItems(product.getAllAssociatedParts());
        productAssociatedParts.addAll(product.getAllAssociatedParts());
    }

}
