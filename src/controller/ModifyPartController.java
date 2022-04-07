package controller;

import model.InHouse;
import model.Outsourced;
import model.Inventory;
import model.Part;
import java.io.IOException;
import java.util.Optional;
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

public class ModifyPartController {
    Part selectedPart;
    int selectedPartIndex;
    @FXML private RadioButton InHouseRadioButton;
    @FXML private RadioButton OutsourcedRadioButton;
    @FXML private TextField NameInhouseModifyPartText;
    @FXML private TextField InventoryInhouseModifyPartText;
    @FXML private TextField PriceCostInhouseModifyPartText;
    @FXML private TextField MaxInhouseModifyPartText;
    @FXML private TextField MinInhouseModifyPartText;
    @FXML private TextField MachineIDInhouseModifyPartText;
    @FXML private Button CancelButton;
    @FXML private Button SaveButton;
    @FXML private Label MachineCompanyLabel;
    @FXML public void handleInHouseRadioButton() {
        MachineCompanyLabel.setText("Machine ID");
    }
    @FXML public void handleOutsourcedRadioButton() { MachineCompanyLabel.setText("Company Name"); }
    @FXML
    void InventoryInhouseModifyPartText(ActionEvent event) {
    }
    @FXML
    void MachineIDInhouseModifyPartText(ActionEvent event) {
    }
    @FXML
    void MaxInhouseModifyPartText(ActionEvent event) {
    }
    @FXML
    void MinInhouseModifyPartText(ActionEvent event) {
    }
    @FXML
    void NameInhouseModifyPartText(ActionEvent event) {
    }
    @FXML
    public void PriceCostInhouseModifyPartText(ActionEvent event) {
    }

    @FXML
    public void cancelHandler(ActionEvent event) throws IOException {
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
    public void saveHandler(ActionEvent event) throws IOException {

        try {
            if (!(Integer.class.isInstance(Integer.parseInt(InventoryInhouseModifyPartText.getText())))){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value you chose needs to be a number.");
                alert.showAndWait();
            } else if (!(Double.class.isInstance(Double.parseDouble(PriceCostInhouseModifyPartText.getText())))){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Price value you chose needs to be a number.");
                alert.showAndWait();
            }
            else if (Integer.parseInt(MinInhouseModifyPartText.getText()) > Integer.parseInt(MaxInhouseModifyPartText.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Min value cannot be greater than Max value.");
                alert.showAndWait();
            } else if (Integer.parseInt(InventoryInhouseModifyPartText.getText()) > Integer.parseInt(MaxInhouseModifyPartText.getText()) || Integer.parseInt(InventoryInhouseModifyPartText.getText()) < Integer.parseInt(MinInhouseModifyPartText.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory amount must be between minimum and maximum values.");
                alert.showAndWait();
            }
            else {
                int id = selectedPart.getId();
                String name = NameInhouseModifyPartText.getText();
                int inventory = Integer.parseInt(InventoryInhouseModifyPartText.getText());
                double price = Double.parseDouble(PriceCostInhouseModifyPartText.getText());
                int max = Integer.parseInt(MaxInhouseModifyPartText.getText());
                int min = Integer.parseInt(MinInhouseModifyPartText.getText());

                if (InHouseRadioButton.isSelected()) {

                    int machineID = Integer.parseInt(MachineIDInhouseModifyPartText.getText());

                    InHouse inhousePart = new InHouse(id, name, price, inventory, min, max, machineID);
                    Inventory.getPartList().set(selectedPartIndex, inhousePart);
                }

                if (OutsourcedRadioButton.isSelected()) {

                    String companyName = MachineIDInhouseModifyPartText.getText();

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

    public void setPart(Part part, int index) {
        selectedPart = part;
        selectedPartIndex = index;

        if (part instanceof InHouse) {

            InHouse newPart = (InHouse) part;
            InHouseRadioButton.setSelected(true);
            MachineCompanyLabel.setText("Machine ID");
            this.NameInhouseModifyPartText.setText(newPart.getName());
            this.InventoryInhouseModifyPartText.setText((Integer.toString(newPart.getStock())));
            this.PriceCostInhouseModifyPartText.setText((Double.toString(newPart.getPrice())));
            this.MinInhouseModifyPartText.setText((Integer.toString(newPart.getMin())));
            this.MaxInhouseModifyPartText.setText((Integer.toString(newPart.getMax())));
            this.MachineIDInhouseModifyPartText.setText((Integer.toString(newPart.getMachineID())));
            Inventory.modifyPart(selectedPartIndex, newPart);

        }

        if (part instanceof Outsourced) {

            Outsourced newPart = (Outsourced) part;
            OutsourcedRadioButton.setSelected(true);
            MachineCompanyLabel.setText("Company Name");
            this.NameInhouseModifyPartText.setText(newPart.getName());
            this.InventoryInhouseModifyPartText.setText((Integer.toString(newPart.getStock())));
            this.PriceCostInhouseModifyPartText.setText((Double.toString(newPart.getPrice())));
            this.MinInhouseModifyPartText.setText((Integer.toString(newPart.getMin())));
            this.MaxInhouseModifyPartText.setText((Integer.toString(newPart.getMax())));
            this.MachineIDInhouseModifyPartText.setText(newPart.getCompanyName());
            Inventory.modifyPart(selectedPartIndex, newPart);
        }
    }
}