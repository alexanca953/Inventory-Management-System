package BusinessLogic;
import BusinessLogic.validators.NotEmptyValidatorProduct;
import BusinessLogic.validators.StockValidator;
import Model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import BusinessLogic.validators.Validator;
import DataAccess.ProductDao;

import javax.swing.*;
/**
 * <p>
 * Business Logic Layer class for managing Product entities.
 * Validates and delegates operations to the ProductDao.
 * </p>
 */
public class ProductBLL {
    private List<Validator<Product>> validators;
    ProductDao productDAO;
    /**
     * <p>
     * Constructor that initializes validators and the ProductDao.
     * </p>
     */
    public ProductBLL() {
        validators = new ArrayList<>();
        validators.add(new StockValidator());
        validators.add( new NotEmptyValidatorProduct());
        productDAO = new ProductDao();
    }
    /**
     * <p>
     * Retrieves all products from the database.
     * </p>
     *
     * @return a list containing all products
     */
 public ArrayList<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }
    /**
     * <p>
     * Finds a Product by its ID.
     * </p>
     *
     * @param id the ID of the product to find
     * @return the found product
     * @throws NoSuchElementException if the product is not found
     */
 public Product findById(int id) {
        Product product = productDAO.findById(id);
        if(product == null)
            throw new NoSuchElementException("Product not found");
        return product;
    }
    /**
     * <p>
     * Deletes a Product by its ID.
     * </p>
     *
     * @param id the ID of the product to delete
     */
 public void delete(int id) {
        productDAO.delete(id);
    }
    /**
     * <p>
     * Retrieves all products from the database as a List.
     * </p>
     *
     * @return a list of all products
     */
 public List<Product> findAll() {
        return productDAO.findAll();
    }
 /**
     * <p>
     * Inserts a new Product into the database after validating it.
     * </p>
     *
     * @param product the product to insert
     * @return the inserted product
     */
 public Product insert(Product product)
    {
        validators.forEach(v -> v.validate(product));
        return productDAO.insert(product);
    }
    /**
     * <p>
     * Updates an existing Product in the database after validating it.
     * </p>
     *
     * @param product the product to update
     * @return the updated product
     */
 public Product update(Product product) {
        for(Validator<Product> v : validators)
        {
            v.validate(product);
        }
        return productDAO.update(product);
    }

    /**
     * <p>
     * Populates a Swing JTable with product data from a list of products.
     * </p>
     *
     * @param table    the JTable to populate
     * @param products the list of products to display in the table
     */
 public void populateTableFromList(JTable table, List<Product> products)
    {
        productDAO.populateTableFromList(table, products);
    }


}