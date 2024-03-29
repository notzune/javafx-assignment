package xyz.zuner.javafxassignment;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import xyz.zuner.javafxassignment.objects.*;
import xyz.zuner.javafxassignment.util.PricingUtil;

/**
 * <p>
 * Main class.
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
 */
public class StoreApplication extends Application {

    private Cart cart = new Cart();
    private VBox cartView;
    private ScrollPane cartScrollPane;
    private VBox cartItemsContainer;
    private Label subtotalLabel, taxLabel, discountsLabel, totalLabel, totalDiscountLabel;
    private Label itemCountLabel;

    // buttons for checkout and printing receipt
    private Button proceedToCheckoutButton;
    private Button printReceiptButton;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the application.
     */
    @Override
    public void start(Stage stage) {
        Inventory inventory = initInventory();

        BorderPane root = new BorderPane();
        // create header
        itemCountLabel = new Label("0");
        itemCountLabel.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-padding: 3px;");
        root.setTop(createHeader());

        // create product listing
        root.setCenter(createProductListing(inventory));

        // create shopping cart
        this.cartView = createCartView();
        root.setRight(cartView);

        Scene scene = new Scene(root, 960, 540);
        stage.setTitle("Electronics Store");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creates the header box
     *
     * @return HBox
     */
    private HBox createHeader() {
        HBox header = new HBox();
        header.setPadding(new Insets(15));
        header.setAlignment(Pos.CENTER);
        Label title = new Label("Z's Discount Electronics Store!");
        title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-text-fill: #279f00; -fx-font-weight: bold; -fx-font-size: 30px");

        Label cartIcon = new Label("\uD83D\uDED2"); // unicode for shopping cart
        cartIcon.setStyle("-fx-font-size: 24px;");

        Button cartButton = new Button("", cartIcon);
        cartButton.setGraphic(new VBox(cartIcon, itemCountLabel)); // stack cart icon and item count badge
        cartButton.setStyle("-fx-background-color: transparent;");
        cartButton.setContentDisplay(ContentDisplay.TOP);
        cartButton.setOnAction(event -> toggleCartVisibility());

        HBox rightHeader = new HBox(cartButton);
        rightHeader.setAlignment(Pos.CENTER_RIGHT);

        HBox.setHgrow(rightHeader, Priority.ALWAYS);
        header.getChildren().addAll(title, rightHeader);

        return header;
    }

    /**
     * Initializes the store's inventory.
     *
     * @return Inventory
     */
    private Inventory initInventory() {
        Inventory inventory = new Inventory();

        inventory.addOrUpdateProduct(new Product("Laptop", "001", 999.99));
        inventory.addOrUpdateProduct(new Product("Smartphone", "002", 599.99));
        inventory.addOrUpdateProduct(new Product("Smartwatch", "003", 249.99));
        inventory.addOrUpdateProduct(new Product("Camera", "004", 449.99));

        return inventory;
    }

    /**
     * Creates the card to display the product in the cart.
     *
     * @param product the product to be displayed
     * @return VBox
     */
    private VBox createProductCard(Product product) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-border-color: lightgray; -fx-background-color: white;");

        ImageView productImage = new ImageView(new Image(product.getImagePath()));
        productImage.setFitHeight(100);
        productImage.setFitWidth(100);
        productImage.setPreserveRatio(true);

        Label nameLabel = new Label(product.getName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");

        double displayPrice = PricingUtil.getMarkedUpPrice(product);
        Label priceLabel = new Label(String.format("$%.2f", displayPrice));
        priceLabel.setStyle("-fx-font-size: 14px;");

        Button addButton = new Button("Add to cart");
        addButton.setStyle("-fx-background-color: #00c0ff; -fx-text-fill: white;");

        addButton.setOnAction(event -> {
            cart.addProduct(product, 1);
            updateCartViewAndItemCount();
        });

        card.getChildren().addAll(productImage, nameLabel, priceLabel, addButton);
        return card;
    }

    /**
     * Creates a listing for a product if it is added to the inventory.
     *
     * @param inventory the inventory object being initialized
     * @return GridPane
     * @see #initInventory()
     */
    private GridPane createProductListing(Inventory inventory) {
        GridPane productGrid = new GridPane();
        productGrid.setHgap(15);
        productGrid.setVgap(15);
        productGrid.setPadding(new Insets(15));

        int column = 0;
        int row = 0;

        for (Product product : inventory.getProducts().values()) {
            VBox productCard = createProductCard(product);

            productGrid.add(productCard, column, row);
            column++;

            // if the column reaches a certain number, increase the row count and reset the column count
            if (column == 3) {
                column = 0;
                row++;
            }
        }

        return productGrid;
    }

    /**
     * Handles the GUI for the cart.
     *
     * @return VBox
     */
    private VBox createCartView() {
        cartView = new VBox(10);
        cartView.setPadding(new Insets(10));
        cartView.setAlignment(Pos.TOP_CENTER);
        cartView.setStyle("-fx-background-color: #f8f8f8;");

        Label cartLabel = new Label("Shopping Cart");
        cartLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        cartItemsContainer = new VBox(5);
        cartScrollPane = new ScrollPane(cartItemsContainer);
        cartScrollPane.setFitToWidth(true);

        TextField discountCodeInput = new TextField();
        discountCodeInput.setPromptText("Enter promo code");
        Button applyDiscountButton = new Button("Apply Discount");
        applyDiscountButton.setOnAction(event -> {
            String code = discountCodeInput.getText();
            applyDiscountCode(code);
        });
        HBox discountCodeBox = new HBox(5, discountCodeInput, applyDiscountButton);
        discountCodeBox.setAlignment(Pos.CENTER);

        subtotalLabel = new Label("Subtotal: $0.00");
        taxLabel = new Label("Tax: $0.00");
        discountsLabel = new Label("Discounts Applied: None");
        totalDiscountLabel = new Label("Total Discount: $0.00");
        totalLabel = new Label("Total: $0.00");

        Button clearCartButton = new Button("Clear Cart");
        clearCartButton.setOnAction(event -> {
            cart.clearCart();
            updateCartViewAndItemCount();
        });

        proceedToCheckoutButton = new Button("Proceed to Checkout");
        proceedToCheckoutButton.setOnAction(event -> handleCheckout());

        printReceiptButton = new Button("Print Receipt");
        printReceiptButton.setOnAction(event -> printReceipt());
        printReceiptButton.setVisible(false);

        cartView.getChildren().addAll(
                cartLabel,
                discountCodeBox,
                discountCodeInput,
                applyDiscountButton,
                cartScrollPane,
                subtotalLabel,
                taxLabel,
                discountsLabel,
                totalDiscountLabel,
                totalLabel,
                clearCartButton,
                proceedToCheckoutButton,
                printReceiptButton
        );

        return cartView;
    }

    /**
     * Handles checkout.
     */
    private void handleCheckout() {
        proceedToCheckoutButton.setVisible(false);
        printReceiptButton.setVisible(true);
    }

    /**
     * Applies discount to cart.
     *
     * @param code the Discount code (String)
     */
    private void applyDiscountCode(String code) {
        // todo: add err msg handling for invalid or null codes
        // todo: fix 'BOGO' and 'FLAT100' codes for some reason
        cart.applyDiscountCode(code);
        updateCartViewAndItemCount();
    }

    /**
     * Handles creating and accessing a new Transaction object to generate a receipt.
     */
    private void printReceipt() {
        Transaction transaction = new Transaction(cart.getAppliedDiscountCodes(), cart.getItems(), cart.getSubtotalCost(), cart.getTotalTax(), cart.getTotalDiscountAmount(), cart.getTotalCostAfterDiscounts());
        String receipt = transaction.generateReceipt();

        Stage receiptStage = new Stage();
        receiptStage.initModality(Modality.APPLICATION_MODAL);
        receiptStage.setTitle("Receipt");

        Label receiptContent = new Label(receipt);
        receiptContent.setStyle("-fx-font-family: 'Courier New', Courier, monospace; -fx-font-size: 10pt; -fx-padding: 10;");

        VBox layout = new VBox(10, receiptContent);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2;");

        Scene scene = new Scene(layout, 300, 600);
        receiptStage.setScene(scene);
        receiptStage.showAndWait();

        // clear cart after receipt "prints"
        cart.clearCart();
        // remove discounts as well
        cart.clearDiscounts();
        updateCartViewAndItemCount();
    }

    /**
     * Toggles cart view.
     */
    private void toggleCartVisibility() {
        boolean isVisible = cartView.isVisible();
        cartView.setVisible(!isVisible);
        updateCartView();
    }

    /**
     * Updates the view.
     */
    private void updateCartViewAndItemCount() {
        updateCartView();
        itemCountLabel.setText(String.valueOf(cart.getItemCount()));
    }

    /**
     * Updates cart view.
     */
    private void updateCartView() {
        cartItemsContainer.getChildren().clear();
        for (CartItem item : cart.getItems()) {
            cartItemsContainer.getChildren().add(createCartItemView(item));
        }

        subtotalLabel.setText("Subtotal: " + String.format("$%.2f", cart.getSubtotalCost()));
        taxLabel.setText("Tax: " + String.format("$%.2f", cart.getTotalTax()));
        discountsLabel.setText("Discounts Applied: \n" + cart.getAppliedDiscountCodes());
        totalDiscountLabel.setText(String.format("Total Discount: $%.2f", cart.getTotalDiscountAmount()));
        totalLabel.setText("Total: " + String.format("$%.2f", cart.getTotalCostAfterDiscounts()));
    }

    /**
     * Handles logic for creating the item box in the cart view.
     *
     * @param cartItem the CartItem to display
     * @return HBox
     */
    private HBox createCartItemView(CartItem cartItem) {
        HBox itemBox = new HBox(10);
        itemBox.setAlignment(Pos.CENTER_LEFT);

        ImageView productImage = new ImageView(new Image(cartItem.getProduct().getImagePath()));
        productImage.setFitWidth(50);
        productImage.setFitHeight(50);

        Label nameLabel = new Label(cartItem.getProduct().getName());
        double finalCost = PricingUtil.getMarkedUpPrice(cartItem.getProduct()) * cartItem.getQuantity();
        Label priceLabel = new Label(String.format("$%.2f", finalCost));

        Spinner<Integer> quantitySpinner = new Spinner<>(1, 100, cartItem.getQuantity());
        quantitySpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (oldValue > newValue) {
                cartItem.remove();
                updateCartViewAndItemCount();
            } else if (newValue > oldValue) {
                cart.addProduct(cartItem.getProduct(), 1);
                updateCartViewAndItemCount();
            }
        });

        Button removeItem = new Button("X");
        removeItem.setStyle("-fx-background-color: #ffff; -fx-text-fill: #ff0000");
        removeItem.setOnAction(event -> {
            cart.removeProduct(cartItem.getProduct());
            updateCartViewAndItemCount();
        });

        itemBox.getChildren().addAll(productImage, nameLabel, quantitySpinner, priceLabel, removeItem);
        return itemBox;
    }
}
