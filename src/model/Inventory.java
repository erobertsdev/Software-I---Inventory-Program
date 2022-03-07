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

    // Find part by ID
    public static Part findPartByID(int partID) {
        Part foundPart = null;
        for (Part part : partObservableList) {
            if (partID == part.getId()) {
                foundPart = part;
            }
        }
        return foundPart;
    }

    // Find product by ID
    public static Product findProductByID(int productID) {
        Product foundProduct = null;
        for (Product product : productObservableList) {
            if (productID == product.getId()) {
                foundProduct = product;
            }
        }
        return foundProduct;
    }

    // Return list of all parts
    public static ObservableList<Part> getPartList() {
        return partObservableList;
    }

    // Return list of all products
    public static ObservableList<Product> getProductList() {
        return productObservableList;
    }
}
