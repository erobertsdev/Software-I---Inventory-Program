package controller;

import javafx.fxml.Initializable;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

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
    private Button CancelButton;
    @FXML
    private Button SaveButton;
    @FXML
    private Label MachineCompanyLabel;
    private Part selectedPart;
    private int partID;

    @FXML
    public void handleInHouseRadioButton() {
        MachineCompanyLabel.setText("Machine ID");
    }

    @FXML
    public void handleOutsourcedRadioButton() {
        MachineCompanyLabel.setText("Company Name");
    }


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

    @FXML
    public void handleSaveButton(ActionEvent event) throws IOException {
        int partInventory = Integer.parseInt(PartInvTextField.getText());
        int partMin = Integer.parseInt(PartMinTextField.getText());
        int partMax = Integer.parseInt(PartMaxTextField.getText());
        if (MainController.confirmDialog("Confirm Changes", "Save this part?"))
            if (partMax < partMin) {
                MainController.infoDialog("Error", "Max less than Min", "Max must be greater than Min.");
            } else if (partInventory < partMin || partInventory > partMax) {
                MainController.infoDialog("Error", "Inventory Number Incorrect", "Inventory must be between Min and Max.");
            } else {
                int id = Integer.parseInt(PartIDTextField.getText());
                String name = PartNameTextField.getText();
                double price = Double.parseDouble(PartPriceTextField.getText());
                int stock = Integer.parseInt(PartInvTextField.getText());
                int min = Integer.parseInt(PartMinTextField.getText());
                int max = Integer.parseInt(PartMaxTextField.getText());
                Stage stage;
                Object scene;
                if (InHouseRadioButton.isSelected()) {
                    try {
                        int machineID = Integer.parseInt(MachineIDTextField.getText());
                        InHouse temp = new InHouse(id, name, price, stock, min, max, machineID);
                        Inventory.updatePart(partID, temp);
                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\MainForm.fxml")));
                        stage.setTitle("Inventory Management System");
                        stage.setScene(new Scene((Parent) scene));
                        stage.show();
                    } catch (NumberFormatException e) {
                        MainController.infoDialog("Error", "Invalid Machine ID", "Machine ID can only contain numbers.");
                    }
                } else {
                    String companyName = MachineIDTextField.getText();
                    Outsourced temp = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.updatePart(partID, temp);
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\MainForm.fxml")));
                    stage.setTitle("Inventory Management System");
                    stage.setScene(new Scene((Parent) scene));
                    stage.show();
                }
            }
    }

    // Possible error: Cannot invoke "javafx.scene.control.TextField.setText(String)" because "this.PartIDTextField" is null
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedPart = getSelectedPart();

        if (selectedPart instanceof InHouse) {
//             inHouseTgl.setSelected(true);
            MachineCompanyLabel.setText("Machine ID");
            MachineIDTextField.setText(String.valueOf(((InHouse) selectedPart).getMachineID()));
        }

        if (selectedPart instanceof Outsourced) {
//             outsourcedTgl.setSelected(true);
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