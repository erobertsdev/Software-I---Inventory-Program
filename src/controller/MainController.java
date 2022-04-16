package controller;

import javafx.collections.FXCollections;
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

public class MainController implements Initializable {

    // Parts Table
    @FXML private TableView<Part> PartTable;
    @FXML private TableColumn<Part, Integer> PartID;
    @FXML private TableColumn<Part, String> PartName;
    @FXML private TableColumn<Part, Integer> PartInvLevel;
    @FXML private TableColumn<Part, Double> PartPrice;
    @FXML private Button AddPartButton;
    @FXML private Button ModifyPartButton;
    @FXML private Button DeletePartButton;
    @FXML private TextField PartSearch;
    // Products Table
    @FXML private TableView<Product> ProductTable;
    @FXML private TableColumn<Product, Integer> ProductID;
    @FXML private TableColumn<Product, String> ProductName;
    @FXML private TableColumn<Product, Integer> ProductInvLevel;
    @FXML private TableColumn<Product, Double> ProductPrice;
    @FXML private Button AddProductButton;
    @FXML private Button ModifyProductButton;
    @FXML private Button DeleteProductButton;
    @FXML private TextField ProductSearch;
    @FXML private Button ExitButton;
    private static Part selectedPart;
    private static int selectedPartIndex;
    public static Product selectedProduct;
    private Parent scene;

    public static Part getSelectedPart() {
        return selectedPart;
    }

    public static int getSelectedPartIndex() {
        return selectedPartIndex;
    }

    public static Product getSelectedProduct() {
        return selectedProduct;
    }

    @FXML
    public void handleAddPartButton(ActionEvent event) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\AddPartForm.fxml"))));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

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

    public void handleAddProductButton(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\AddProductForm.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void handleModifyProductButton(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage=(Stage) ModifyPartButton.getScene().getWindow();
        //load up OTHER FXML document
        FXMLLoader loader=new FXMLLoader(getClass().getResource(
                "..\\view\\ModifyProductForm.fxml"));

        root =loader.load();
        ModifyPartController controller = loader.getController();
        Part part = PartTable.getSelectionModel().getSelectedItem();
        int index = PartTable.getSelectionModel().getSelectedIndex();

        if(part != null) {
            Inventory.modifyPart(index, part);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleDeleteProductButton(ActionEvent event) throws IOException {
        if (ProductTable.getSelectionModel().isEmpty()){
            infoDialog("Delete Product", "Error","Please select a product to delete.");
            return;
        }
        if (confirmDialog("Delete Product", "Are you sure you want to delete this product?")){
            int selectedPart = ProductTable.getSelectionModel().getSelectedIndex();
            ProductTable.getItems().remove(selectedPart);
        }
    }

    // TODO: Fix product search
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

    public void handleExitButton(ActionEvent event) throws IOException {
        confirmDialog("Close Program", "Are you sure you want to exit?");
        {
            System.exit(0);
        }
    }

    static boolean confirmDialog(String title, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Confirm");
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    static void infoDialog(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

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
