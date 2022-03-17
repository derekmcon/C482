package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Product class that defines our fields for a product */
public class Product {
    // Declare fields
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** Declaring product method with parameters */
    // Declare methods
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }
    /** Add associated part method that will add a part to our associated parts list */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    /** Delete associated part method that will remove a part from our associated parts list */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        for (int i = 0; i < getAllAssociatedParts().size(); i++) {
            if (associatedParts.get(i) == selectedAssociatedPart) {
                associatedParts.remove(i);
                return true;
            }
        }
        return false;
    }
    /** Returns all associated parts */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
    /** Returns product id */
    public int getId() {
        return id;
    }
    /** Returns product name */
    public String getName() {
        return name;
    }
    /** Returns product price */
    public double getPrice() {
        return price;
    }
    /** Returns product stock/inv */
    public int getStock() {
        return stock;
    }
    /** Returns product min */
    public int getMin() {
        return min;
    }
    /** Returns product max */
    public int getMax() {
        return max;
    }
    /** Set id to this id */
    public void setId(int id) {
        this.id = id;
    }
    /** Set name to this name */
    public void setName(String name) {
        this.name = name;
    }
    /** Set price to this price */
    public void setPrice(double price) {
        this.price = price;
    }
    /** Set stock to this stock */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /** Set min to this min */
    public void setMin(int min) {
        this.min = min;
    }
    /** Set max to this max */
    public void setMax(int max) {
        this.max = max;
    }
}
