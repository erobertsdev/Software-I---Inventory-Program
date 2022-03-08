package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        // Test parts
        // InHouse
        Inventory.addPart(new InHouse(1, "Flywheel", 250.00, 15, 5, 20, 1));

        // Outsourced
        Inventory.addPart(new Outsourced(2, "Turbocharger", 1200.00, 10, 2, 15, 2, "Honeywell"));

        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setTitle("Main Form");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
