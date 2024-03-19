package xyz.zuner.javafxassignment.objects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * Represents a transaction for logging purchases and managing/keeping track of inventory
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
 */
public class Transaction {

    private final String id;
    private final LocalDateTime timestamp;
    private final String appliedDiscounts;
    private final List<CartItem> items;
    private final double subtotal;
    private final double totalTax;
    private final double totalDiscount;
    private final double total;

    /**
     * Constructor for Transaction.
     *
     * @param items         list of CartItem objects involved in the transaction.
     * @param subtotal      subtotal of the transaction before taxes and discounts.
     * @param totalTax      total tax amount for the transaction.
     * @param totalDiscount total discount amount applied to the transaction.
     * @param total         final total amount after all taxes and discounts.
     */
    public Transaction(String appliedDiscounts, List<CartItem> items, double subtotal, double totalTax, double totalDiscount, double total) {
        this.appliedDiscounts = appliedDiscounts;
        this.id = UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now();
        this.items = items;
        this.subtotal = subtotal;
        this.totalTax = totalTax;
        this.totalDiscount = totalDiscount;
        this.total = total;
    }

    /**
     * Generates a detailed receipt for the transaction.
     *
     * @return The detailed receipt as a String.
     */
    public String generateReceipt() {
        StringBuilder receipt = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        receipt.append("Z's Discount Electronics!\n");
        receipt.append("Transaction ID: ").append(id).append("\n");
        receipt.append("Timestamp: ").append(timestamp.format(formatter)).append("\n");

        receipt.append("\nItems Purchased:\n");
        for (CartItem item : items) {
            receipt.append("- ").append(item.getProduct().getName())
                    .append(" x ").append(item.getQuantity())
                    .append(": $").append(String.format("%.2f", item.getDiscountedPrice())).append("\n");
        }

        receipt.append("\nDiscounts:\n");
        receipt.append(appliedDiscounts).append("\n");

        receipt.append("\n----------------------------------\n");
        receipt.append(String.format("Subtotal:            $%.2f\n", subtotal));
        receipt.append(String.format("Tax:                 $%.2f\n", totalTax));
        receipt.append(String.format("Total Discount:      -$%.2f\n", totalDiscount));
        receipt.append(String.format("Total:               $%.2f\n", total));
        receipt.append("----------------------------------\n");
        receipt.append("\n!!!THANK YOU!!!\n");

        return receipt.toString();
    }
}
