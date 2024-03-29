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
import java.util.regex.Pattern;

import static controller.MainController.getSelectedProductIndex;

/** Class to modify an existing product. */
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
    private final int productIndex = getSelectedProductIndex();
    private ObservableList<Part> associatedPart = FXCollections.observableArrayList();
    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    /** Method to check if an input value is numerical.
     * @param strNum The value to be checked. */
    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return true;
        }
        return !pattern.matcher(strNum).matches();
    }

    /** Method to search for a part.
     * @param event Search button event. */
    @FXML public void handleSearchButton(ActionEvent event) {
        ObservableList<Part> foundPart = Inventory.findPartByName(SearchField.getText());
        if(foundPart.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Part not found");
            alert.setHeaderText(SearchField.getText() + " was not found.");
            alert.showAndWait();
        } else {
            PartTable.setItems(foundPart);
        }
    }

    /** Method to cancel modification of a product.
     * @param event Cancel button event. */
    @FXML public void handleCancelButton(ActionEvent event) throws IOException {
        if (MainController.confirmDialog("Cancel", "Cancel modifying this product?")) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\MainForm.fxml")));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    /** Method to save modifications made to a part.
     * @param event Save button event. */
    @FXML void handleSaveButton(ActionEvent event) throws IOException {
        String productName = NameTextField.getText();
        String productInv = InvTextField.getText();
        String productPrice = PriceTextField.getText();
        String productMin = MinTextField.getText();
        String productMax = MaxTextField.getText();
        try {
            if (isNumeric(productInv) || isNumeric(productPrice) || isNumeric(productMin) || isNumeric(productMax)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid entry");
                alert.setHeaderText("Check all values");
                alert.setContentText("Inv, Price, Min and Max must be numbers.");
                alert.showAndWait();
            } else {
                if (productName.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Enter a part name");
                    alert.setHeaderText("Enter a part name");
                    alert.setContentText("Part name cannot be empty.");
                    alert.showAndWait();
                } else if (Integer.parseInt(productMin) > Integer.parseInt(productMax)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Invalid Min/Max Value");
                    alert.setHeaderText("Invalid Min/Max Value");
                    alert.setContentText("Min value must be less than Max value.");
                    alert.showAndWait();
                } else if (Integer.parseInt(productInv) < Integer.parseInt(productMin) || Integer.parseInt(productInv) > Integer.parseInt(productMax)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Invalid Inv Value");
                    alert.setHeaderText("Invalid Inv Value");
                    alert.setContentText("Inv must be between Min and Max values.");
                    alert.showAndWait();
                } else {
                    Product newProduct = new Product();
                    newProduct.setId(productIndex + 1);
                    newProduct.setName(productName);
                    newProduct.setPrice(Double.parseDouble(productPrice));
                    newProduct.setStock(Integer.parseInt(productInv));
                    newProduct.setMin(Integer.parseInt(productMin));
                    newProduct.setMax(Integer.parseInt(productMax));
                    newProduct.addProductPart(associatedPart);
                    Inventory.modifyProduct(productIndex, newProduct);
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\MainForm.fxml")));
                    stage.setScene(new Scene((Parent) scene));
                    stage.show();
                }
            }
            } catch(NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Please check all fields.");
                alert.showAndWait();
            }

    }

    /** Method to add a part to a product.
     * @param event Add part button event. */
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

    /** Method to remove a part from a product.
     * @param event Remove part button event. */
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

    /** Method to update the parts table for a product. */
    private void updateAssociatedPartTable() {
        AssociatedPartTable.setItems(associatedPart);
    }

    /** Method for the initialization of the GUI. */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Product selectedProduct = MainController.getSelectedProduct();
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