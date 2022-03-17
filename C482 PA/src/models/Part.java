package models;

/** Part class that defines our fields for a part */
public abstract class Part {
    // Declare fields
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** Declaring part method with parameters */
    // Declare methods
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }
    /** Returns part id */
    public int getId() {
        return id;
    }
    /** Returns part name */
    public String getName() {
        return name;
    }
    /** Returns part price */
    public double getPrice() {
        return price;
    }
    /** Returns part stock/inv */
    public int getStock() {
        return stock;
    }
    /** Returns part min */
    public int getMin() {
        return min;
    }
    /** Returns part max */
    public int getMax() {
        return max;
    }
    /** Set id to this id  */
    public void setId(int id) {
        this.id = id;
    }
    /** Set name to this name */
    public void setName(String name) {
        this.name = name;
    }
    /** Set price to this  price */
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
