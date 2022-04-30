package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller for adding a new product. */
public class AddProductController implements Initializable {
    private final ObservableList<Part> productParts = FXCollections.observableArrayList();
    public TextField SearchTextField;
    public TextField ProdIDTextField;
    public TextField ProdNameTextField;
    public TextField ProdInvTextField;
    public TextField ProdMaxTextField;
    public TextField ProdMinTextField;
    public TextField ProdPriceTextField;
    public TableView<Part> PartTable;
    public TableColumn<Part, Integer> PartID;
    public TableColumn<Part, String> PartName;
    public TableColumn<Part, Integer> PartInv;
    public TableColumn<Part, Double> PartPrice;
    public TableView<Part> AssociatedPart;
    public TableColumn<Part, Integer> AssociatedPartID;
    public TableColumn<Part, String> AssociatedPartName;
    public TableColumn<Part, Integer> AssociatedPartInv;
    public TableColumn<Part, Double> AssociatedPartPrice;
    int productId = Inventory.newProdID();

    /** Method for saving a new product.
     * @param actionEvent Save product button event. */
    public void handleSaveButton(ActionEvent actionEvent) throws IOException {
        try {
            String productName = ProdNameTextField.getText();
            double productPrice = Double.parseDouble(ProdPriceTextField.getText());
            int inv = Integer.parseInt(ProdInvTextField.getText());
            int min = Integer.parseInt(ProdMinTextField.getText());
            int max = Integer.parseInt(ProdMaxTextField.getText());

            if (productName.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Enter a part name");
                alert.setHeaderText("Enter a part name");
                alert.setContentText("Part name cannot be empty.");
                alert.showAndWait();
            } else {
                if (min > max) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Invalid Min/Max");
                    alert.setHeaderText("Invalid Min/Max");
                    alert.setContentText("Min value must be less than Max value.");
                    alert.showAndWait();
                } else if ((inv < min) || (inv > max)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Invalid Inv Value");
                    alert.setHeaderText("Invalid Inv Value");
                    alert.setContentText("Inv value must fall between Min and Max.");
                    alert.showAndWait();
                }
                else {
                    Product newProduct = new Product(productId, productName, productPrice, inv, min, max);
                    for (Part part : productParts) {
                        newProduct.addPart(part);
                    }
                    newProduct.setId(productId);
                    Inventory.addProduct(newProduct);
                    Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\MainForm.fxml")));
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please fill out all fields.");
            alert.showAndWait();
        }

    }

    /** Method for cancelling addition of a product.\
     * @param actionEvent Cancel button event.*/
    public void handleCancelButton(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setContentText("Would you like to exit without saving?");
        Optional<ButtonType> answer = alert.showAndWait();

        if (answer.isPresent() && answer.get() == ButtonType.OK) {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\MainForm.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /** Method for adding a part to a product.
     * @param actionEvent Add part button event.*/
    public void handleAddPartButton(ActionEvent actionEvent) {
        Part thisPart = PartTable.getSelectionModel().getSelectedItem();

        if (thisPart == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Select a Part");
            alert.setHeaderText("Select a Part");
            alert.setContentText("Please select a part.");
            alert.showAndWait();
        } else {
            productParts.add(thisPart);
            AssociatedPart.setItems(productParts);
        }
    }

    /** Method for removing a part from a product.
     * @param actionEvent Remove part button event.*/
    public void handleRemovePartButton(ActionEvent actionEvent) {
        Part selectedPart = AssociatedPart.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Select a Part");
            alert.setHeaderText("Select a Part");
            alert.setContentText("Please select a part.");
            alert.showAndWait();
        } else {
            Alert confirmRemoval = new Alert(Alert.AlertType.CONFIRMATION);
            confirmRemoval.setTitle("Alert");
            confirmRemoval.setContentText("Remove Selected Part?");
            Optional<ButtonType> answer = confirmRemoval.showAndWait();

            if (answer.isPresent() && answer.get() == ButtonType.OK) {
                productParts.remove(selectedPart);
                AssociatedPart.setItems(productParts);
            }

        }
    }

    /** Method for searching for a part.
     * @param actionEvent Search button or enter key event. */
    public void handleSearchButton(ActionEvent actionEvent) {
        ObservableList<Part> foundPart = Inventory.findPartByName(SearchTextField.getText());
        if(foundPart.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Part not found");
            alert.setHeaderText(SearchTextField.getText() + " was not found.");
            alert.showAndWait();
        } else {
            PartTable.setItems(foundPart);
        }
    }

    /** Method to initialize the GUI. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ProdIDTextField.setText(String.valueOf(productId));
        PartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartTable.setItems(Inventory.getPartList());

        AssociatedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssociatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

}
