package controller;

import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import static controller.MainController.getSelectedPartIndex;

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
    private static int selectedPartIndex;
    private int partID;

    @FXML
    public void handleInHouseRadioButton() {
        MachineCompanyLabel.setText("Machine ID");
    }

    @FXML
    public void handleOutsourcedRadioButton() {
        MachineCompanyLabel.setText("Company");
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
        int partId = selectedPart.getId();
        int partIndex = selectedPartIndex;
        String partName = PartNameTextField.getText();
        Double partPrice = Double.parseDouble(PartPriceTextField.getText());
        int partInv = Integer.parseInt(PartInvTextField.getText());
        int partMin = Integer.parseInt(PartMinTextField.getText());
        int partMax = Integer.parseInt(PartMaxTextField.getText());
        int machineId;
        String companyName;
        if (partName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Part name cannot be empty.");
            Optional<ButtonType> result = alert.showAndWait();
        } else if ((partMin < partMax) && (partInv > partMin) && (partInv < partMax)) {
            if (InHouseRadioButton.isSelected()) {
                try {
                    machineId = Integer.parseInt(MachineIDTextField.getText());
                    InHouse modifyInHouse = new InHouse(partId, partName, partPrice, partInv, partMin, partMax, machineId);
                    Inventory.updatePart(partIndex, modifyInHouse);
                    Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\MainForm.fxml")));
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (OutsourcedRadioButton.isSelected()) {
                companyName = MachineIDTextField.getText();
                Outsourced newOutsourced = new Outsourced(partId, partName, partPrice, partInv, partMin, partMax, companyName);
                Inventory.updatePart(partIndex, newOutsourced);
                Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\MainForm.fxml")));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        }
    }

    // Possible error: Cannot invoke "javafx.scene.control.TextField.setText(String)" because "this.PartIDTextField" is null
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
            MachineCompanyLabel.setText("Company");
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