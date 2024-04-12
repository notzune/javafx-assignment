package xyz.zuner.javafxassignment.objects;

import java.net.URL;

/**
 * </p>
 * Represents a product in stock in the store.
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
public class Product {

    private String name;
    private String UPC; // universal product code
    private int stock;
    private double price; // manufacturer's price (MSRP)

    /**
     * Instantiates a new product object
     *
     * @param name  the name of the product
     * @param UPC   the Universal Product Code
     * @param price the manufacturer's price of the object
     */
    public Product(String name, String UPC, double price, int initialQuantity) {
        this.name = name;
        this.UPC = UPC;
        this.price = price;
        this.stock = initialQuantity;
    }

    /**
     * Gets the MSRP of the item
     *
     * @return MSRP (double)
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the Universal Product Code of this item
     *
     * @return UPC (String)
     */
    public String getUPC() {
        return UPC;
    }

    /**
     * Gets the quantity of the item
     *
     * @return quantity (int)
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the quantity of the product
     *
     * @param stock the new quantity
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Adds to the quantity count.
     *
     * @param additional The amount to add to the quantity.
     */
    public void add(int additional) {
        this.stock += additional;
    }

    /**
     * Method for checking whether the product is available or not.
     *
     * @param quantity the quantity to check for
     * @return boolean
     */
    public boolean isAvailable(int quantity) {
        return quantity <= stock;
    }

    /**
     * Reduces stock of the item by a certain amount.
     *
     * @param quantity the quantity to reduce the stock by.
     */
    public void reduceStock(int quantity) {
        if (quantity <= stock) {
            stock -= quantity;
        } else {
            throw new IllegalArgumentException("Insufficient stock for product " + name);
        }
    }

    /**
     * Adjusts the inventory quantity
     *
     * @param quantityChange the quantity amount change.
     *                       Can be positive (restock) or negative (sale).
     */
    public void adjustInventoryQuantity(int quantityChange) {
        this.stock += quantityChange;
    }

    /**
     * Gets the product name.
     *
     * @return the product name (String)
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the path to an image representing the product, if none is found, defaults to "No Image"
     *
     * @return image path (String)
     */
    public String getImagePath() {
        String imagePath = "/assets/products/" + this.UPC + ".jpg";

        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl != null) {
            return imageUrl.toExternalForm();
        } else {
            // fallback to a default image if the specific product image is not found
            imageUrl = getClass().getResource("/assets/products/no-image-available.jpg");
            return imageUrl != null ? imageUrl.toExternalForm() : null;
        }
    }

    /**
     * Checks if the given UPC matches the product's UPC.
     *
     * @param UPC the UPC to compare with the product's UPC.
     * @return boolean true if the UPCs match, false otherwise.
     */
    public boolean equals(String UPC) {
        return this.UPC.equals(UPC);
    }

    @Override
    public String toString() {
        return "Product {" +
                "\nname: " + name +
                "\nUPC: " + UPC +
                "\nprice: " + price +
                "\nquantity: " + stock
                + "\n}";
    }
}
