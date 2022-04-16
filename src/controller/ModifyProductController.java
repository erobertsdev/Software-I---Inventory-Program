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
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {
    private Stage stage;
    private Object scene;
    @FXML private TableView<Part> PartTable;
    @FXML private TableColumn<Part, Integer> PartID;
    @FXML private TableColumn<Part, String> PartName;
    @FXML private TableColumn<Part, Integer> PartInv;
    @FXML private TableColumn<Part, Double> PartPrice;
    @FXML private TableView<Part> AssociatedPartTable;
    @FXML private TableColumn<Product, Integer> AssociatedPartID;
    @FXML private TableColumn<Product, String> AssociatedPartName;
    @FXML private TableColumn<Product, Integer> AssociatedPartInv;
    @FXML private TableColumn<Product, Double> AssociatedPartPrice;
    @FXML private TextField NameTextField;
    @FXML private TextField InvTextField;
    @FXML private TextField PriceTextField;
    @FXML private TextField MaxTextField;
    @FXML private TextField MinTextField;
    @FXML private TextField IDTextField;
    @FXML private TextField SearchField;
    private Product selectedProduct;
    private Product modifyProduct;
    private final ObservableList<Part> associatedPart = FXCollections.observableArrayList();
    private int productID;

    public void setProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
        productID = Inventory.getProductList().indexOf(selectedProduct);
        IDTextField.setText(Integer.toString(selectedProduct.getId()));
        NameTextField.setText(selectedProduct.getName());
        InvTextField.setText(Integer.toString(selectedProduct.getStock()));
        PriceTextField.setText(Double.toString(selectedProduct.getPrice()));
        MaxTextField.setText(Integer.toString(selectedProduct.getMax()));
        MinTextField.setText(Integer.toString(selectedProduct.getMin()));
        associatedPart.addAll(selectedProduct.getProductParts());
    }

    // TODO: Add search button to modify product form
    @FXML public void handleSearchButton(ActionEvent event) {
        ObservableList<Part> foundPart = model.Inventory.findPartByName(SearchField.getText());
        if(foundPart.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("NO MATCH");
            alert.setHeaderText("Unable to locate a Part name with: " + SearchField.getText());
            alert.showAndWait();
        } else {
            PartTable.setItems(foundPart);
        }
    }

    @FXML public void handleCancelButton(ActionEvent event) throws IOException {
        if (MainController.confirmDialog("Cancel", "Cancel modifying this product?")) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\MainForm.fxml")));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    @FXML void handleSaveButton(ActionEvent event) throws IOException {
        int productInventory = Integer.parseInt(InvTextField.getText());
        int productMinimum = Integer.parseInt(MinTextField.getText());
        int productMaximum = Integer.parseInt(MaxTextField.getText());
        if(MainController.confirmDialog("Save?", "Would you like to save this part?"))
            if(productMaximum < productMinimum) {
                MainController.infoDialog("Input Error", "Error in min and max field", "Check Min and Max value." );
            }
            else if(productInventory < productMinimum || productInventory > productMaximum) {
                MainController.infoDialog("Input Error", "Error in inventory field", "Inventory must be between Minimum and Maximum" );
            }
            else {
                this.modifyProduct = selectedProduct;
                selectedProduct.setId(Integer.parseInt(IDTextField.getText()));
                selectedProduct.setName(NameTextField.getText());
                selectedProduct.setStock(Integer.parseInt(InvTextField.getText()));
                selectedProduct.setPrice(Double.parseDouble(PriceTextField.getText()));
                selectedProduct.setMax(Integer.parseInt(MaxTextField.getText()));
                selectedProduct.setMin(Integer.parseInt(MinTextField.getText()));
                selectedProduct.getProductParts().clear();
                selectedProduct.addProductPart(associatedPart);
                model.Inventory.modifyProduct(productID, selectedProduct);
                stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\MainForm.fxml")));
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }
    }

    @FXML void handleAddButton(ActionEvent event) {
        Part selectedPart = PartTable.getSelectionModel().getSelectedItem();
        if(selectedPart != null) {
            associatedPart.add(selectedPart);
            updateAssociatedPartTable();
        }
        else {
            MainController.infoDialog("Select a part","Select a part", "Select a part to add to the Product");
        }
    }

    @FXML
    void handleRemoveButton(ActionEvent event) {
        Part selectedPart = AssociatedPartTable.getSelectionModel().getSelectedItem();

        if(selectedPart != null) {
            MainController.confirmDialog("Deleting Part","Are you sure you want to delete " + selectedPart.getName() + " from the Product?");
            associatedPart.remove(selectedPart);
            updateAssociatedPartTable();
        }
        else {
            MainController.infoDialog("No Selection","No Selection", "Please choose something to remove");
        }
    }

    public void updatePartTable() {
        PartTable.setItems(model.Inventory.getPartList());
    }

    private void updateAssociatedPartTable() {
        AssociatedPartTable.setItems(associatedPart);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PartID.setCellValueFactory(new PropertyValueFactory<>("partID"));
        PartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPrice.setCellValueFactory(new PropertyValueFactory<>("partCost"));
        PartTable.setItems(model.Inventory.getPartList());
        AssociatedPartID.setCellValueFactory(new PropertyValueFactory<>("partID"));
        AssociatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("partCost"));
        AssociatedPartTable.setItems(associatedPart);

        updatePartTable();
        updateAssociatedPartTable();
    }
}