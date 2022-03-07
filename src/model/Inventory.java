package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    // ObservableLists for test data and parts/products
    private static ObservableList<Part> partObservableList = FXCollections.observableArrayList();
    private static ObservableList<Product> productObservableList = FXCollections.observableArrayList();

    // Add part method
    public static void addPart(Part part) {
        partObservableList.add(part);
    }

    // Add product method
    public static void addProduct(Product product) {
        productObservableList.add(product);
    }


    // Return list of parts
    public static ObservableList<Part> getPartList() {
        return partObservableList;
    }

    // Return list of products
    public static ObservableList<Product> getProductList() {
        return productObservableList;
    }
}
