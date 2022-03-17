package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.InHouse;
import models.Inventory;
import models.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Add part controller that is used by AddPartView.fxml */
public class AddPartController implements Initializable {
    public Label addPartMachineIDL;
    public TextField addPartIDTF;
    public TextField addPartNameTF;
    public TextField addPartInvTF;
    public TextField addPartPriceTF;
    public TextField addPartMinTF;
    public TextField addPartMaxTF;
    public TextField addPartMachineIDTF;
    public RadioButton addPartInHouseRB;
    public RadioButton addPartOutsourcedRB;
    public Button addPartSaveB;
    public Button addPartCancelB;

    // Creating stage and scene variables for scene changes
    Stage stage;
    Parent scene;

    /** Initialize method used to initialize any controls */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    /** When InHouse radio button is selected set the part machine label to machine id */
    public void onAddPartInHouseRB(ActionEvent actionEvent) {
        addPartMachineIDL.setText("Machine ID");
    }
    /** When Outsourced radio button is selected set the part machine label to company name */
    public void onAddPartOutsourcedRB(ActionEvent actionEvent) {
        addPartMachineIDL.setText("Company Name");
    }
    /** When the save button is pressed save the new part */
    public void onAddPartSaveB(ActionEvent actionEvent) throws IOException {
        try {
            // Create variables to store the text in the addPart fields
            int id = 0;
            String name = addPartNameTF.getText();
            int stock = Integer.parseInt(addPartInvTF.getText());
            double price = Double.parseDouble(addPartPriceTF.getText());
            int min = Integer.parseInt(addPartMinTF.getText());
            int max = Integer.parseInt(addPartMaxTF.getText());
            for (int i = 0; i < Inventory.getAllParts().size(); i++) {
                if (id <= Inventory.getAllParts().get(i).getId()) {
                    id = Inventory.getAllParts().get(i).getId() + 1;
                }
            }
            if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Inv must be more than Min and less than Max");
                alert.showAndWait();
            }
            else {
                boolean inHouse;
                if (addPartInHouseRB.isSelected()) {
                    inHouse = true;
                    int machineID = Integer.parseInt(addPartMachineIDTF.getText());
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
                }
                else if (addPartOutsourcedRB.isSelected()) {
                    inHouse = false;
                    String companyName = addPartMachineIDTF.getText();
                    Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                }
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

    /** When the cancel button is pressed cancel creating a new part */
    public void onAddPartCancelB(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("This will cancel creating this part. Would you like to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/InventoryManagementView.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

}


