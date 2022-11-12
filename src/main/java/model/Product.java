package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Retrieves or creates a new Product
 *
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** Constructor for the product
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Returns the product ID
     *
     * @return product ID
     */
    public int getID() {
        return id;
    }

    /** Sets the product ID specific to the product
     *
     * @param id
     */
    public void setID(int id) {
        this.id = id;
    }

    /** Returns the product name
     *
     * @return product name
     */
    public String getName() {
        return name;
    }

    /** Sets the product name specific to the product
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns the product's price
     *
     * @return product price
     */
    public double getPrice() {
        return price;
    }

    /** Sets the price to the specific product
     *
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Returns the amount of inventory for the product
     *
     * @return product stock
     */
    public int getStock() {
        return stock;
    }

    /** Sets the stock specific to the product
     *
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Returns the minimum inventory of the product
     *
     * @return product minimum inventory
     */
    public int getMin() {
        return min;
    }

    /** Sets the products' minimum inventory
     *
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** Returns the maximum inventory of the product
     *
     * @return product maximum inventory
     *
     */
    public int getMax() {
        return max;
    }

    /** Sets the products' maximum inventory
     *
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** Adds the associated part the associated parts list for the product
     *
     * @param part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** Deletes the associated part from the associated parts list
     *
     * @param selectedAssociatedPart
     * @return true if product deleted
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /** Retrieves and returns the associated parts list for the product
     *
     * @return associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }


}
