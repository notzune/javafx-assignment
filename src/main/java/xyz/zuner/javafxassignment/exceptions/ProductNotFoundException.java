package xyz.zuner.javafxassignment.exceptions;

/**
 * Exception message for if a product is not found in the system/inventory.
 *
 * 21:198:102/02: Computers and Programming II
 * JavaFX Assignment
 * Rutgers ID - 199009651
 *
 * @author Zeyad "zmr15" Rashed
 * @mailto zmr15@scarletmail.rutgers.edu
 * @created 3/14/24, Thursday
 **/
public class ProductNotFoundException extends Exception {

    public ProductNotFoundException(String message) {
        super("Product could not found in the system!");
    }
}
