package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

import static model.Inventory.getPartList;
import static model.Inventory.getProductList;

/** Controller for the main screen of the application. */
public class MainController implements Initializable {

    // Parts Table
    @FXML private TableView<Part> PartTable;
    @FXML private TableColumn<Part, Integer> PartID;
    @FXML private TableColumn<Part, String> PartName;
    @FXML private TableColumn<Part, Integer> PartInvLevel;
    @FXML private TableColumn<Part, Double> PartPrice;
    @FXML private TextField PartSearch;
    // Products Table
    @FXML private TableView<Product> ProductTable;
    @FXML private TableColumn<Product, Integer> ProductID;
    @FXML private TableColumn<Product, String> ProductName;
    @FXML private TableColumn<Product, Integer> ProductInvLevel;
    @FXML private TableColumn<Product, Double> ProductPrice;
    @FXML private TextField ProductSearch;
    private static Part selectedPart;
    private static int selectedPartIndex;
    public static Product selectedProduct;
    public static int selectedProductIndex;

    /** Method to return the currently selected part.
     * @return the currently selected part. */
    public static Part getSelectedPart() {
        return selectedPart;
    }

    /** Method to return the index of the currently selected part.
     * @return the index of the currently selected part. */
    public static int getSelectedPartIndex() {
        return selectedPartIndex;
    }

    /** Method to return the currently selected product.
     * @return the currently selected product. */
    public static Product getSelectedProduct() {
        return selectedProduct;
    }

    /** Method to return the index of the currently selected product.
     * @return index of selected product. */
    public static int getSelectedProductIndex() { return selectedProductIndex; }

    /** Method to add a part to the inventory.
     * @param event Add part button event.*/
    @FXML
    public void handleAddPartButton(ActionEvent event) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\AddPartForm.fxml"))));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /** Method to modify an existing part.
     * @param event Modify part button event. */
    public void handleModifyPartButton(ActionEvent event) throws IOException {
        selectedPart = PartTable.getSelectionModel().getSelectedItem();
        selectedPartIndex = getPartList().indexOf(selectedPart);
        try {
            if (selectedPart != null) {
                Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\ModifyPartForm.fxml")));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No Part Selected");
                alert.setContentText("Please select a part to modify.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Method to delete an existing part.
     * @param event Delete part event. */
    public void handleDeletePartButton(ActionEvent event) throws IOException {
        if (PartTable.getSelectionModel().isEmpty()){
            infoDialog("Delete Part", "Error","Please select a part to delete.");
            return;
        }
        if (confirmDialog("Delete Part", "Are you sure you want to delete this part?")){
            int selectedPart = PartTable.getSelectionModel().getSelectedIndex();
            PartTable.getItems().remove(selectedPart);
        }
    }

    /** Method to search for a part.
     * @param event Search button or enter keypress event. */
    public void handlePartSearch(ActionEvent event) throws IOException {
        ObservableList<Part> partSearchText = Inventory.findPartByName(PartSearch.getText());
        if(partSearchText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Part not found");
            alert.setHeaderText(PartSearch.getText() + " was not found.");
            alert.showAndWait();
        } else {
            PartTable.setItems(partSearchText);
        }
    }

    /** Method to add product to inventory.
     * @param event Add product button event. */
    public void handleAddProductButton(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\AddProductForm.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /** Method to modify an existing product.
     * @param event Modify product event. */
    public void handleModifyProductButton(ActionEvent event) throws IOException {
        selectedProduct = ProductTable.getSelectionModel().getSelectedItem();
        selectedProductIndex = getProductList().indexOf(selectedProduct);
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Product Selected");
            alert.setHeaderText("Select a product to modify.");
            alert.showAndWait();
        } else {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\ModifyProductForm.fxml")));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /** Method to delete an existing product.
     * @param event Modify product button event. */
    public void handleDeleteProductButton(ActionEvent event) throws IOException {
        Product selectedProduct = ProductTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null){
            infoDialog("Delete Product", "Error","Please select a product to delete.");
        } else if (!selectedProduct.getProductParts().isEmpty()) {
            infoDialog("Delete Product", "Error","All associated parts must be removed before product can be deleted.");
        } else {
            if (confirmDialog("Delete Product", "Are you sure you want to delete this product?")) {
                int selectedPart = ProductTable.getSelectionModel().getSelectedIndex();
                ProductTable.getItems().remove(selectedPart);
            }
        }
    }

    /** Method to search for a product.
     * @param event Search button or enter keypress event. */
    public void handleProductSearch(ActionEvent event) throws IOException {
        ObservableList<Product> productSearchText = Inventory.findProductByName(ProductSearch.getText());
        if(productSearchText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Product not found.");
            alert.setHeaderText(ProductSearch.getText() + " was not found.");
            alert.showAndWait();
        } else {
            ProductTable.setItems(productSearchText);
        }
    }

    /** Method to exit the program.
     * @param event Exit button event.*/
    public void handleExitButton(ActionEvent event) throws IOException {
        confirmDialog("Close Program", "Are you sure you want to exit?");
        {
            System.exit(0);
        }
    }

    /** Method to create a GUI dialog box.
     * @param content Description of what the dialog box is for.
     * @param title Title of the event for the dialog box */
    static boolean confirmDialog(String title, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Confirm");
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /** Method to create a dialog box.
     * @param title Title of the dialog box.
     * @param content Content of the dialog box.
     * @param header Header of the dialog box. */
    static void infoDialog(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /** Method to initialize the GUI. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO: Runtime error possibility here, controller.MainController.PartTable is null, there was no fx:id in FXML file
        PartTable.setItems(getPartList());
        ProductTable.setItems(getProductList());

        PartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        ProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
