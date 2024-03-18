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
     * Gets the manufacturer's suggested retail price (MSRP) for the product.
     *
     * @param product the product to get the MSRP for.
     * @return MSRP (double)
     */
    public static double getMSRP(Product product) {
        return product.getPrice();
    }

    /**
     * Calculates the marked-up price for a single product.
     *
     * @param product the product to calculate the price for.
     * @return marked-up price for a single unit of the product.
     */
    public static double getMarkedUpPrice(Product product) {
        return product.getPrice() * (1 + MARKUP);
    }

    /**
     * Calculates the amount of tax that will be applied to the total cost.
     *
     * @param total the total cost pre-tax
     * @return the tax amount
     */
    public static double calculateSalesTax(double total) {
        return total * TAX_RATE;
    }

    /**
     * Calculates the total price for a product, including the markup, before tax.
     *
     * @param product  the product to calculate the price for.
     * @param quantity the quantity of the product.
     * @return total marked-up price for the specified quantity of the product before tax.
     */
    public static double getSubtotal(Product product, int quantity) {
        double markedUpPrice = getMarkedUpPrice(product);
        return markedUpPrice * quantity;
    }

    /**
     * Calculates the after-tax price of the (marked-up) product.
     *
     * @param product the product to calculate the price for.
     * @return the after-tax price on the marked-up price for a single unit of the product.
     */
    public static double getTotalPriceBeforeDiscount(Product product) {
        double subtotal = getMarkedUpPrice(product);
        return subtotal + calculateSalesTax(subtotal);
    }

    /**
     * Calculates the total price for a product with a specific quantity, after applying tax and before any discounts.
     *
     * @param product  the product to calculate the total price for.
     * @param quantity the quantity of the product.
     * @return total price for the specified quantity of the product, including tax.
     */
    public static double getTotalPriceAfterTax(Product product, int quantity) {
        double subtotal = getSubtotal(product, quantity);
        return subtotal + calculateSalesTax(subtotal);
    }

    /**
     * Calculates the total price after a discount rate is applied.
     *
     * @param totalPriceBeforeDiscount the total price before discount.
     * @param discountRate             the discount rate to apply (as a decimal, e.g. 0.20 for 20%).
     * @return total price after the discount.
     */
    public static double getTotalPriceAfterDiscount(double totalPriceBeforeDiscount, double discountRate) {
        double discountAmount = totalPriceBeforeDiscount * discountRate;
        return totalPriceBeforeDiscount - discountAmount;
    }
}
