package xyz.zuner.javafxassignment.objects;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
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
    private String generateReceipt() {
        StringBuilder receipt = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        receipt.append("Z's Discount Electronics!\n");
        receipt.append("Transaction ID: ").append(id).append("\n");
        receipt.append("Timestamp: ").append(timestamp.format(formatter)).append("\n");

        receipt.append("\nItems Purchased:\n");
        for (CartItem item : items) {
            receipt.append("- ").append(item.getProduct().getName())
                    .append("(").append(item.getSelectedOptions()).append(")")
                    .append(" x ").append(item.getQuantity())
                    .append(": $").append(String.format("%.2f", item.getDiscountedPrice())).append("\n")
                    .append(item.getProduct().getDescription());
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

    /**
     * Method for saving the receipt to a text file with a default path fallback.
     *
     * @param receipt The receipt content to save.
     */
    private void saveToDrive(String receipt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the directory path where you want to save the receipt, or press ENTER to use the default location:");
        String directory = scanner.nextLine();
        Path path;

        if (directory.isEmpty()) {
            path = Paths.get(System.getProperty("user.home"), "javafx-receipts", id + ".txt");
            System.out.println("No directory provided. Using default directory: " + path.getParent());
        } else {
            path = Paths.get(directory, id + ".txt");
        }

        try {
            Files.createDirectories(path.getParent());
            Files.writeString(path, receipt, StandardCharsets.UTF_8);
            System.out.println("Receipt saved successfully to " + path);
        } catch (IOException e) {
            System.out.println("Error saving the receipt: " + e.getMessage());
        }
    }

    /**
     * Generates a detailed receipt for the transaction and saves to a text file with a default path fallback.
     *
     * @return The detailed receipt as a String.
     */
    public String processTransaction() {
        String receipt = generateReceipt();
        saveToDrive(receipt);
        return receipt;
    }
}
