package DataAccess;

import Model.Product;

import java.util.ArrayList;

/**
 * <p>Data Access Object (DAO) for {@link Product} entities.</p>
 * <p>Handles database operations related to products.</p>
 */

public class ProductDao extends AbstractDAO<Product> {
    /**
     * <p>Retrieves all products from the database.</p>
     * @return an {@code ArrayList} containing all products
     */
    public ArrayList<Product> getAllProducts() {
        return (ArrayList<Product>) super.findAll();
    }
}
