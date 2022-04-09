package controller;

import javafx.fxml.Initializable;
import model.InHouse;
import model.Outsourced;
import model.Inventory;
import model.Part;
import java.io.IOException;
import java.net.URL;
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

public class ModifyPartController implements Initializable {
    Part selectedPart;
    int selectedPartIndex;
    @FXML private RadioButton InHouseRadioButton;
    @FXML private RadioButton OutsourcedRadioButton;
    @FXML private TextField PartIDTextField;
    @FXML private TextField PartNameTextField;
    @FXML private TextField PartInvTextField;
    @FXML private TextField PartPriceTextField;
    @FXML private TextField PartMaxTextField;
    @FXML private TextField PartMinTextField;
    @FXML private TextField MachineIDTextField;
    @FXML private Button CancelButton;
    @FXML private Button SaveButton;
    @FXML private Label MachineCompanyLabel;
    @FXML public void handleInHouseRadioButton() {
        MachineCompanyLabel.setText("Machine ID");
    }
    @FXML public void handleOutsourcedRadioButton() { MachineCompanyLabel.setText("Company Name"); }

    @FXML
    public void handleCancelButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field " +
                "values, do you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {

            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("..\\view\\MainForm.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    @FXML
    public void handleSaveButton(ActionEvent event) throws IOException {

        try {
            if (!(Integer.class.isInstance(Integer.parseInt(PartInvTextField.getText())))){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value you chose needs to be a number.");
                alert.showAndWait();
            } else if (!(Double.class.isInstance(Double.parseDouble(PartPriceTextField.getText())))){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Price value you chose needs to be a number.");
                alert.showAndWait();
            }
            else if (Integer.parseInt(PartMinTextField.getText()) > Integer.parseInt(PartMaxTextField.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Min value cannot be greater than Max value.");
                alert.showAndWait();
            } else if (Integer.parseInt(PartInvTextField.getText()) > Integer.parseInt(PartMaxTextField.getText()) || Integer.parseInt(PartInvTextField.getText()) < Integer.parseInt(PartMinTextField.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory amount must be between minimum and maximum values.");
                alert.showAndWait();
            }
            else {
                int id = selectedPart.getId();
                String name = PartNameTextField.getText();
                int inventory = Integer.parseInt(PartInvTextField.getText());
                double price = Double.parseDouble(PartPriceTextField.getText());
                int max = Integer.parseInt(PartMaxTextField.getText());
                int min = Integer.parseInt(PartMinTextField.getText());

                if (InHouseRadioButton.isSelected()) {

                    int machineID = Integer.parseInt(MachineIDTextField.getText());

                    InHouse inhousePart = new InHouse(id, name, price, inventory, min, max, machineID);
                    Inventory.getPartList().set(selectedPartIndex, inhousePart);
                }

                if (OutsourcedRadioButton.isSelected()) {

                    String companyName = MachineIDTextField.getText();

                    Outsourced outsourcedPart = new Outsourced(id, name, price, inventory, min, max, companyName);
                    Inventory.getPartList().set(selectedPartIndex, outsourcedPart);
                }

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("..\\view\\MainForm.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }
        }
        catch(NumberFormatException e){


            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a valid value for each text field.");
            alert.showAndWait();

        }

    }

    // Possible error: Cannot invoke "javafx.scene.control.TextField.setText(String)" because "this.PartIDTextField" is null
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Part selectedPart = Inventory.getPartList().get(selectedPartIndex);
        PartIDTextField.setText(Integer.toString(selectedPart.getId()));
        PartNameTextField.setText(selectedPart.getName());
        PartInvTextField.setText(Integer.toString(selectedPart.getStock()));
        PartPriceTextField.setText(Double.toString(selectedPart.getPrice()));
        PartMaxTextField.setText(Integer.toString(selectedPart.getMax()));
        PartMinTextField.setText(Integer.toString(selectedPart.getMin()));
        if (selectedPart instanceof InHouse) {
            InHouseRadioButton.setSelected(true);
            MachineIDTextField.setText(Integer.toString(((InHouse) selectedPart).getMachineID()));
        } else {
            OutsourcedRadioButton.setSelected(true);
            MachineIDTextField.setText(((Outsourced) selectedPart).getCompanyName());
        }
    }

//    public void setPart(Part part, int index) {
//        this.selectedPart = part;
//        this.selectedPartIndex = index;
//
//        PartIDTextField.setText(Integer.toString(selectedPartIndex));
//        PartNameTextField.setText(selectedPart.getName());
//        PartInvTextField.setText(Integer.toString(selectedPart.getStock()));
//        PartPriceTextField.setText(Double.toString(selectedPart.getPrice()));
//        PartMaxTextField.setText(Integer.toString(selectedPart.getMax()));
//        PartMinTextField.setText(Integer.toString(selectedPart.getMin()));
//        if(selectedPart instanceof InHouse){
//            InHouse ih = (InHouse) selectedPart;
//            inHouse.setSelected(true);
//            this.inhouseoroutsourced.setText("Machine ID");
//            companyORmachineID.setText(Integer.toString(ih.getMachineID()));
//        }
//        else{
//            OutSourced os = (OutSourced) selectedPart;
//            outsourced.setSelected(true);
//            this.inhouseoroutsourced.setText("Company Name");
//            companyORmachineID.setText(os.getCompanyName());
//        }
//
//        }
//
//        if (part instanceof Outsourced) {
//
//            Outsourced newPart = (Outsourced) part;
//            OutsourcedRadioButton.setSelected(true);
//            MachineCompanyLabel.setText("Company Name");
//            this.PartNameTextField.setText(newPart.getName());
//            this.PartInvTextField.setText((Integer.toString(newPart.getStock())));
//            this.PartPriceTextField.setText((Double.toString(newPart.getPrice())));
//            this.PartMinTextField.setText((Integer.toString(newPart.getMin())));
//            this.PartMaxTextField.setText((Integer.toString(newPart.getMax())));
//            this.MachineIDTextField.setText(newPart.getCompanyName());
//            Inventory.modifyPart(selectedPartIndex, newPart);
//        }
//    }
}