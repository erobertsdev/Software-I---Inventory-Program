package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {
    private Stage stage;
    private Object scene;

    @FXML private TextField ProdNameTextField;
    @FXML private TextField ProdInvTextField;
    @FXML private TextField ProdPriceTextField;
    @FXML private TextField ProdMaxTextField;
    @FXML private TextField ProdMinTextField;
    @FXML private TableView<Part> PartTable;
    @FXML private TableColumn<Part, Integer> PartID;
    @FXML private TableColumn<Part, String> PartName;
    @FXML private TableColumn<Part, Integer> PartInv;
    @FXML private TableColumn<Part, Double> PartPrice;
    @FXML private TableView<Part> AssociatedPart;
    @FXML private TableColumn<Product, Integer> AssociatedPartID;
    @FXML private TableColumn<Product, String> AssociatedPartName;
    @FXML private TableColumn<Product, Integer> AssociatedPartInv;
    @FXML private TableColumn<Product, Double> AssociatedPartPrice;
    @FXML private TextField SearchTextField;
    private final ObservableList<Part> associatedPart = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Part> originalPart = Inventory.getPartList();

        PartID.setCellValueFactory(new PropertyValueFactory<>("partID"));
        PartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPrice.setCellValueFactory(new PropertyValueFactory<>("partCost"));
        PartTable.setItems(originalPart);

        AssociatedPartID.setCellValueFactory(new PropertyValueFactory<>("partID"));
        AssociatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("partCost"));
        AssociatedPart.setItems(associatedPart);

        PartTable.setItems(Inventory.getPartList());
        AssociatedPart.setItems(associatedPart);
    }

    @FXML public void handleSearchButton(ActionEvent event) {
        ObservableList<Part> foundPart = Inventory.findPartByName(SearchTextField.getText());
        if(foundPart.isEmpty()) {
            MainController.confirmDialog("Part Not Found", SearchTextField.getText() + "was not found.");
        } else {
            PartTable.setItems(foundPart);
        }
    }

    @FXML void handleAddPartButton(ActionEvent event) {
        Part selectedPart = PartTable.getSelectionModel().getSelectedItem();

        if(selectedPart != null) {
            associatedPart.add(selectedPart);
            PartTable.setItems(Inventory.getPartList());
            AssociatedPart.setItems(associatedPart);
        }

        else {
            MainController.infoDialog("Select Part","Select Part", "Select part to add");
        }
    }

    @FXML
    void handleRemovePartButton(ActionEvent event) {
        Part selectedPart = AssociatedPart.getSelectionModel().getSelectedItem();

        if(selectedPart != null) {
            MainController.confirmDialog("Delete Part","Remove " + selectedPart.getName() + "?");
            associatedPart.remove(selectedPart);
            PartTable.setItems(Inventory.getPartList());
            AssociatedPart.setItems(associatedPart);
        }

        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No part selected.");
            alert.showAndWait();
        }
    }

    @FXML public void handleCancelButton(ActionEvent event) throws IOException {
        if (MainController.confirmDialog("Cancel", "Cancel?")) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\MainForm.fxml")));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    @FXML void handleSaveButton(ActionEvent event) throws IOException {

        if (associatedPart.isEmpty()) {
            MainController.infoDialog("Error", "Please add a part", "Product must have at least one part.");
            return;
        }

        if (ProdNameTextField.getText().isEmpty() || ProdMinTextField.getText().isEmpty() || ProdMaxTextField.getText().isEmpty() || ProdMaxTextField.getText().isEmpty() || ProdPriceTextField.getText().isEmpty()) {
            MainController.infoDialog("Error", "All fields are required", "Please enter a value in all fields.");
            return;
        }

        int inv = Integer.parseInt(this.ProdInvTextField.getText());
        int min = Integer.parseInt(this.ProdMinTextField.getText());
        int max = Integer.parseInt(this.ProdMaxTextField.getText());

        if (max < min) {
            MainController.infoDialog("Error", "Invalid Value", "Max value cannot be less than Min value.");
            return;
        }

        if (inv < min || inv > max) {
            MainController.infoDialog("Error", "Invalid Value", "Inventory value must fall between Min and Max values.");
            return;
        }

        //Add Product
        if (MainController.confirmDialog("Save Product", "Save this product?")) {
            Product product = new Product();
            product.setId(getNewID());
            product.setName(this.ProdNameTextField.getText());
            product.setStock(Integer.parseInt(this.ProdInvTextField.getText()));
            product.setMin(Integer.parseInt(this.ProdMinTextField.getText()));
            product.setMax(Integer.parseInt(this.ProdMaxTextField.getText()));
            product.setPrice(Double.parseDouble(this.ProdPriceTextField.getText()));
            product.addProductPart(associatedPart);
            Inventory.addProduct(product);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\MainWindow.fxml")));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    private int getNewID(){
        int newID = 1;
        for (int i = 0; i < Inventory.getPartList().size(); i++) {
            if (Inventory.getPartList().get(i).getId() == newID) {
                newID++;
            }
        }
        return newID;
    }

}
