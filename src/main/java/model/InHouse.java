package model;

/** Extends the Part class to specify the Machine ID for the part
 *
 */
public class InHouse extends Part {

    private int machineID;

    /** Constructor for the In-house type Part
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /** Retrieves and returns the Machine ID for the part
     *
     * @return Machine ID
     */
    public int getMachineID() {
        return machineID;
    }

    /** Sets the Machine ID for the part
     *
     * @param machineID
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}

