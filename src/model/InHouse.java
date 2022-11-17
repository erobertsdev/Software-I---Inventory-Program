package model;
/** @author Elias Adams-Roberts */
/** Model for an in house part. */
public class InHouse extends Part {
    private int machineID;

    /** Constructor for an in house part.
     * @param id part ID
     * @param machineID Machine ID for part
     * @param max Max inventory for part.
     * @param min Minimum inventory for part.
     * @param name Name of the part.
     * @param price Price of the part.
     * @param stock Current stock for the part. */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);

        this.machineID = machineID;
    }

    /** Method to set the machine ID
     * @param machineID The Machine ID of the part. */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    /** Method to return the Machine ID of a part.
     * @return The machine ID. */
    public int getMachineID() {
        return machineID;
    }
}
