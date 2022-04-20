package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;

public class Inventory {

    // ObservableLists for test data and parts/products
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    // Add part method
    public static void addPart(Part part) {
        allParts.add(part);
    }

    // Add product method
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    // Find part by ID
    public static Part findPartByID(int partID) {
        Part foundPart = null;
        for (Part part : allParts) {
            if (partID == part.getId()) {
                foundPart = part;
            }
        }
        return foundPart;
    }

    public static ObservableList<Part> findPartByName(String partName) {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().toLowerCase().contains(partName.toLowerCase()) ||
                    (String.valueOf(part.getId()).contains(partName))) {
                parts.add(part);
            }
        }
        return parts;
    }

    // Modify Part
    public static void modifyPart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    // Delete Part
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    // Find product by ID
    public static Product findProductByID(int productID) {
        Product foundProduct = null;
        for (Product product : allProducts) {
            if (productID == product.getId()) {
                foundProduct = product;
            }
        }
        return foundProduct;
    }

    // Find Product by Name
    public static ObservableList<Product> findProductByName(String productName) {
        ObservableList<Product> products = FXCollections.observableArrayList();
        for (Product product : allProducts){
            if(product.getName().toLowerCase().contains(productName.toLowerCase())
                    ||
                    (String.valueOf(product.getId()).contains(productName))){
                products.add(product);
            }
        }
        return products;
    }

    // Modify Product
    public static void modifyProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    // Delete Product
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    // Update Part
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    // updateProduct
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    // Return list of all parts
    public static ObservableList<Part> getPartList() {
        return allParts;
    }

    // Return list of all products
    public static ObservableList<Product> getProductList() {
        return allProducts;
    }

}
