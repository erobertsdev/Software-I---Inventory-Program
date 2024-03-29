package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/** This class adds a part to the inventory. */
/** @author Elias Adams-Roberts */
public class AddPartController implements Initializable {

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

    /** Sets MachineCompanyLabel label to 'Machine ID'. */
    @FXML
    public void handleInHouseRadioButton() {
        MachineCompanyLabel.setText("Machine ID");
    }
    /** Sets MachineCompanyLabel label to 'Company Name'. */
    public void handleOutsourcedRadioButton() {
        MachineCompanyLabel.setText("Company Name");
    }

    /** Sets ID for a new part. */
    private static int newID = Inventory.newPartID();

    /** Regex to check if input value is numerical. */
    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    /** Method to check if a value is numerical.
     * @param strNum input value to be checked.
     * @return True if strNum is a number, false otherwise.*/
    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return true;
        }
        return !pattern.matcher(strNum).matches();
    }

    /** Method to save a new part.
     * @param event Save part event.
     * @throws NumberFormatException if input values are invalid. */
    public void handleSaveButton(ActionEvent event) throws IOException {
        String partID = IDTextField.getText();
        String partName = NameTextField.getText();
        String partInv = InvTextField.getText();
        String partPrice = PriceTextField.getText();
        String partMax = MaxTextField.getText();
        String partMin = MinTextField.getText();
        String machineIDText = MachineIDTextField.getText();

        try {
            if (isNumeric(partInv)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inv must be a number.");
                alert.showAndWait();
            } else if (isNumeric(partPrice)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Price is invalid.");
                alert.showAndWait();
            } else if (Integer.parseInt(partMin) > Integer.parseInt(partMax)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "MIN value can't be greater than MAX value.");
                alert.showAndWait();
            } else if (Integer.parseInt(partInv) > Integer.parseInt(partMax) || Integer.parseInt(partInv) < Integer.parseInt(partMin)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory amount must be between minimum and maximum values.");
                alert.showAndWait();
            } else {
                int id = newID;
                int inventory = Integer.parseInt(partInv);
                double cost = Double.parseDouble(partPrice);
                int max = Integer.parseInt(partMax);
                int min = Integer.parseInt(partMin);

                if (InHouseRadioButton.isSelected()) {
                    int machineID = Integer.parseInt(machineIDText);
                    InHouse addInHousePart = new InHouse(id, partName, cost, inventory, min, max, machineID);
                    addInHousePart.setId(Inventory.newPartID());
                    Inventory.addPart(addInHousePart);
                }
                if (OutsourcedRadioButton.isSelected()) {
                    Outsourced addOutsourcedPart = new Outsourced(id, partName, cost, inventory,
                            min, max, machineIDText);
                    addOutsourcedPart.setId(Inventory.newPartID());
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

    /** Method to cancel the addition of a new part.
     * @param event Cancel adding part event.
     * @throws IOException if an error occurs. */
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

    /** Initializes GUI for adding a part. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    IDTextField.setText(String.valueOf("Auto-generated"));
    }
}