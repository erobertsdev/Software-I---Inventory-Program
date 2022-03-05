package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

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
    }

    public void handleCancelButton() {

    }

}