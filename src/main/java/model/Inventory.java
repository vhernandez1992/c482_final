package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Methods used to access the inventory of parts or products
 *
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static int partID = 0;

    /** Adds the part to the Parts list
     *
     * @param newPart
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /** Generates a new part ID so there are no repeating part ID's
     *
     * @return new unique part ID
     */
    public static int newPartID() {
        partID = partID + 1;
        return partID;
    }

    /** Searches though the parts list for a matching part ID
     *
     * @param partID
     * @return matching part ID or returns no infomation if no match found
     *
     */
    public static Part lookupPart(int partID) {
        for (Part tempID : allParts) {
            if (tempID.getId() == partID) {
                return tempID;
            }
        }
        return null;
    }

    /** Searches the parts list to find a matching partial or
     * full part name
     *
     * @param partName
     * @return matching parts to the parts list
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partSearchResults = FXCollections.observableArrayList();

        for (Part tempPart : allParts) {

            if (tempPart.getName().toLowerCase().contains(partName.toLowerCase())) {
                 partSearchResults.add(tempPart);
            }
        }
        return partSearchResults;
    }

    /** Updates the modified part to the parts list
     *
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /** Deletes the selected part from the parts list
     *
     * @param selectedPart
     * @return true if the part has been deleted
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /** Retrieves and returns all parts
     *
     * @return all parts from the parts list
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }


    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int productID;

    /** Adds the product to the product list
     *
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /** Generates a new product ID to prevent repeating product ID's
     *
     * @return newly generated product ID
     */
    public static int newProductID() {
        productID = productID + 1;
        return productID;
    }

    /** Looks up to see if a matching product ID can be found
     *
     * @param productID
     * @return matching product ID or nothing if no match found
     */
    public static Product lookupProduct(int productID) {
        for (Product tempProduct : allProducts) {
            if (tempProduct.getID() == productID) {
                return tempProduct;
            }
        }
        return null;
    }

    /** Searches the product list for  a partial or full matching
     * product name
     * @param productName
     * @return matching product list
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productSearchResults = FXCollections.observableArrayList();

        for (Product tempProduct : allProducts) {
            if (tempProduct.getName().toLowerCase().contains(productName.toLowerCase())) {
                productSearchResults.add(tempProduct);
            }
        }

        return productSearchResults;
    }

    /** Updates the modified product and adds into the product list
     * at the selected index
     *
     * @param index
     * @param selectedProduct
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /** Deletes selected product from the product list
     *
     * @param selectedProduct
     * @return true if product has been deleted
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }

    /** Retrieves and returns all products from the product list
     *
     * @return all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
