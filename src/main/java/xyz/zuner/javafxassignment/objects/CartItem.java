package xyz.zuner.javafxassignment.objects;

import xyz.zuner.javafxassignment.util.PricingUtil;

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

    private Product product;
    private int quantity;
    private Discount discount;
    private double discountedPrice;
    private String selectedOptions;

    /**
     * Constructs a new CartItem with the given product and quantity.
     *
     * @param product  the product associated with this item in the cart.
     * @param quantity the quantity of the product.
     */
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.selectedOptions = product.getSelectedOptionsAsString();
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
        this.quantity = Math.max(0, quantity);
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

    /**
     * Apply discounted price to the item.
     *
     * @param discount the discount to be applied.
     */
    public void applyDiscount(Discount discount) {
        this.discount = discount;
        if (discount != null) {
            this.discountedPrice = discount.applyTo(PricingUtil.getMarkedUpPrice(product) * quantity, quantity);
        }
    }

    /**
     * Gets the newly calculated price after applying discounts. If no discount, price is the regular marked up price.
     *
     * @return Discounted price, else regular price.
     */
    public double getDiscountedPrice() {
        // return the total price if no discount is applied
        return (discount == null) ? PricingUtil.getMarkedUpPrice(product) * quantity : discountedPrice;
    }

    /**
     * Removes the discount and reverts items to original cost.
     */
    public void removeDiscount() {
        this.discount = null;
        this.discountedPrice = PricingUtil.getMarkedUpPrice(product) * quantity;
    }

    /**
     * Retrieves the selected options for the product in this cart item.
     *
     * @return String
     */
    public String getSelectedOptions() {
        return selectedOptions;
    }

    /**
     * Returns a string representation of this cart item, including the product details,
     * selected options, and quantity.
     *
     * @return String
     */
    @Override
    public String toString() {
        return String.format("%s x%d\nOptions: %s", product.getName(), quantity, selectedOptions);
    }
}
