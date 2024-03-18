package xyz.zuner.javafxassignment.objects;

import java.util.HashMap;
import java.util.Map;

/**
 * </p>
 * Manages the inventory of the store.
 * </p>
 * <br>
 * <p>21:198:102/02 Computers and Programming II</p>
 * <p>JavaFX Assignment</p>
 * <p>Rutgers ID: 199009651</p>
 * <br>
 *
 * @author Zeyad "zmr15" Rashed
 * @mailto zmr15@scarletmail.rutgers.edu
 * @created 3/14/24, Thursday
 */
public class Inventory {

    private Map<String, Product> products;

    /**
     * Instantiates a new Inventory object with an empty product list.
     */
    public Inventory() {
        this.products = new HashMap<>();
    }

    /**
     * Adds a product to the inventory or updates the quantity if it already exists.
     *
     * @param product the product to add or update in the inventory
     */
    public void addOrUpdateProduct(Product product) {
        // check if the product already exists in the inventory
        Product existingProduct = products.get(product.getUPC());
        if (existingProduct != null) {
            // product exists, so update its quantity
            existingProduct.add(product.getQuantity());
        } else {
            // new product, add it to the inventory
            products.put(product.getUPC(), product);
        }
    }

    /**
     * Finds a product in the inventory by its UPC.
     *
     * @param UPC the Universal Product Code of the product to find
     * @return the Product object if found, or null otherwise
     */
    public Product findProductByUPC(String UPC) {
        return products.get(UPC);
    }

    /**
     * Updates the quantity of a product in the inventory.
     *
     * @param UPC      the Universal Product Code of the product to update
     * @param quantity the new quantity to set
     */
    public void updateProductQuantity(String UPC, int quantity) {
        Product product = products.get(UPC);
        if (product != null) {
            product.setQuantity(quantity);
        }
    }

    /**
     * Updates the quantity of a product in the inventory.
     *
     * @param product  the product to update
     * @param quantity the new quantity to set
     */
    public void updateProductQuantity(Product product, int quantity) {
        if (product != null) {
            product.setQuantity(quantity);
        }
    }

    /**
     * Retrieves all products in the inventory.
     *
     * @return a map of products keyed by their UPC.
     */
    public Map<String, Product> getProducts() {
        return this.products;
    }
}
