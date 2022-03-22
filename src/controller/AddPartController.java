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
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddPartController {

    /**
     * Declare variables for UI components
     **/

    @FXML
    private RadioButton InHouseRadioButton;
    @FXML
    private RadioButton OutsourcedRadioButton;
    @FXML
    private TextField IDTextField;
    @FXML
    private TextField NameTextField;
    @FXML
    private TextField InvTextField;
    @FXML
    private TextField PriceTextField;
    @FXML
    private TextField MaxTextField;
    @FXML
    private TextField MinTextField;
    @FXML
    private TextField MachineIDTextField;
    @FXML
    private Label MachineCompanyLabel;
    @FXML
    private Button SaveButton;
    @FXML
    private Button CancelButton;

    /**
     * Event Handlers
     **/
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

    public static int generateID() {
        int newID = 1;
        for (int i = 0; i < Inventory.getPartList().size(); i++) {
            newID++;
        }
        return newID;
    }

    // Regex to check if certain inputs are numbers
    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    public void handleSaveButton(ActionEvent event) throws IOException {
        String partID = IDTextField.getText();
        String partName = NameTextField.getText();
        String partInv = InvTextField.getText();
        String partPrice = PriceTextField.getText();
        String partMax = MaxTextField.getText();
        String partMin = MinTextField.getText();
        String machineIDText = MachineIDTextField.getText();

        try {
            if (!isNumeric(partInv)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inv must be a number.");
                alert.showAndWait();
            } else if (!isNumeric(partPrice)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Price is invalid.");
                alert.showAndWait();
            } else if (Integer.parseInt(partMin) > Integer.parseInt(partMax)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "MIN value can't be greater than MAX value.");
                alert.showAndWait();
            } else if (Integer.parseInt(partInv) > Integer.parseInt(partMax) || Integer.parseInt(partInv) < Integer.parseInt(partMin)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory amount must be between minimum and maximum values.");
                alert.showAndWait();
            } else {
                int id = generateID();
                int inventory = Integer.parseInt(partInv);
                double cost = Double.parseDouble(partPrice);
                int max = Integer.parseInt(partMax);
                int min = Integer.parseInt(partMin);

                if (InHouseRadioButton.isSelected()) {
                    int machineID = Integer.parseInt(machineIDText);
                    InHouse addInHousePart = new InHouse(id, partName, cost, inventory, min, max, machineID);

                    Inventory.addPart(addInHousePart);
                }
                if (OutsourcedRadioButton.isSelected()) {
                    Outsourced addOutsourcedPart = new Outsourced(id, partName, cost, inventory,
                            min, max, machineIDText);

                    Inventory.addPart(addOutsourcedPart);
                }
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\MainForm.fxml")));
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("Error adding part. Please check inputs for errors.");
            alert.showAndWait();
        }
    }

    public void handleCancelButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\MainForm.fxml")));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }
}