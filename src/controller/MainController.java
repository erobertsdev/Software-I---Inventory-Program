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
import java.util.logging.Level;
import java.util.logging.Logger;

import static model.Inventory.getPartList;
import static model.Inventory.getProductList;

public class MainController implements Initializable {

    /** Declare variables for UI components **/

    // Parts Pane
    @FXML private TableView<Part> PartTable;
    @FXML private TableColumn<Part, Integer> PartID;
    @FXML private TableColumn<Part, String> PartName;
    @FXML private TableColumn<Part, Integer> PartInvLevel;
    @FXML private TableColumn<Part, Double> PartPrice;
    @FXML private Button AddPartButton;
    @FXML private Button ModifyPartButton;
    @FXML private Button DeletePartButton;
    @FXML private TextField PartSearch;
    // Products Pane
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

    /** Event Handlers **/

    private Parent scene;
    @FXML
    public void handleAddPartButton(ActionEvent event) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\AddPartForm.fxml"))));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void handleModifyPartButton(ActionEvent event) throws IOException {
        // TODO: Add logic to figure out which part is selected
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\ModifyPartForm.fxml"))));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void handleDeletePartButton(ActionEvent event) throws IOException {
        if (PartTable.getSelectionModel().isEmpty()){
            infoDialog("Delete Part", "Error","Please select a part to delete.");
            return;
        }
        if (confirmDialog("Warning!", "Are you sure you want to delete this part?")){
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
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\AddProductForm.fxml"))));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void handleModifyProductButton(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage=(Stage) ModifyPartButton.getScene().getWindow();
        //load up OTHER FXML document
        FXMLLoader loader=new FXMLLoader(getClass().getResource(
                "/View/ModifyPart.fxml"));

        root =loader.load();
        ModifyPartController controller = loader.getController();
        Part part=PartTable.getSelectionModel().getSelectedItem();
        int index = PartTable.getSelectionModel().getSelectedIndex();

        if(part != null) {
            controller.setPart(part, index);
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
        PartTable.setItems(getPartList());
        ProductTable.setItems(getProductList());

        // TODO: Runtime error possibility here, this.PartID is null, there was no fx:id in FXML file
        PartID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        PartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PartInvLevel.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        PartPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        ProductID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        ProductName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ProductInvLevel.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        ProductPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }

}
