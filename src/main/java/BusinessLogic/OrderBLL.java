package BusinessLogic;

import BusinessLogic.validators.AddressValidator;
import BusinessLogic.validators.Validator;
import DataAccess.OrderDao;
import Model.Client;
import Model.Orders;
import Model.Product;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
/**
 * <p>
 * Business Logic Layer class for managing Orders.
 * Handles validation and delegates CRUD operations to the OrderDao.
 * </p>
 */
public class OrderBLL {
    public OrderDao orderDao ;
    private List<Validator<Orders>>validators;
    /**
     * <p>
     * Constructor that initializes validators and the OrderDao.
     * </p>
     */
    public OrderBLL()
    {
        validators = new ArrayList<>();
       orderDao=new OrderDao();
       validators.add(new AddressValidator());
    }
    /**
     * <p>
     * Retrieves all orders from the database.
     * </p>
     *
     * @return a list containing all orders
     */
    public ArrayList<Orders> getAllOrders() {
        return (ArrayList<Orders>)orderDao.findAll();
    }
    /**
     * <p>
     * Retrieves an order by its ID.
     * </p>
     *
     * @param id the ID of the order to be retrieved
     * @return the order with the specified ID
     */
    public Orders getOrderById(int id) {
        return (Orders)orderDao.findById(id);
    }
    /**
     * <p>
     * Inserts a new Order after validating it and checking the client and product list.
     * </p>
     *
     * @param c      the order to be inserted
     * @param p      the list of products associated with the order
     * @param client the client placing the order
     * @return the inserted order
     * @throws NullPointerException if the client is null or product list is empty
     */
    public Orders insertOrder(Orders c, ArrayList<Product> p, Client client) {
        validators.forEach(v -> v.validate(c));
        if (client == null) {
           throw new NullPointerException("Client is null");
        }
      else  if (p.isEmpty()) {
            throw new NullPointerException("Product list is empty");
        }

        else

        return (Orders) orderDao.insert(c);
    }
    /**
     * <p>
     * Updates an existing Order.
     * </p>
     *
     * @param s the order to be updated
     * @return the updated order
     */
    public Orders updateOrder(Orders s) {
        return (Orders)orderDao.update(s);
    }
    /**
     * <p>
     * Deletes an Order by its ID.
     * </p>
     *
     * @param id the ID of the order to be deleted
     * @return true if the deletion was successful, false otherwise
     */
    public boolean deleteOrder(int id) {
        return orderDao.delete(id);
    }
    /**
     * <p>
     * Populates a JTable with orders data from a list of Orders.
     * </p>
     *
     * @param table  the JTable to populate
     * @param orders the list of orders to populate the table with
     */
    public void populateTableFromList(JTable table, List<Orders> orders)
    {
        orderDao.populateTableFromList(table, orders);
    }
}
