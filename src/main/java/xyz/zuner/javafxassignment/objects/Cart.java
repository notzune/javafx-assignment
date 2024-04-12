package xyz.zuner.javafxassignment.objects;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import xyz.zuner.javafxassignment.util.PricingUtil;

import java.util.*;


/**
 * <p>
 * Represents a shopping cart object.
 * </p>
 * <br>
 * <p>21:198:102/02 Computers and Programming II</p>
 * <p>JavaFX Assignment</p>
 * <p>Rutgers ID: 199009651</p>
 * <br>
 *
 * @author Zeyad "zmr15" Rashed
 * @mailto zmr15@scarletmail.rutgers.edu
 * @created 3/15/24, Friday
 */
public class Cart {

    private final List<CartItem> items = new ArrayList<>();
    private Discount cartDiscount; // cart-wide discounts
    private double cartWideDiscountAmount = 0.0; // stores cart-wide discount amount
    private Map<String, Discount> appliedDiscounts = new HashMap<>(); // track applied discounts

    /**
     * Adds a product to the cart. If the product already exists, its quantity is increased.
     * Checks inventory to ensure there are enough products available before adding.
     *
     * @param product  the product to add
     * @param quantity the quantity of the product to add
     */
    public void addProduct(Product product, int quantity) {
        if (!product.isAvailable(quantity)) {
            throw new IllegalArgumentException("Insufficient stock for " + product.getName());
        }

        CartItem existingItem = findItemByProductAndOptions(product);

        if (existingItem != null) {
            int combinedQuantity = existingItem.getQuantity() + quantity;
            if (!product.isAvailable(combinedQuantity)) {
                String errorMsg = "Adding quantity exceeds stock for " + product.getName();
                showErrorDialog("Error Adding Product", errorMsg);
                return;
            }
            // if enough stock, increase the quantity
            existingItem.add(quantity);
            product.reduceStock(quantity);
        } else {
            items.add(new CartItem(product, quantity));
            product.reduceStock(quantity);
        }
    }

    /**
     * Finds an existing cart item by matching the product and its selected options,
     * this ensures that products with different selected options are treated as separate cart items.
     *
     * @param product the product to find in the cart items.
     * @return CartItem, or null if no match is found.
     */
    private CartItem findItemByProductAndOptions(Product product) {
        for (CartItem item : items) {
            if (item.getProduct().equals(product) && item.getSelectedOptions().equals(product.getSelectedOptionsAsString())) {
                return item;
            }
        }
        return null;
    }

    /**
     * Removes a product from the cart.
     *
     * @param product the product to remove
     */
    public void removeProduct(Product product) {
        items.removeIf(item -> item.getProduct().equals(product));
    }

    /**
     * Gets the list of items in the cart.
     *
     * @return a list of CartItem objects
     */
    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    /**
     * Applies a discount via its code.
     *
     * @param code String representation of the discount code
     */
    public void applyDiscountCode(String code) {
        if (validatePromoCode(code)) {
            Discount discount = lookupDiscountByCode(code);
            if (discount.isItemSpecific()) {
                items.forEach(item -> {
                    if (discount.getApplicableProductUPCs().contains(item.getProduct().getUPC())) {
                        item.applyDiscount(discount);
                    }
                });
            } else {
                this.cartDiscount = discount;
                applyCartDiscount();
            }
            appliedDiscounts.put(code, discount);
        }
    }

    /**
     * Stores the discount amount for later.
     */
    public void applyCartDiscount() {
        if (this.cartDiscount != null) {
            cartWideDiscountAmount += cartDiscount.getAmount();
            // this won't work for %off cart-wide discounts in the future but shouldn't be a problem because the math should work out per item anyway
        }
    }

    /**
     * Clears the applied discounts.
     */
    public void clearDiscounts() {
        this.appliedDiscounts.clear();
        this.cartDiscount = null;
        this.cartWideDiscountAmount = 0.0;
        for (CartItem item : items) {
            item.removeDiscount();
        }
    }

    private Discount lookupDiscountByCode(String code) {
        return DiscountFactory.getDiscountByCode(code);
    }

    /**
     * Validates the discount code entered.
     *
     * @param code the code entered by the user.
     * @return true if the promo code is valid, false if it is invalid or expired.
     */
    private boolean validatePromoCode(String code) {
        Map<String, Discount> availableDiscounts = DiscountFactory.getDiscountMap();

        if (!availableDiscounts.containsKey(code)) {
            showErrorDialog("Invalid or Expired Promo Code", "The code you entered is either invalid or has expired.");
            return false;
        }
        return true;
    }

    /**
     * Utility method to show an error dialog with the specified title and message.
     *
     * @param title   the title of the dialog.
     * @param message the message to display in the dialog.
     */
    public void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-text-fill: red;");

        alert.showAndWait();
    }

    /**
     * Calculates the total cost of the items in the cart before tax.
     *
     * @return the subtotal cost.
     */
    public double getSubtotalCost() {
        return items.stream()
                .mapToDouble(item -> item.getQuantity() * PricingUtil.getMarkedUpPrice(item.getProduct()))
                .sum();
    }

    /**
     * Calculates the total sales tax for the items in the cart.
     *
     * @return the total tax amount.
     */
    public double getTotalTax() {
        return PricingUtil.calculateSalesTax(getSubtotalCost());
    }

    /**
     * Calculates the total cost of the items in the cart including tax but before discounts.
     *
     * @return the total cost including tax.
     */
    public double getTotalCostBeforeDiscounts() {
        return getSubtotalCost() + getTotalTax();
    }

    /**
     * Calculates the amount being discounted from the total.
     *
     * @return the total discount amount.
     */
    public double getTotalDiscountAmount() {
        return items.stream()
                .mapToDouble(item -> (PricingUtil.getMarkedUpPrice(item.getProduct()) * item.getQuantity()) - item.getDiscountedPrice())
                .sum() + cartWideDiscountAmount;
    }

    /**
     * Calculates the final total cost after all discounts are applied.
     *
     * @return the final total cost.
     */
    /**
     * Calculates the final total cost after all discounts are applied.
     *
     * @return the final total cost.
     */
    public double getTotalCostAfterDiscounts() {
        double itemsTotal = items.stream()
                .mapToDouble(CartItem::getDiscountedPrice)
                .sum();

        double totalCostAfterItemDiscounts = itemsTotal + getTotalTax();

        if (cartDiscount != null) {
            totalCostAfterItemDiscounts = cartDiscount.applyTo(totalCostAfterItemDiscounts, 1);
        }

        return totalCostAfterItemDiscounts;
    }

    /**
     * Gets a String representation of a list of discount codes being applied.
     *
     * @return String
     */
    public String getAppliedDiscountCodes() {
        if (appliedDiscounts.isEmpty()) {
            return "None";
        } else {
            return String.join("\n", appliedDiscounts.keySet());
        }
    }

    /**
     * Calculates the total number of items in the cart.
     *
     * @return the total item count.
     */
    public int getItemCount() {
        return items.stream().mapToInt(CartItem::getQuantity).sum();
    }

    /**
     * Clears all items from the cart.
     */
    public void clearCart() {
        items.clear();
    }

    /**
     * Returns a string representation of the cart, including all items and their quantities.
     *
     * @return a string representation of the cart
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Cart Contents:\n");
        for (CartItem item : items) {
            builder.append(item.getProduct().getName())
                    .append(" - ")
                    .append(item.getProduct().getSelectedOptionsAsString())
                    .append(": ")
                    .append(item.getQuantity())
                    .append("\n");
        }
        return builder.toString();
    }
}
