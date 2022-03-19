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

    public static ObservableList<Part> findPartByName(String partName) {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        for (Part part : partObservableList) {
            if (part.getName().contains(partName)) {
                parts.add(part);
            }
        }
        return parts;
    }

    // Modify Part
    public static void modifyPart(int index, Part selectedPart) {
        partObservableList.set(index, selectedPart);
    }

    // Delete Part
    public static boolean deletePart(Part selectedPart) {
        return partObservableList.remove(selectedPart);
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

    public static ObservableList<Product> findProductByName(String productName) {
        ObservableList<Product> products = FXCollections.observableArrayList();
        for (Product product : productObservableList){
            if(product.getName().contains(productName)){
                products.add(product);
            }
        }
        return products;
    }

    // Modify Product
    public static void modifyProduct(int index, Product selectedProduct) {
        productObservableList.set(index, selectedProduct);
    }

    // Delete Product
    public static boolean deleteProduct(Product selectedProduct) {
        return productObservableList.remove(selectedProduct);
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
