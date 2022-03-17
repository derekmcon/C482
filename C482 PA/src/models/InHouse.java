package models;

/** InHouse class that extends Part class */
public class InHouse extends Part {
    // Declare fields
    private int machineID;

    /** Declaring InHouse method with parameters */
    // Declare methods
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        // Super has to be first line in method. Java rule.
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }
    /** Returns machine id */
    public int getMachineID() {
        return machineID;
    }
    /** Sets machine id to this machine id */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
