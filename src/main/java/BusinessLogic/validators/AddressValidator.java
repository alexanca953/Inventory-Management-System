package BusinessLogic.validators;

import Model.Orders;
/**
 * Validator for verifying that the shipping address in an order is not null.
 */
public class AddressValidator implements Validator<Orders>{
    public void validate(Orders t) {
        if (t.getShippingAddress()==null) {
            throw new IllegalArgumentException("Address must not be null ");
        }
    }
}
