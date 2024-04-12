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
     *
     * @param product  the product to add
     * @param quantity the quantity of the product to add
     */
    public void addProduct(Product product, int quantity) {
        Optional<CartItem> existingItem = items.stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().add(quantity);
        } else {
            items.add(new CartItem(product, quantity));
        }
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
            showInvalidDiscountError();
            return false;
        }
        return true;
    }

    /**
     * Shows an error when an invalid coupon code is entered.
     */
    private void showInvalidDiscountError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid or Expired Promo Code");
        alert.setHeaderText(null);
        alert.setContentText("The code you entered is either invalid or has expired.");

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
        StringBuilder sb = new StringBuilder("Cart Contents:\n");
        for (CartItem item : items) {
            sb.append(item).append("\n");
        }
        return sb.toString();
    }
}
