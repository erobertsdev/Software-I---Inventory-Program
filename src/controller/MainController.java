package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;

import java.io.IOException;

public class MainController {

    /** Declare variables for UI components **/

//    @FXML private TableView PartTable;
    @FXML private Button AddPartButton;
    @FXML private Button ModifyPartButton;
    @FXML private Button DeletePartButton;
    @FXML private TextField PartSearch;
//    @FXML private TableView ProductTable;
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

    public void handleModifyPartButton() {

    }

    public void handleDeletePartButton() {

    }

    public void handlePartSearch() {

    }

    public void handleAddProductButton(ActionEvent event) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("..\\view\\AddProductForm.fxml")));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void handleModifyProductButton() {

    }

    public void handleDeleteProductButton() {

    }

    public void handleProductSearch() {

    }

    public void handleExitButton() {

    }
}
