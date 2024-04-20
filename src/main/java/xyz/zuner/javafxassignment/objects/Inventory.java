package xyz.zuner.javafxassignment.objects;

import java.util.Arrays;
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
        initializeInventory();
    }

    private void initializeInventory() {
        Product laptop = new Product("Laptop", "001", 999.99, 20);
        laptop.addOption("Color", Arrays.asList("Black", "Silver", "White"));
        laptop.addOption("Storage", Arrays.asList("256GB SSD", "512GB SSD", "1TB SSD"));

        products.put(laptop.getUPC(), laptop);

        Product smartphone = new Product("Smartphone", "002", 499.99, 12);
        smartphone.addOption("Color", Arrays.asList("Black", "Blue", "Red"));
        smartphone.addOption("Storage", Arrays.asList("64GB", "128GB", "256GB"));

        products.put(smartphone.getUPC(), smartphone);

        Product watch = new Product("Smartwatch", "003", 249.99, 6);
        watch.addOption("Color", Arrays.asList("Black", "Blue", "Red"));
        watch.addOption("Size", Arrays.asList("35mm", "40mm"));

        products.put(watch.getUPC(), watch);

        Product camera = new Product("Camera", "004", 449.99, 25);
        camera.addOption("Color", Arrays.asList("Black", "Silver", "White"));
        camera.addOption("Storage", Arrays.asList("64GB", "128GB", "256GB"));

        products.put(camera.getUPC(), camera);
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
            existingProduct.add(product.getStock());
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
    public void updateStock(String UPC, int quantity) {
        Product product = findProductByUPC(UPC);
        if (product != null) {
            product.reduceStock(quantity);
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
