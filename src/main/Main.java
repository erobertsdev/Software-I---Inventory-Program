package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        // Test parts
        // InHouse
        Inventory.addPart(new InHouse(1, "Flywheel", 250.00, 15, 5, 20, 1));

        // Outsourced
        Inventory.addPart(new Outsourced(2, "Turbocharger", 1200.00, 10, 2, 15,"Honeywell"));

        // Test Products - broke figure it oot
        Inventory.addProduct(new Product(1, "Nissan GT-R", 95000.00, 5, 2, 10));

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainForm.fxml")));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
