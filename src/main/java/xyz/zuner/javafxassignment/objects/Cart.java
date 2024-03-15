package xyz.zuner.javafxassignment.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *     Represents a shopping cart object.
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
