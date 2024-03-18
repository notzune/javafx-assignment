package xyz.zuner.javafxassignment.objects;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Provides logic for parsing Discounts from a JSON file.
 * </p>
 * <br>
 * <p>21:198:102/02 Computers and Programming II</p>
 * <p>JavaFX Assignment</p>
 * <p>Rutgers ID: 199009651</p>
 * <br>
 *
 * @author Zeyad "zmr15" Rashed
 * @mailto zmr15@scarletmail.rutgers.edu
 * @created 3/18/24, Monday
 * @see Discount
 */
public class DiscountFactory {

    private static final Map<String, Discount> discountMap = new HashMap<>();

    static {
        loadDiscounts("/assets/discounts.json");
        printAllDiscounts();
    }

    /**
     * Parses a JSON file in-order to serialize a new Discount object.
     *
     * @param filePath the path to the discount file
     * @see Discount
     */
    public static void loadDiscounts(String filePath) {
        try (InputStream is = DiscountFactory.class.getResourceAsStream(filePath)) {
            if (is == null) {
                throw new IOException("Resource not found: " + filePath);
            }
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            Gson gson = new Gson();
            Type type = new TypeToken<Set<Discount>>() {
            }.getType();
            Set<Discount> discounts = gson.fromJson(content, type);

            for (Discount discount : discounts) {
                discountMap.put(discount.getCode(), discount);
            }
        } catch (IOException e) {
            System.err.println("Error loading discounts from resource: " + e.getMessage());
        }
    }

    public static Discount getDiscountByCode(String code) {
        return discountMap.get(code);
    }

    private static void printAllDiscounts() {
        discountMap.forEach((code, discount) -> System.out.println(discount.toString()));
    }
}
