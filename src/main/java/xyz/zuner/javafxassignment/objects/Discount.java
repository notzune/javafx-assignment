package xyz.zuner.javafxassignment.objects;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

/**
 * <p>
 * Represents a Discount coupon to be applied to the Cart or CartItems
 * </p>
 * <br>
 * <p>21:198:102/02 Computers and Programming II</p>
 * <p>JavaFX Assignment</p>
 * <p>Rutgers ID: 199009651</p>
 * <br>
 *
 * @author Zeyad "zmr15" Rashed
 * @mailto zmr15@scarletmail.rutgers.edu
 * @created 3/17/24, Sunday
 * @see Cart
 * @see CartItem
 * @see Transaction
 */
public class Discount {

    private String code;
    @SerializedName("discount_amount")
    private double amount;
    @SerializedName("is_percentage")
    private boolean isPercentage;
    @SerializedName("discount_type")
    private DiscountType discountType;
    @SerializedName("required_quantity")
    private int requiredQuantity; // quantity required to trigger discount (for things like BOGO)
    @SerializedName("discount_on_additional")
    private double discountOnAdditional;
    @SerializedName("is_item_specific")
    private boolean isItemSpecific;
    @SerializedName("applicable_product_upcs")
    private Set<String> applicableProductUPCs;

    /**
     * Constructor for flat rate and percentage discounts.
     */
    public Discount(String code, double amount, boolean isPercentage, boolean isItemSpecific, Set<String> applicableProductUPCs) {
        this.code = code;
        this.amount = amount;
        this.isPercentage = isPercentage;
        this.discountType = isPercentage ? DiscountType.PERCENTAGE : DiscountType.FLAT;
        this.isItemSpecific = isItemSpecific;
        this.applicableProductUPCs = applicableProductUPCs;
    }

    /**
     * Constructor for BOGO and BOGO_PERCENTAGE discounts.
     */
    public Discount(String code, DiscountType discountType, int requiredQuantity, double discountOnAdditional, boolean isItemSpecific, Set<String> applicableProductUPCs) {
        this.code = code;
        this.discountType = discountType;
        this.requiredQuantity = requiredQuantity;
        this.discountOnAdditional = discountOnAdditional;
        this.isPercentage = discountType == DiscountType.BOGO_PERCENTAGE;
        this.isItemSpecific = isItemSpecific;
        this.applicableProductUPCs = applicableProductUPCs;
    }

    /**
     * Gets the discount code.
     *
     * @return discount code (String).
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets the amount of the discount.
     *
     * @return the amount being taken off.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Applies this discount based on the type of discount and quantity of items.
     *
     * @param originalAmount the original amount before discount
     * @param quantity       the quantity of items being purchased
     * @return the adjusted amount after applying the discount
     */
    public double applyTo(double originalAmount, int quantity) {
        switch (discountType) {
            case FLAT:
                return originalAmount - amount;
            case PERCENTAGE:
                return originalAmount * (1 - amount);
            case BOGO:
                // assumes the free item is of equal or lesser value (simple BOGO)
                int eligibleFreeItems = quantity / requiredQuantity;
                return originalAmount - (eligibleFreeItems * (originalAmount / quantity));
            case BOGO_PERCENTAGE:
                int discountItems = quantity / requiredQuantity;
                double discountAmount = originalAmount / quantity * discountItems * discountOnAdditional;
                return originalAmount - discountAmount;
            default:
                return originalAmount;
        }
    }

    /**
     * Checks if the Discount is item specific or not.
     *
     * @return boolean
     */
    public boolean isItemSpecific() {
        return isItemSpecific;
    }

    /**
     * Returns the set of UPCs for which this Discount is applicable.
     *
     * @return Set
     */
    public Set<String> getApplicableProductUPCs() {
        return applicableProductUPCs;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "code='" + code + '\'' +
                ", amount=" + amount +
                ", isPercentage=" + isPercentage +
                ", discountType=" + discountType +
                ", requiredQuantity=" + requiredQuantity +
                ", discountOnAdditional=" + discountOnAdditional +
                ", isItemSpecific=" + isItemSpecific +
                ", applicableProductUPCs=" + applicableProductUPCs +
                '}';
    }

    /**
     * Enum to represent different types of discounts.
     */
    public enum DiscountType {
        FLAT,
        PERCENTAGE,
        BOGO,
        BOGO_PERCENTAGE
    }
}
