package xyz.zuner.javafxassignment.objects;

/**
 * <p>
 * Represents an item in the shopping cart
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
 * @see Product
 */
public class CartItem {

    private final double MARKUP = 0.50; // apply a 50% mark up
    private Product product;
    private int quantity;
    private double discountRate;

    /**
     * Constructs a new CartItem with the given product and quantity.
     *
     * @param product  the product associated with this item in the cart.
     * @param quantity the quantity of the product.
     */
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.discountRate = 0; // Default to no discount
    }

    /**
     * Constructs a new CartItem with the given product, quantity, and discount.
     *
     * @param product      the product associated with this item in the cart.
     * @param quantity     the quantity of the product.
     * @param discountRate the applied discounted rate to the item.
     */
    public CartItem(Product product, int quantity, double discountRate) {
        this.product = product;
        this.quantity = quantity;
        this.discountRate = discountRate;
    }

    /**
     * Returns the product associated with this cart item.
     *
     * @return Product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Represents the quantity in the cart for this item
     *
     * @return quantity (int)
     */
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
     * Gets the current discount rate.
     *
     * @return Discount rate (double)
     */
    public double getDiscountRate() {
        return discountRate;
    }

    /**
     * Sets the discount rate.
     *
     * @param discountRate the rate to be applied to this item.
     */
    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
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
     * Calculates the total marked up price for this item before any discounts are applied.
     *
     * @return Total price before discount.
     */
    public double getTotalPriceBeforeDiscount() {
        double markup = product.getPrice() * quantity * MARKUP;
        return product.getPrice() * quantity + markup;
    }

    /**
     * Calculates the total price for this item after applying the discount.
     *
     * @return Total price after discount.
     */
    public double getTotalPriceAfterDiscount() {
        double discountAmount = getTotalPriceBeforeDiscount() * (discountRate / 100);
        return getTotalPriceBeforeDiscount() - discountAmount;
    }

    /**
     * Decreases the quantity by one.
     */
    public void remove() {
        if (this.quantity > 0) {
            this.quantity--;
        }
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
