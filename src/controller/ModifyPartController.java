package controller;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static controller.MainController.getSelectedPart;
import static controller.MainController.getSelectedPartIndex;

/** Controller for the part modification screen. */
public class ModifyPartController implements Initializable {

    @FXML
    private RadioButton InHouseRadioButton;
    @FXML
    private RadioButton OutsourcedRadioButton;
    @FXML
    private TextField PartIDTextField;
    @FXML
    private TextField PartNameTextField;
    @FXML
    private TextField PartInvTextField;
    @FXML
    private TextField PartPriceTextField;
    @FXML
    private TextField PartMaxTextField;
    @FXML
    private TextField PartMinTextField;
    @FXML
    private TextField MachineIDTextField;
    @FXML
    private Label MachineCompanyLabel;
    private Part selectedPart;
    private static int selectedPartIndex;
    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    /** Method to check if a value is numerical.
     * @param strNum Input to be checked. */
    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return true;
        }
        return !pattern.matcher(strNum).matches();
    }

    /** Method to handle InHouse radio button. */
    @FXML
    public void handleInHouseRadioButton() {
        InHouseRadioButton.setSelected(true);
        OutsourcedRadioButton.setSelected(false);
        MachineCompanyLabel.setText("Machine ID");
    }

    /** Method to handle outsourced radio button. */
    @FXML
    public void handleOutsourcedRadioButton() {
        InHouseRadioButton.setSelected(false);
        OutsourcedRadioButton.setSelected(true);
        MachineCompanyLabel.setText("Company Name");
    }

    /** Method to handle cancelling modification of a part.
     * @param event Cancel button event.*/
    @FXML
    public void handleCancelButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Cancel Changes?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\MainForm.fxml")));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    /** Method to handle saving of modification to a part.
     * @param event Save button event. */
    @FXML
    public void handleSaveButton(ActionEvent event) throws IOException {
        int partId = selectedPart.getId();
        int partIndex = selectedPartIndex;
        String partName = PartNameTextField.getText();
        String partPrice = PartPriceTextField.getText();
        String partInv = PartInvTextField.getText();
        String partMin = PartMinTextField.getText();
        String partMax = PartMaxTextField.getText();
        int machineId;
        String companyName;
        if (partName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Part name cannot be empty.");
            Optional<ButtonType> result = alert.showAndWait();
        } else if (isNumeric(partInv)) {
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
            if (InHouseRadioButton.isSelected()) {
                try {
                    machineId = Integer.parseInt(MachineIDTextField.getText());
                    InHouse modifyInHouse = new InHouse(partId, partName, Double.parseDouble(partPrice), Integer.parseInt(partInv), Integer.parseInt(partMin), Integer.parseInt(partMax), machineId);
                    Inventory.updatePart(partIndex, modifyInHouse);
                    Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\MainForm.fxml")));
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please double check all inputs.");
                    Optional<ButtonType> result = alert.showAndWait();
                }
            }
            if (OutsourcedRadioButton.isSelected()) {
                companyName = MachineIDTextField.getText();
                Outsourced newOutsourced = new Outsourced(partId, partName, Double.parseDouble(partPrice), Integer.parseInt(partInv), Integer.parseInt(partMin), Integer.parseInt(partMax), companyName);
                Inventory.updatePart(partIndex, newOutsourced);
                Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\MainForm.fxml")));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    /** Method to initialize the GUI for part modification.
     * RUNTIME ERROR: Cannot invoke "javafx.scene.control.TextField.setText(String)" because "this.PartIDTextField" is null
     * FIX: the appropriate fx:id had not been set in the ModifyPartForm.fxml file. */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedPart = getSelectedPart();
        selectedPartIndex = getSelectedPartIndex();
        if (selectedPart instanceof InHouse) {
            InHouseRadioButton.setSelected(true);
            MachineCompanyLabel.setText("Machine ID");
            MachineIDTextField.setText(String.valueOf(((InHouse) selectedPart).getMachineID()));
        }
        if (selectedPart instanceof Outsourced) {
            OutsourcedRadioButton.setSelected(true);
            MachineCompanyLabel.setText("Company Name");
            MachineIDTextField.setText(String.valueOf(((Outsourced) selectedPart).getCompanyName()));
        }
        PartIDTextField.setText(String.valueOf(selectedPart.getId()));
        PartNameTextField.setText(String.valueOf(selectedPart.getName()));
        PartPriceTextField.setText(String.valueOf(selectedPart.getPrice()));
        PartInvTextField.setText(String.valueOf(selectedPart.getStock()));
        PartMaxTextField.setText(String.valueOf(selectedPart.getMax()));
        PartMinTextField.setText(String.valueOf(selectedPart.getMin()));
    }
}