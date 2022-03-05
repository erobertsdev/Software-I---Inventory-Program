package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    /** Declare variables for UI components **/

    // TODO: LEFT OFF HERE
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
    @FXML
    public void handleAddPartButton(ActionEvent event) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("..\\view\\AddPartForm.fxml")));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void handleModifyPartButton(ActionEvent event) throws IOException {
        // TODO: Add logic to figure out which part is selected
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("..\\view\\ModifyPartForm.fxml")));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void handleDeletePartButton(ActionEvent event) throws IOException {

    }

    public void handlePartSearch(ActionEvent event) throws IOException {

    }

    public void handleAddProductButton(ActionEvent event) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("..\\view\\AddProductForm.fxml")));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void handleModifyProductButton(ActionEvent event) throws IOException {
        // TODO: Add logic to figure out which product is selected
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("..\\view\\ModifyProductForm.fxml")));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void handleDeleteProductButton(ActionEvent event) throws IOException {

    }

    public void handleProductSearch(ActionEvent event) throws IOException {

    }

    public void handleExitButton(ActionEvent event) throws IOException {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PartID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        PartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PartInvLevel.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        PartPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        ProductID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        ProductName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ProductInvLevel.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        ProductPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }

    // TODO: THIS HAS TO BE DONE IN model/Inventory.java YOU MORON
    // ObservableLists for test data and parts/products
    ObservableList<Part> observableList= FXCollections.observableArrayList(
            new Part(1, "Flywheel", 250.00, 5)
    );
}
