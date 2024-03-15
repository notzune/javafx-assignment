package xyz.zuner.javafxassignment.objects;

/**
 * <p>
 * Represents an item in the shopping cart
 * </p>
 *
 * <p>21:198:102/02 Computers and Programming II</p>
 * <p>JavaFX Assignment</p>
 * <p>Rutgers ID: 199009651</p>
 *
 * @author Zeyad "zmr15" Rashed
 * @mailto zmr15@scarletmail.rutgers.edu
 * @created 3/14/24, Thursday
 * @see Product
 */
public class CartItem {

    private Product product;
    private int quantity;

    /**
     * Constructs a new CartItem with the given product and quantity;
     *
     * @param product   the product associated with this item in the cart.
     * @param quantity  the quantity of the product.
     */
    public CartItem(Product product, int quantity) {
        this. product = product;
        this.quantity = quantity;
    }

    /**
     * Returns the product associated with this cart item.
     *
     * @return Product
     */
    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product.
     *
     * @param quantity the new quantity.
     */
    public void setQuantity(int quantity) {
        this.quantity = Math.max(0, quantity); // Prevents quantity from being negative.
    }

    /**
     * Increases the quantity by one.
     */
    public void add() {
        this.quantity++;
    }

    /**
     * Increases the quantity by a specified amount.
     *
     * @param amount The amount to increase the quantity by.
     */
    public void add(int amount) {
        this.quantity += amount;
    }

    /**
     * Decreases the quantity by one.
     */
    public void remove() {
        if (this.quantity > 0) {
            this.quantity--;
        }
    }
}