package xyz.zuner.javafxassignment.objects;

import java.util.*;

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

    // product categories
    private final String[] categories = {"Smartphones", "Laptops", "Smartwatches", "Cameras"};
    private HashMap<String, List<Product>> products;
    private Map<String, Product> productsByUPC = new HashMap<>();


    /**
     * Instantiates a new Inventory object with an empty product list.
     */
    public Inventory() {
        this.products = new HashMap<>();
        initializeInventory();
    }

    /**
     * Initializes the full inventory.
     *
     * @see #initializeLaptops()
     * @see #initializeSmartphones()
     */
    private void initializeInventory() {
        initializeLaptops();
        initializeSmartphones();
        initializeSmartwatches();
        initializeCameras();
    }

    /**
     * Helper method for initializing the laptop category of the inventory.
     */
    private void initializeLaptops() {
        Product thinkpad = new Product("Lenovo Thinkpad", "001", 999.99, 20, "Laptops");
        thinkpad.addOption("Color", Arrays.asList("Black", "Silver", "White"));
        thinkpad.addOption("Storage", Arrays.asList("256GB SSD", "512GB SSD", "1TB SSD"));

        Product surface = new Product("Surface Pro", "005", 1999.99, 22, "Laptops");
        surface.addOption("Color", Arrays.asList("Black", "Silver", "White"));
        surface.addOption("Storage", Arrays.asList("256GB SSD", "512GB SSD", "1TB SSD"));

        Product macbook = new Product("Macbook Pro", "006", 2999.99, 22, "Laptops");
        macbook.addOption("Color", Arrays.asList("Silver", "White"));
        macbook.addOption("Storage", Arrays.asList("256GB SSD", "512GB SSD", "1TB SSD"));

        Product macbookAir = new Product("Macbook Air", "007", 5999.99, 22, "Laptops");
        macbookAir.addOption("Color", Arrays.asList("Black", "Silver"));
        macbookAir.addOption("Storage", Arrays.asList("256GB SSD", "512GB SSD", "1TB SSD"));

        Product chromebook = new Product("Chromebook", "107", 99.99, 22, "Laptops");
        macbookAir.addOption("Color", List.of("Black"));
        macbookAir.addOption("Storage", Arrays.asList("256GB SSD", "512GB SSD", "1TB SSD"));

        products.put("Laptops", new ArrayList<>(Arrays.asList(thinkpad, surface, macbook, macbookAir, chromebook)));
        addProduct(new Product[]{thinkpad, surface, macbook, macbookAir, chromebook});
    }

    /**
     * Helper method for initializing the smartphone category of the inventory.
     */
    private void initializeSmartphones() {
        Product iphone15 = new Product("iPhone 15", "002", 499.99, 12, "Smartphones");
        iphone15.addOption("Color", Arrays.asList("Black", "Blue", "Red"));
        iphone15.addOption("Storage", Arrays.asList("64GB", "128GB", "256GB"));

        Product iphone15pro = new Product("iPhone 15 Pro", "008", 899.99, 12, "Smartphones");
        iphone15pro.addOption("Color", Arrays.asList("Black", "Blue", "Red"));
        iphone15pro.addOption("Storage", Arrays.asList("64GB", "128GB", "256GB"));

        Product galaxy = new Product("Samsung Galaxy", "009", 529.99, 12, "Smartphones");
        galaxy.addOption("Color", Arrays.asList("Black", "Blue", "Red"));
        galaxy.addOption("Storage", Arrays.asList("64GB", "128GB", "256GB"));

        Product pixel = new Product("Google Pixel", "019", 199.99, 12, "Smartphones");
        pixel.addOption("Color", Arrays.asList("Black", "Blue", "Red"));
        pixel.addOption("Storage", Arrays.asList("64GB", "128GB", "256GB"));

        Product motorolla = new Product("Motorolla Razer", "119", 199.99, 12, "Smartphones");
        motorolla.addOption("Color", Arrays.asList("Black", "Silver", "White"));
        motorolla.addOption("Storage", Arrays.asList("64GB", "128GB", "256GB"));

        products.put("Smartphones", new ArrayList<>(Arrays.asList(iphone15, iphone15pro, galaxy, pixel, motorolla)));
        addProduct(new Product[]{iphone15, iphone15pro, galaxy, pixel, motorolla});
    }

    /**
     * Helper method for initializing the smartwatch category of the inventory.
     */
    private void initializeSmartwatches() {
        Product appleWatch = new Product("Apple Watch", "003", 249.99, 6, "Smartwatches");
        appleWatch.addOption("Color", Arrays.asList("Black", "Blue", "Red"));
        appleWatch.addOption("Size", Arrays.asList("35mm", "40mm"));

        Product appleWatchPro = new Product("Apple Watch Pro", "103", 449.99, 6, "Smartwatches");
        appleWatchPro.addOption("Color", Arrays.asList("Black", "Blue", "Red"));
        appleWatchPro.addOption("Size", Arrays.asList("35mm", "40mm"));

        Product galaxyWatch = new Product("Galaxy Watch", "010", 249.99, 6, "Smartwatches");
        galaxyWatch.addOption("Color", Arrays.asList("Black", "Blue", "Red"));
        galaxyWatch.addOption("Size", Arrays.asList("35mm", "40mm", "45mm"));

        Product samsungWatch = new Product("Samsung Watch", "110", 249.99, 6, "Smartwatches");
        samsungWatch.addOption("Color", Arrays.asList("Black", "Blue", "Red"));
        samsungWatch.addOption("Size", Arrays.asList("35mm", "40mm", "45mm"));

        Product fitbit = new Product("Fitbit", "011", 249.99, 6, "Smartwatches");
        fitbit.addOption("Color", Arrays.asList("Black", "Blue", "Red"));
        fitbit.addOption("Size", Arrays.asList("35mm", "40mm", "45mm"));

        products.put("Smartwatches", new ArrayList<>(Arrays.asList(appleWatch, appleWatchPro, galaxyWatch, fitbit, samsungWatch)));
        addProduct(new Product[]{appleWatch, appleWatchPro, galaxyWatch, fitbit, samsungWatch});
    }

    /**
     * Helper method for initializing the camera category of the inventory.
     */
    private void initializeCameras() {
        Product nikon = new Product("Nikon Coolpix", "004", 449.99, 25, "Cameras");
        nikon.addOption("Color", Arrays.asList("Black", "Silver"));
        nikon.addOption("Storage", Arrays.asList("64GB", "128GB", "256GB"));

        Product dslr = new Product("Canon DSLR", "014", 449.99, 25, "Cameras");
        dslr.addOption("Color", Arrays.asList("Black", "Gray"));
        dslr.addOption("Storage", Arrays.asList("64GB", "128GB", "256GB"));

        Product huji = new Product("HujiFilm Disposable", "015", 10.99, 25, "Cameras");
        Product genericDispo = new Product("Disposable Camera", "016", 5.99, 25, "Cameras");
        Product film = new Product("Film", "017", 2.99, 25, "Cameras");

        products.put("Cameras", new ArrayList<>(Arrays.asList(nikon, dslr, huji, genericDispo, film)));
        addProduct(new Product[]{nikon, dslr, huji, genericDispo, film});
    }

    /**
     * Adds a product to the inventory or updates the quantity if it already exists.
     *
     * @param product the product to add or update in the inventory
     */
    public void addOrUpdateProduct(Product product) {
        // check if the product already exists in the inventory
        Product existingProduct = productsByUPC.get(product.getUPC());
        if (existingProduct != null) {
            // product exists, so update its quantity
            existingProduct.add(product.getStock());
        } else {
            // new product, add it to the inventory
            productsByUPC.put(product.getUPC(), product);
        }
    }

    /**
     * Adds a single product to the UPC HashMap.
     *
     * @param product the product to add
     */
    public void addProduct(Product product) {
        productsByUPC.put(product.getUPC(), product);
    }

    /**
     * Adds an array of products to the UPC HashMap.
     *
     * @param products the array of products to add
     */
    public void addProduct(Product[] products) {
        for (Product product : products) {
            productsByUPC.put(product.getUPC(), product);
        }
    }

    /**
     * Finds a product in the inventory by its UPC.
     *
     * @param UPC the Universal Product Code of the product to find
     * @return the Product object if found, or null otherwise
     */
    public Product getProductByUPC(String UPC) {
        if (UPC == null || UPC.isEmpty()) {
            throw new IllegalArgumentException("UPC cannot be null or empty");
        }
        return productsByUPC.getOrDefault(UPC, null);
    }

    /**
     * Gets a list of products associated with a certain category.
     *
     * @param category the category to search
     * @return list of Product objects if found, null otherwise
     */
    public List<Product> getProductsByCategory(String category) {
        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }
        return products.getOrDefault(category, null);
    }

    /**
     * Updates the quantity of a product in the inventory.
     *
     * @param UPC      the Universal Product Code of the product to update
     * @param quantity the new quantity to set
     */
    public void updateStock(String UPC, int quantity) {
        Product product = getProductByUPC(UPC);
        if (product != null) {
            product.reduceStock(quantity);
        }
    }

    /**
     * Retrieves all products from all categories.
     *
     * @return List<Product> a list containing all products.
     */
    public List<Product> getAllProducts() {
        List<Product> allProducts = new ArrayList<>();
        for (List<Product> productList : products.values()) {
            allProducts.addAll(productList);
        }
        return allProducts;
    }

    /**
     * Returns a String array of available product categories.
     *
     * @return String[]
     */
    public String[] getCategories() {
        return categories;
    }
}
