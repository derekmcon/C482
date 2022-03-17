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
import models.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Modify part controller that is used by ModifyPartView.fxml */
public class ModifyPartController implements Initializable {
    public TextField modifyPartIDTF;
    public TextField modifyPartNameTF;
    public TextField modifyPartInvTF;
    public TextField modifyPartPriceTF;
    public TextField modifyPartMinTF;
    public TextField modifyPartMaxTF;
    public TextField modifyPartMachineIDTF;
    public Label modifyPartMachineIDL;
    public RadioButton modifyPartInHouseRB;
    public RadioButton modifyPartOutsourcedRB;
    public Button modifyPartSaveB;
    public Button modifyPartCancelB;

    // Creating stage and scene variables for scene changes
    Stage stage;
    Parent scene;

    // Will replace old part with new part using Inventory.updatePart() index gets set in sendPart() below
    int index;

    /** Initialize method used to initialize any controls */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    /** When InHouse radio button is selected set the part machine label to machine id */
    public void onModifyPartInHouseRB(ActionEvent actionEvent) {
        modifyPartMachineIDL.setText("Machine ID");
    }
    /** When Outsourced radio button is selected set the part machine label to company name */
    public void onModifyPartOutsourcedRB(ActionEvent actionEvent) {
        modifyPartMachineIDL.setText("Company Name");
    }
    /** When the save button is pressed save the modifications made to the part */
    public void onModifyPartSaveB(ActionEvent actionEvent) throws IOException {
        try {
            // Create variables to store the text in the addPart fields
            int id = Integer.parseInt(modifyPartIDTF.getText());
            String name = modifyPartNameTF.getText();
            int stock = Integer.parseInt(modifyPartInvTF.getText());
            double price = Double.parseDouble(modifyPartPriceTF.getText());
            int min = Integer.parseInt(modifyPartMinTF.getText());
            int max = Integer.parseInt(modifyPartMaxTF.getText());
            if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Inv must be more than Min and less than Max");
                alert.showAndWait();
            }
            else {
                boolean inHouse;
                if (modifyPartInHouseRB.isSelected()) {
                    inHouse =  true;
                    int machineID = Integer.parseInt(modifyPartMachineIDTF.getText());
                    InHouse inHouseUpdate = new InHouse(id, name, price, stock, min, max, machineID);
                    Inventory.updatePart(index, inHouseUpdate);
                }
                else if (modifyPartOutsourcedRB.isSelected()) {
                    inHouse = false;
                    String companyName = modifyPartMachineIDTF.getText();
                    Outsourced outsourcedUpdate = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.updatePart(index, outsourcedUpdate);
                }
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
    /** When the cancel button is pressed cancel modifying current part  */
    public void onModifyPartCancelB(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setContentText("This will cancel modifying this part. Would you like to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/InventoryManagementView.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    /** Retrieves which part we need to modify from the InventoryManagementController */
    public void sendPart(Part part) {
        index = Inventory.getAllParts().indexOf(part);
        modifyPartIDTF.setText(String.valueOf(part.getId()));
        modifyPartNameTF.setText(part.getName());
        modifyPartInvTF.setText(String.valueOf(part.getStock()));
        modifyPartPriceTF.setText(String.valueOf(part.getPrice()));
        modifyPartMinTF.setText(String.valueOf(part.getMin()));
        modifyPartMaxTF.setText(String.valueOf(part.getMax()));
        if (part instanceof InHouse) {
            modifyPartInHouseRB.setSelected(true);
            modifyPartMachineIDL.setText("Machine ID");
            modifyPartMachineIDTF.setText(String.valueOf(((InHouse) part).getMachineID()));
        }
        if (part instanceof Outsourced) {
            modifyPartOutsourcedRB.setSelected(true);
            modifyPartMachineIDL.setText("Company Name");
            modifyPartMachineIDTF.setText(((Outsourced) part).getCompanyName());
        }
    }

}
