package BusinessLogic.validators;

import Model.Client;
/**
 * Validator class to ensure that all required fields of a Client object are not empty.
 * Checks that last name, first name, phone, and address are non-empty strings.
 */
public class NotEmptyValidator implements Validator<Client>{
    public void validate(Client t) {
        if (t.getLastName().isEmpty() || t.getFirstName().isEmpty()|| t.getPhone().isEmpty()||t.getAddress().isEmpty()) {
            throw new IllegalArgumentException("Data must not be null ");
        }
    }
}
