package models;

import com.sun.glass.ui.Clipboard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Inventory class that has a static ObservableList of Parts and Products */
public class Inventory {
    // Declare fields
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Add part method that will add a new part to the allParts list */
    // Declare methods
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    /** Add product method that will add a new product to the allProducts list */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    /** Lookup part method that will search for the ID of a part and return that part out of our allParts list */
    public static Part lookupPart(int partID) {
        ObservableList<Part> partIDs = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getId() == partID) {
                partIDs.add(part);
                return part;
            }
        }
        return null;
    }
    /** Lookup product method that will search for the ID of a product and return that product out of our allProducts list */
    public static Product lookupProduct(int productID) {
        ObservableList<Product> productIDs = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getId() == productID) {
                productIDs.add(product);
                return product;
            }
        }
        return null;
    }
    /** Lookup part method that will search for the partName and return that part out of our allParts list */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partNames = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                partNames.add(part);
            }
        }
        return partNames;
    }
    /** Lookup product method that will search for the productName and return that product out of our allProducts list */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productNames = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                productNames.add(product);
            }
        }
        return productNames;
    }
    /** Update part method that will replace the part at a given index */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }
    /** Update product method that will replace the product at a given index */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }
    /** Delete part method that will delete a selectedPart from our allParts list */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }
    /** Delete product method that will delete a selectedProduct from our allProducts list */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }
    /** Get all parts method that will return all our parts from our list */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    /** Get all products method that will return all our products from our list */
    public static ObservableList<Product>getAllProducts(){
        return allProducts;
    }

}
