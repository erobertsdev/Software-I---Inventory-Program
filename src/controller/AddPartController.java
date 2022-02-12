package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

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

    @FXML
    public void handleInHouseRadioButton() {
        label.setText("OK Button pressed");
    }
}