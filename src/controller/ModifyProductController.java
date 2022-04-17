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

import static controller.MainController.getSelectedProductIndex;

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
    private int productIndex = getSelectedProductIndex();
    private ObservableList<Part> associatedPart = FXCollections.observableArrayList();
    private int productID;

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
        String productName = NameTextField.getText();
        String productInv = InvTextField.getText();
        String productPrice = PriceTextField.getText();
        String productMin = MinTextField.getText();
        String productMax = MaxTextField.getText();
        try {
                Product newProduct = new Product();
                newProduct.setId(productIndex + 1);
                newProduct.setName(productName);
                newProduct.setPrice(Double.parseDouble(productPrice));
                newProduct.setStock(Integer.parseInt(productInv));
                newProduct.setMin(Integer.parseInt(productMin));
                newProduct.setMax(Integer.parseInt(productMax));
                newProduct.addProductPart(associatedPart);
                Inventory.modifyProduct(productIndex, newProduct);

                stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\MainForm.fxml")));
                stage.setScene(new Scene((Parent) scene));
                stage.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please check all fields.");
            alert.showAndWait();
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
        selectedProduct = MainController.getSelectedProduct();
        associatedPart = selectedProduct.getProductParts();

        PartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartTable.setItems(Inventory.getPartList());

        AssociatedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssociatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        AssociatedPartTable.setItems(associatedPart);

        IDTextField.setText(String.valueOf(selectedProduct.getId()));
        NameTextField.setText(selectedProduct.getName());
        PriceTextField.setText(String.valueOf(selectedProduct.getPrice()));
        InvTextField.setText(String.valueOf(selectedProduct.getStock()));
        MinTextField.setText(String.valueOf(selectedProduct.getMin()));
        MaxTextField.setText(String.valueOf(selectedProduct.getMax()));
    }
}