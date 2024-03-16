package xyz.zuner.javafxassignment;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import xyz.zuner.javafxassignment.objects.Inventory;
import xyz.zuner.javafxassignment.objects.Product;
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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Inventory inventory = initInventory();

        BorderPane root = new BorderPane();
        root.setTop(createHeader());
        root.setCenter(createProductListing(inventory));

        Scene scene = new Scene(root, 960, 540);
        stage.setTitle("Electronics Store");
        stage.setScene(scene);
        stage.show();
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setPadding(new Insets(15));
        header.setAlignment(Pos.CENTER);
        Label title = new Label("Z's Discount Electronics Store!");
        header.getChildren().add(title);
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
            // TODO: add logic to add the product to the cart
        });

        card.getChildren().addAll(productImage, nameLabel, priceLabel, addButton);
        return card;
    }

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
}
