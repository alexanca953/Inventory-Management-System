package BusinessLogic.validators;

import Model.Product;
/**
 * Validator class to check if the fields of a Product object are valid and not empty.
 * Ensures that the product name is not empty, price and stock quantity are positive,
 * and the product ID is not negative.
 */
public class NotEmptyValidatorProduct implements Validator<Product>{
    public void validate(Product t) {
        if (t.getName().isEmpty()|| !(t.getPrice()>0)||!(t.getStockQuantity()>0)||t.getId()<0) {
            throw new IllegalArgumentException("Data must not be null ");
        }
    }
}
