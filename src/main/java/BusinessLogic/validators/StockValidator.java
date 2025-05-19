
package BusinessLogic.validators;

import Model.Product;
/**
 * Validator for Product stock quantity.
 * Ensures that the stock quantity of a product is not negative.
 */
public class StockValidator implements Validator<Product> {
    public void validate(Product t) {
        if (t.getStockQuantity() < 0) {
            throw new IllegalArgumentException("Stock quantity needs to be a positive number: " + t.getPrice());
        }
    }
}