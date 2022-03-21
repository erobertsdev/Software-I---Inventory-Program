package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class AddPartController {

    /** Declare variables for UI components **/

    @FXML private RadioButton InHouseRadioButton;
    @FXML private RadioButton OutsourcedRadioButton;
    @FXML private TextField IDTextField;
    @FXML private TextField NameTextField;
    @FXML private TextField InvTextField;
    @FXML private TextField PriceTextField;
    @FXML private TextField MaxTextField;
    @FXML private TextField MinTextField;
    @FXML private TextField MachineIDTextField;
    @FXML private Label MachineCompanyLabel;
    @FXML private Button SaveButton;
    @FXML private Button CancelButton;

    /** Event Handlers **/
    @FXML
    public void handleInHouseRadioButton() {
        MachineCompanyLabel.setText("Machine ID");
    }

    public void handleOutsourcedRadioButton() {
        MachineCompanyLabel.setText("Company Name");
    }

    public void handleIDTextField() {

    }

    public void handleNameTextField() {

    }

    public void handleInvTextField() {

    }

    public void handlePriceTextField() {

    }

    public void handleMaxTextField() {

    }

    public void handleMachineIDTextField() {

    }

    public void handleMinTextField() {

    }

    public void handleSaveButton(ActionEvent event) throws IOException {
        String partName = NameTextField.getText();
        String partInv = InvTextField.getText();
        String partPrice = PriceTextField.getText();
        String partMax = MaxTextField.getText();
        String partMin = MinTextField.getText();
        String partID = MachineIDTextField.getText();

        int ID = 0;
        ObservableList<Part> allParts = Inventory.getPartList();
        for (Part part : allParts) {
            if (part.getId() > ID)
                ID = part.getId();
        }

        try {
            if (!(Integer.class.isInstance(Integer.parseInt(partInv)))){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Value must be a number.");
                alert.showAndWait();
            } else if (!(Double.class.isInstance(Double.parseDouble(partPrice)))){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Value must be a number.");
                alert.showAndWait();
            }
            else if (Integer.parseInt(MinPartText.getText()) > Integer.parseInt(MaxPartText.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Min value cannot be greater than Max value.");
                alert.showAndWait();
            } else if (Integer.parseInt(InventoryAddPartText.getText()) > Integer.parseInt(MaxPartText.getText()) || Integer.parseInt(InventoryAddPartText.getText()) < Integer.parseInt(MinPartText.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory amount must be between minimum and maximum values.");
                alert.showAndWait();
            }
            else {
                IDAddPartText.setText(String.valueOf(++ID));
                int id = Integer.parseInt(IDAddPartText.getText());
                String name = NameAddPartText.getText();
                int inventory = Integer.parseInt(InventoryAddPartText.getText());
                double priceCost = Double.parseDouble(PriceCostAddPartText.getText());
                int max = Integer.parseInt(MaxPartText.getText());
                int min = Integer.parseInt(MinPartText.getText());

                if (InHouseRadioButton.isSelected()) {
                    int machineID = Integer.parseInt(MachineIDAddPartText.getText());
                    InHouse addInHousePart = new InHouse(id, name, priceCost, inventory, min, max
                            , machineID);

                    Inventory.addPart(addInHousePart);
                }
                if (OutsourcedRadioButton.isSelected()) {
                    String companyName = MachineIDAddPartText.getText();
                    Outsourced addOutsourcedPart = new Outsourced(id, name, priceCost, inventory,
                            min, max, companyName);

                    Inventory.addPart(addOutsourcedPart);
                }
                Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }

        }
        catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("Some fields contain incompatible values. Please double check your selections.");
            alert.showAndWait();
        }
    }


    public void handleCancelButton() {

    }

}