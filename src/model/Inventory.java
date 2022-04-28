package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Model for the current inventory. */
public class Inventory {
    private static final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Method to add a part to the inventory.
     * @param part Part to be added.*/
    public static void addPart(Part part) {
        associatedParts.add(part);
    }

    /** Method to a dd a product to the inventory.
     * @param product Product to be added. */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /** Method to find a part by ID.
     * @param partID ID to be searched for.
     * @return foundPart returns a part if found. */
    public static Part findPartByID(int partID) {
        Part foundPart = null;
        for (Part part : associatedParts) {
            if (partID == part.getId()) {
                foundPart = part;
            }
        }
        return foundPart;
    }

    /** Method to search for part by name.
     * @param partName Text to be searched for.
     * @return parts returns list of parts with the search term included. */
    public static ObservableList<Part> findPartByName(String partName) {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        for (Part part : associatedParts) {
            if (part.getName().toLowerCase().contains(partName.toLowerCase()) ||
                    (String.valueOf(part.getId()).contains(partName))) {
                parts.add(part);
            }
        }
        return parts;
    }

    /** Method to modify an existing part.
     * @param index Index of part to be modified.
     * @param selectedPart The currently selected part. */
    public static void modifyPart(int index, Part selectedPart) {
        associatedParts.set(index, selectedPart);
    }

    /** Method to delete a part.
     * @param selectedPart The currently selected part. */
    public static boolean deletePart(Part selectedPart) {
        return associatedParts.remove(selectedPart);
    }

    /** Method to find a product by ID.
     * @param productID ID to be searched for.
     * @return foundProduct returns a product with the ID that was searched for. */
    public static Product findProductByID(int productID) {
        Product foundProduct = null;
        for (Product product : allProducts) {
            if (productID == product.getId()) {
                foundProduct = product;
            }
        }
        return foundProduct;
    }

    /** Method to find a product by name.
     * @param productName Name to be searched for.
     * @return products returns list of products with the search term included */
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

    /** Method to modify an exising product.
     * @param index Index of product to be modified.
     * @param selectedProduct The currently selected product. */
    public static void modifyProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /** Method to delete an existing product.
     * @param selectedProduct The currently selected product.
     * @return Returns list of products with the deleted one removed. */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /** Method to update an exisint product.
     * @param index Index of product to be modified.
     * @param selectedPart The currently selected product. */
    public static void updatePart(int index, Part selectedPart){
        associatedParts.set(index, selectedPart);
    }

    /** Method to return all parts in a product.
     * @return associatedParts returns all parts in a product. */
    public static ObservableList<Part> getPartList() {
        return associatedParts;
    }

    /** Method to return list of all products.
     * @return allProducts returns all current products. */
    public static ObservableList<Product> getProductList() {
        return allProducts;
    }

}
