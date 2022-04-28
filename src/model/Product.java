package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Model for a product.  */
public class Product {
    private ObservableList<Part> productParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Sets default parameters for a product if they're not included. */
    public Product() {
        this(0, null, 0.00, 0, 0, 0);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** Method to add a part to a product.
     * @param part the part to be added. */
    public void addPart(Part part) {
        productParts.add(part);
    }

    /** Method to add a part to a product.
     * @param part part to be added. */
    public void addProductPart(ObservableList<Part> part) {
        this.productParts.addAll(part);
    }

    /** Return all parts in a product.
     * @return productParts All of the parts in a product. */
    public ObservableList<Part> getProductParts() {
        return productParts;
    }

}