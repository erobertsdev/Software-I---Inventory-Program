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

/** This class is the main controller for the application. It displays all parts and products and allows navigation to
 * delete and modify parts and products.
 * Javadocs are located in "javadoc" directory.
 * @author Elias Adams-Roberts
 * FUTURE ENHANCEMENT: Connect the application to a database or use local storage to provide data persistence.
 * RUNTIME ERROR: I ran into multiple instances where javafx scenes could not be invoked because of null values, the fix
 * was adding the appropriate fx:id values needed to the fxml files. */
public class Main extends Application {

/**This method creates and displays the main GUI. Shows the main GUI screen and populates test parts/products. */
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("..\\view\\MainForm.fxml")));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }
    /**This is the main method. This is the first method that gets called when you run the program. */
    public static void main(String[] args) {
        launch(args);
    }
}
