package xyz.zuner.javafxassignment;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import xyz.zuner.javafxassignment.objects.*;
import xyz.zuner.javafxassignment.util.PricingUtil;

import java.util.Objects;

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

    private Inventory inventory = new Inventory();
    private Cart cart = new Cart();
    private VBox cartView, cartItemsContainer;
    private ComboBox<String> categoryComboBox;
    private GridPane productGrid;
    private ScrollPane cartScrollPane;
    private Label itemCountLabel, subtotalLabel, taxLabel, discountsLabel, totalLabel, totalDiscountLabel;

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
        BorderPane root = new BorderPane();

        root.setTop(createHeader());
        root.setCenter(createProductListing());
        root.setRight(createCartView());

        Scene scene = new Scene(root, 960, 540);
        stage.setTitle("Z's Discount Electronics Store");
        stage.setScene(scene);
        stage.show();

        // populate all products initially
        updateProductGrid(null);
    }

    /**
     * Creates the header box
     *
     * @return HBox
     */
    private HBox createHeader() {
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15));

        Label sortLabel = new Label("Sort by category:");
        categoryComboBox = new ComboBox<>(FXCollections.observableArrayList(inventory.getCategories()));
        categoryComboBox.getItems().add(0, "All Products");
        categoryComboBox.getSelectionModel().select(0);
        categoryComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> updateProductGrid(newVal));

        StackPane cartIconStack = createCartIcon();
        Button cartButton = new Button();
        cartButton.setGraphic(cartIconStack);
        cartButton.setStyle("-fx-background-color: transparent;"); // Make the button transparent
        cartButton.setOnAction(e -> toggleCartVisibility()); // Use this to toggle the visibility of the cart

        header.getChildren().addAll(sortLabel, categoryComboBox, cartButton);
        return header;
    }

    private StackPane createCartIcon() {
        Label cartIcon = new Label("\uD83D\uDED2");
        cartIcon.setStyle("-fx-font-size: 24px;");

        itemCountLabel = new Label("0");
        itemCountLabel.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-padding: 3px; -fx-font-size: 10px; -fx-border-radius: 50%;");
        itemCountLabel.setAlignment(Pos.TOP_RIGHT);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(cartIcon, itemCountLabel);
        stackPane.setAlignment(Pos.TOP_RIGHT);

        return stackPane;
    }

    /**
     * Updates the product grid for each category.
     *
     * @param category the category to display.
     */
    private void updateProductGrid(String category) {
        productGrid.getChildren().clear();
        int row = 0, col = 0;
        for (Product product : category == null || category.equals("All Products") ? inventory.getAllProducts() : inventory.getProductsByCategory(category)) {
            if (col == 3) {
                col = 0;
                row++;
            }
            productGrid.add(createProductCard(product), col++, row);
        }
    }

    /**
     * Creates a listing for a product if it is added to the inventory.
     *
     * @return GridPane
     */
    private ScrollPane createProductListing() {
        productGrid = new GridPane();
        productGrid.setHgap(10);
        productGrid.setVgap(10);
        productGrid.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(productGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return scrollPane;
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

        Label categoryLabel = new Label("Category: " + product.getCategory());

        double displayPrice = PricingUtil.getMarkedUpPrice(product);
        Label priceLabel = new Label(String.format("$%.2f", displayPrice));
        priceLabel.setStyle("-fx-font-size: 14px;");

        product.getOptions().forEach((optionCategory, options) -> {
            Label optionLabel = new Label(optionCategory + ":");

            if (shouldUseSpinnerForOption(optionCategory)) {
                Spinner<String> optionSpinner = new Spinner<>();
                SpinnerValueFactory<String> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(
                        FXCollections.observableArrayList(options)
                );
                optionSpinner.setValueFactory(valueFactory);
                optionSpinner.setEditable(true);

                optionSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
                    product.setSelectedOption(optionCategory, newVal);
                });
                card.getChildren().addAll(optionLabel, optionSpinner);
            } else {
                ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList(options));
                choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                    product.setSelectedOption(optionCategory, newVal);
                });
                card.getChildren().addAll(optionLabel, choiceBox);
            }
        });

        TextField quantityField = new TextField();
        quantityField.setPromptText("Insert quantity here");
        Button addButton = addToCartButton(product, quantityField);

        Button showDesc = createProductInfoButton(product, card);

        card.getChildren().addAll(
                productImage,
                nameLabel,
                categoryLabel,
                priceLabel,
                addButton,
                quantityField,
                showDesc
        );

        return card;
    }

    private boolean shouldUseSpinnerForOption(String optionCategory) {
        return "Size".equals(optionCategory) || "Storage".equals(optionCategory);
    }

    private Button addToCartButton(Product product, TextField quantityField) {
        Button addButton = new Button("Add to Cart");
        addButton.setOnAction(event -> {
            try {
                if (quantityField.getText().trim().isEmpty()) {
                    // if empty, assume default quantity of 1
                    cart.addProduct(product, 1);
                } else {
                    int quantity = Integer.parseInt(quantityField.getText().trim());
                    if (quantity >= 1) {
                        cart.addProduct(product, quantity);
                    } else {
                        // if the quantity is less than 1, show an error dialog
                        throw new IllegalArgumentException("Please enter a quantity greater than zero.");
                    }
                }
                updateCartViewAndItemCount();
            } catch (NumberFormatException e) {
                cart.showErrorDialog("Invalid Input", "Please enter a valid number.");
            } catch (IllegalArgumentException e) {
                cart.showErrorDialog("Invalid Input", e.getMessage());
            }
        });
        return addButton;
    }

    private Button createProductInfoButton(Product product, VBox card) {
        Button infoButton = new Button("Show Description");
        infoButton.setOnAction(event -> {
            Popup popup = new Popup();
            VBox content = new VBox(10);
            content.setStyle("-fx-padding: 10; -fx-background-color: white; -fx-border-color: black; -fx-border-width: 2;");

            Label descriptionLabel = new Label(product.getDescription() + "\nUPC: #" + product.getUPC());
            content.getChildren().addAll(descriptionLabel);

            popup.getContent().add(content);
            popup.setAutoHide(true);
            popup.setHideOnEscape(true);

            Bounds boundsInScreen = card.localToScreen(card.getBoundsInLocal());
            popup.show(card, boundsInScreen.getMinX(), boundsInScreen.getMaxY());
        });
        return infoButton;
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

        updateButtonStates();

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
     * Updates the state of checkout and print receipt buttons based on the cart's content.
     */
    private void updateButtonStates() {
        boolean isCartEmpty = cart.getItems().isEmpty();

        proceedToCheckoutButton.setDisable(isCartEmpty);
        printReceiptButton.setDisable(isCartEmpty);

        // Optionally change the style to visually indicate that the button is disabled
        proceedToCheckoutButton.setStyle(isCartEmpty ? "-fx-opacity: 0.5;" : "-fx-opacity: 1.0;");
        printReceiptButton.setStyle(isCartEmpty ? "-fx-opacity: 0.5;" : "-fx-opacity: 1.0;");
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
        String receipt = transaction.processTransaction();

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
        updateButtonStates();
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
        Label optionsLabel = new Label(cartItem.getSelectedOptions());
        optionsLabel.setStyle("-fx-font-size: 8px;");
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

        itemBox.getChildren().addAll(productImage, nameLabel, optionsLabel, quantitySpinner, priceLabel, removeItem);
        return itemBox;
    }
}
