package BusinessLogic.validators;
/**
 * Generic interface for validating objects of type T.
 */
public interface Validator <T>{
    public void validate(T t);
}
