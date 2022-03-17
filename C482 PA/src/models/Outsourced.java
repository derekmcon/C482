package models;

/** Outsourced class that extends Part class */
public class Outsourced extends Part {
    // Declare fields
    private String companyName;

    /** Declaring Outsourced method with parameters */
    // Declare methods
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        // Super has to be first line in method. Java rule.
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    /** Returns company name */
    public String getCompanyName() {
        return companyName;
    }
    /** Sets company name to this company name */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
