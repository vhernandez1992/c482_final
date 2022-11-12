package model;

/** Extends the Part to specify the Outsourced Company Name
 *
 */
public class Outsourced extends Part{

    private String companyName;

    /** Constructor for the Outsourced Part
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** Returns the parts company name
     *
     * @return company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /** Sets the user input for the company name to the selected part
     *
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
