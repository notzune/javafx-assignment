package xyz.zuner.javafxassignment.util;

import xyz.zuner.javafxassignment.objects.Product;

/**
 * <p>
 * Pricing utility class for displaying marked up prices, and calculating discounts.
 * </p>
 * <br>
 * <p>21:198:102/02 Computers and Programming II</p>
 * <p>JavaFX Assignment</p>
 * <p>Rutgers ID: 199009651</p>
 * <br>
 *
 * @author Zeyad "zmr15" Rashed
 * @mailto zmr15@scarletmail.rutgers.edu
 * @created 3/16/24, Saturday
 */
public class PricingUtil {

    private static final double MARKUP = 0.50; // apply a 50% mark up
    private static final double TAX_RATE = 0.07; // sales tax rate

    /**
     * Gets the manufacturer's price for the product (MSRP)
     *
     * @param product the product to get the warehouse price for.
     * @return MSRP (double)
     */
    public static double getMSRP(Product product) {
        return product.getPrice();
    }

    /**
     * Calculates the marked-up price for a single product before any discounts are applied.
     *
     * @param product the product to calculate the price for.
     * @return marked-up price for a single unit of the product.
     */
    public static double getMarkedUpPrice(Product product) {
        return product.getPrice() * (1 + MARKUP);
    }

    /**
     * Calculates the after tax price of the (marked-up) product.
     *
     * @param product the product to calculate the price for.
     * @return the after-tax price on the marked-up price for a single unit of the product.
     */
    public static double getAfterTaxPrice(Product product) {
        return getMarkedUpPrice(product) * (1 + TAX_RATE);
    }

    /**
     * Calculates the total price for a product with a specific quantity before any discounts are applied.
     *
     * @param product  the product to calculate the price for.
     * @param quantity the quantity of the product.
     * @return total marked-up after-tax price for the specified quantity of the product.
     */
    public static double getTotalPriceBeforeDiscount(Product product, int quantity) {
        double markedUpPrice = getMarkedUpPrice(product);
        return markedUpPrice * quantity + (1 + TAX_RATE);
    }

    /**
     * Calculates the price for a product after applying a discount rate.
     *
     * @param product      the product to calculate the discount for.
     * @param quantity     the quantity of the product.
     * @param discountRate the discount rate to apply (as a decimal, e.g. 0.20 for 20%).
     * @return total price for the specified quantity of the product after the discount.
     */
    public static double getTotalPriceAfterDiscount(Product product, int quantity, double discountRate) {
        double totalPriceBeforeDiscount = getTotalPriceBeforeDiscount(product, quantity);
        double discountAmount = totalPriceBeforeDiscount * discountRate;
        return totalPriceBeforeDiscount - discountAmount;
    }
}
